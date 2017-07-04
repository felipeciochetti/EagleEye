package br.com.eagleeye.util;

import java.io.File;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.eagleeye.jpa.Usuario;

public  class ContextUtils {
	
	private static ContextUtils instance = new ContextUtils();

	private String pathFilesAttacheds;
	private String pathTemporarios;
	private String pathArquivosProperties;
	private String volta2Path = File.separator + ".." + File.separator + "..";
	private String pastaEagle = File.separator +"logs" +  File.separator +"Eagle";
 
	private String pathBase;
	private String pathFilesLoteXML;
	private String pathFilesSerial;
	private String pathFilesDocuments;
	private String pathFilesJasper;
	private String pathFilesRegistros;
	private String pathConfigEagle;
	private String pathTempFiles;
	private String pathLog;
	private String pathFilesProperties;
	private String pathConfigEagleEye;
	
	static Logger logger = Logger.getLogger(ContextUtils.class);	
	public static ContextUtils getInstance(){
		return instance;
	}
	public static ContextUtils getInstance(String pathConfigEagleEye){
		if ( getInstance().pathConfigEagleEye == null ){
			getInstance().setPathConfigEagle(pathConfigEagleEye);
		}
			
		return instance;
	}

	private void setPathConfigEagle(String pathConfigEagleEye){
		this.pathBase = pathConfigEagleEye + volta2Path;
		this.pathConfigEagleEye = pathConfigEagleEye + pastaEagle + File.separator + "EagleConfig" + File.separator;
	}

	public HttpSession getSession(){
		return ( HttpSession )getExternalContext().getSession( false );
	}

	public String getPathConfigEagle(){	
		if ( pathConfigEagle == null ){
			pathConfigEagle = System.getProperty ("catalina.base") + pastaEagle + File.separator + "EagleConfig" + File.separator; 
		}
		return pathConfigEagle;
	}

	public String getPathLogoTipos(){
		return System.getProperty ("catalina.base") + File.separator + pastaEagle +  File.separator + "EagleLogoTipos" + File.separator;
	}
	public static String getParemeterMap(String parameter){
		
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parameter);
	}
	public String getPathFilesAttacheds(){	
		if ( pathFilesAttacheds == null ){
			if ( System.getProperty("aplic") == null || System.getProperty("es.env") == null ){
				//pathFilesAttacheds = System.getProperty ("catalina.base") + volta2PathMaisEagle + File.separator + "ArquivosAnexos" + File.separator; 
				pathFilesAttacheds = System.getProperty ("catalina.base") + pastaEagle + File.separator + "ArquivosAnexos" + File.separator;
			}else{
				//pathFilesAttacheds = System.getProperty("aplic") + System.getProperty("es.env") + "1" + File.separator + "prod" + File.separator;
			}
		}
		return pathFilesAttacheds;
	}
	public String getPathArquivosProperties(){
		
		if ( pathArquivosProperties == null ){
			if ( System.getProperty("aplic") == null || System.getProperty("es.env") == null ){
				pathArquivosProperties = System.getProperty ("catalina.base")  + pastaEagle + File.separator + "Properties" + File.separator; 
			}else{
				pathArquivosProperties = System.getProperty("aplic") + System.getProperty("es.env") + "1" + File.separator + "prod" + File.separator;
			}
		}
		
		return pathArquivosProperties;
	}
	public String getPathTemporarios(){	
		if ( pathTemporarios == null ){
			if ( System.getProperty("aplic") == null || System.getProperty("es.env") == null ){
				pathTemporarios = System.getProperty ("catalina.base") + pastaEagle + File.separator + "Temporarios" + File.separator; 
			}else{
				pathTemporarios = System.getProperty("aplic") + System.getProperty("es.env") + "1" + File.separator + "prod" + File.separator;
			}
		}
		Functions.criarDiretorio(pathTemporarios);
		
		return pathTemporarios;
	}
	public String getPathDocuments(){	
		if ( pathFilesDocuments == null ){
			pathFilesDocuments = System.getProperty ("catalina.base") + File.separator + "ByDocuments" + File.separator; 
		}
		return pathFilesDocuments;
	}

	public String getPathRegistros(){	
		if ( pathFilesRegistros == null ){
			pathFilesRegistros = System.getProperty ("catalina.base") + pastaEagle + File.separator + "ByRegistros" + File.separator; 
		}
		return pathFilesRegistros;
	}

	public String getPathTempFiles(){	
		if ( pathTempFiles == null ){
			pathTempFiles = System.getProperty ("catalina.base") + File.separator + "ByTempFiles" + File.separator; 
		}
		return pathTempFiles;
	}

	public String getPathFilesLoteXML(){		
		if ( pathFilesLoteXML == null ){
			pathFilesLoteXML = System.getProperty ("catalina.base") + File.separator + "ByLoteXMLFiles" + File.separator; 
		}
		return pathFilesLoteXML;
	}
	public Usuario getUsuarioLogado(){
		return (Usuario) getSession().getAttribute("usuario");
	}
	public String getTipoUsuarioLogado(){
		try{
			Usuario usuario =  (Usuario) getSession().getAttribute("usuario");
			if(usuario != null){
				return usuario.getNivel();
			}
		}catch(Exception e){
		}
		return "";
	}

	public ExternalContext getExternalContext(){
		FacesContext fc = FacesContext.getCurrentInstance();
		if ( fc != null ){
			return fc.getExternalContext();
		}
		return null;
	}

	public String getUrlLogotipo(){
		if ( System.getProperty("aplic") == null || System.getProperty("es.env") == null ){
			return System.getProperty ("catalina.base")  + pastaEagle + File.separator + "Logotipos" + File.separator ; 
		}else{
			return  System.getProperty("aplic") + System.getProperty("es.env") + "1" + File.separator + "prod" + File.separator;
		}
	}
	
}
