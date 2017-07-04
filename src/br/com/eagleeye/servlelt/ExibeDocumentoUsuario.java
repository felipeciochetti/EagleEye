package br.com.eagleeye.servlelt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.eagleeye.util.ContextUtils;

@WebServlet("/usuario/exibeDocumento")
public class ExibeDocumentoUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExibeDocumentoUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = "";
		String tipo = "";
		
		try{
			if ( request.getParameter("nome") != null ){
				try{
					nome = request.getParameter("nome");
					tipo = request.getParameter("tipo");
				}catch(Exception e){		
					e.printStackTrace();
				}
			
				byte[] ba = carregarArquivo(nome, tipo);
				
				response.addHeader("Content-Disposition", "attachment; filename=" + nome);		
				response.setContentLength(ba.length);		
				response.getOutputStream().write( ba ); 
			}else{
				response.getWriter().println("Erro ao passar nome do arquivo ao servidor !");
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Ocorreu erro durante operação !");			
		}
	}
	
	private byte[] carregarArquivo(String nomeArquivo, String tipo){
		byte[] retorno = new byte[0];
		
		File f = null;
		InputStream fis = null;
		
		try{
								
			if ( tipo.equals("1") ){
				f = new File( ContextUtils.getInstance().getPathTemporarios() + nomeArquivo);
			}else if ( tipo.equals("2") ){
				f = new File( ContextUtils.getInstance().getPathRegistros() + nomeArquivo);
			}else if ( tipo.equals("3") ){
				f = new File( ContextUtils.getInstance().getPathFilesAttacheds() + nomeArquivo);
			}else if ( tipo.equals("4") ){
				f = new File( ContextUtils.getInstance().getPathTempFiles() + nomeArquivo);
			}else if ( tipo.equals("5") ){
				f = new File( ContextUtils.getInstance().getPathFilesLoteXML() + nomeArquivo);
			}
			if ( f.exists() ){
			
				retorno = new byte[(int)f.length()];
				
				fis = new FileInputStream( f );
				try{
					fis.read(retorno);
				}finally{
					try{
						fis.close();
						fis = null;
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
				
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			f = null;
		}
		
		return retorno;
	}

	
}
