package br.com.eagleeye.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.eagleeye.filters.ControleDeAcesso;
import br.com.eagleeye.mensagens.Mensagem;

public class UploadFileUtils {
	static Logger logger = Logger.getLogger(UploadFileUtils.class);
	private String cleanString(String str) {
        String c_acentos = "ÄÅÁÂÀÃäáâàãÉÊËÈéêëèÍÎÏÌíîïìÖÓÔÒÕöóôòõÜÚÛüúûùÇçº§£ª<>©&\"\'";
        String s_acentos = "AAAAAAaaaaaEEEEeeeeIIIIiiiiOOOOOoooooUUUuuuuCc          ";
 
        
        if(str != null && !str.trim().equals("")){
            for(int i=0; i < c_acentos.length(); i++){
                str= str.replace(c_acentos.charAt(i),s_acentos.charAt(i));
            }
            str = str.replaceAll("\0", "_");
            str = str.replace(" ", "_");
            return str.trim();
 
        }else{
            return "";
        }
    }
	
	public void doFileUpload(UploadedFile uploadedFile, String codigo, List<String> arquivosAnexos) {
		File f = null;
		try{
			if ( uploadedFile.getSize() > 5000000 ){
				throw new Exception("Arquivo não pode exceder 5mb !");
			}
			if ( codigo == null || codigo.trim().length() == 0 ){
				throw new Exception("Path não foi setado !");
			}
			File dir = new File(ContextUtils.getInstance().getPathFilesAttacheds() + codigo);
			try{
				if ( ! dir.exists() ){
					dir.mkdirs();
				}
			}catch (Exception e) {
				throw e;
			}finally{
				dir= null;
			}
			String fileName = uploadedFile.getFileName();
			if ( fileName.indexOf(File.separator) >= 0 ){
				fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
			}
			fileName = cleanString(fileName);
			f = new File(ContextUtils.getInstance().getPathFilesAttacheds() + codigo + File.separator + fileName);
			if ( f.exists() && f.isFile() ){
				throw new Exception("Já existe um arquivo com este nome.");
			}
			FileOutputStream fos = new FileOutputStream(f);
			try{
				fos.write(uploadedFile.getContents());
				fos.flush();	
				arquivosAnexos.add(fileName);
			}catch (Exception e) {
				throw e;
			}finally{
				try{
					fos.close();
					fos = null;
				}catch (Exception e) {					
				}
			}
			
			new Mensagem().mensagensGeralAviso("Arquivo transferido com sucesso. " + fileName);
		}catch (Exception e) {
			e.printStackTrace();
			new Mensagem().mensagensGeralAviso("Operação abortada: " + e.getMessage());
		}finally{
			f = null;
		}
	}
	
	public String handleFileUpload(FileUploadEvent event, String codigo) {
		File f = null;
		try{
			if ( codigo == null || codigo.trim().length() == 0 ){
				throw new Exception("Path não foi setado !");
			}
			File dir = new File(ContextUtils.getInstance().getPathFilesAttacheds() + codigo);
			try{
				if ( ! dir.exists() ){
					dir.mkdirs();
				}
			}catch (Exception e) {
				throw e;
			}finally{
				dir= null;
			}
			String fileName = event.getFile().getFileName();
			
			//Nao pode ser File.separator pois esta vindo da estacao. Se a estacao for Windows e server for linux gera problema.
			fileName = fileName.replace("\\", "/");
			if ( fileName.indexOf("/") >= 0 ){
				fileName = fileName.substring(fileName.lastIndexOf("/")+1);
			}
			fileName = cleanString(fileName);
			String url = ContextUtils.getInstance().getPathFilesAttacheds() + codigo + File.separator + fileName;
			f = new File(url);
			if ( f.exists() && f.isFile() ){
				throw new Exception("Já existe um arquivo com este nome.");
			}
			logger.warn("salvar aqv");
			FileOutputStream fos = new FileOutputStream(f);
			try{
				fos.write(event.getFile().getContents());
				fos.flush();	
			}catch (Exception e) {
				throw e;
			}finally{
				try{
					fos.close();
					fos = null;
				}catch (Exception e) {					
				}
			}
			System.out.println("Arquivo transferido com sucesso. " + fileName);
			new Mensagem().addMessage("msgArquivosAnexos", "Arquivo transferido com sucesso. " + fileName, FacesMessage.SEVERITY_INFO);
			return url;
		}catch (Exception e) {
			e.printStackTrace();
			new Mensagem().mensagensGeralAviso("Operação abortada: " + e.getMessage());
			return "";
		}finally{
			f = null;
		}
    }
}
