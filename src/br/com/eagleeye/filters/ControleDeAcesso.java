package br.com.eagleeye.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.eagleeye.jpa.Usuario;
public class ControleDeAcesso implements Filter {

	static Logger logger = Logger.getLogger(ControleDeAcesso.class);
	
	@Override
	public void doFilter ( ServletRequest request , ServletResponse response ,
			FilterChain chain ) throws IOException , ServletException {

		HttpServletRequest req = ( HttpServletRequest ) request ;
		HttpSession session = req.getSession();

		if ( (session != null && verificarUsuarioSessao(session,req)) || req.getRequestURI().contains("/Login.xhtml") || req.getRequestURI ().contains("/javax.faces.resource") || req.getRequestURI (). endsWith(".jnlp") || req.getRequestURI (). endsWith(".jar") || req.getRequestURI (). contains("/myMessageBroker") || req.getRequestURI (). contains("/services") || (req.getRequestURI().contains("/icons")) || (req.getRequestURI().contains("/fontes") || req.getRequestURI().contains("/resources/img/")) ) {
			logger.info("Controle Acesso OK : session > " +(session != null) +" |  usuario "+session.getAttribute ("usuario") + " | url > "+req.getRequestURI());	
			continuarOk(req, response, chain);
		}else{
			logger.warn("Controle Acesso Negado : session > " + ((session != null))+" | usuario "+session.getAttribute ("usuario") +" | url >" + req.getRequestURI() + " | redireciona Login ");
			redireciona("Login.xhtml", response);
		}
		
	}
	
	public boolean verificarUsuarioSessao(HttpSession session,HttpServletRequest req){
	
		if(session.getAttribute ("usuario") != null){
			
			Usuario usuario = (Usuario) session.getAttribute ("usuario"); 
			
			// usuario cliente so pode ver essas duas telas
			if(usuario.getNivel().equals("C")){
				
				if(req.getRequestURI().contains("/usuario/") || req.getRequestURI().contains("/exibeDocumento") ){
					return true;
				}else{
					logger.warn("Controle Acesso Negado : usuario do tipo cliente solicitando pagina invalida" );
					return false;
				}
				
			}else{
				return true;
			}
			
		}else{
			return false;
		}
		
		
	}
	
	public void redireciona(String url , ServletResponse response) {
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			res.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void continuarOk(HttpServletRequest request , ServletResponse response ,FilterChain chain) throws IOException , ServletException {

		chain.doFilter ( request , response );
		
	}

	@Override
	public void init ( FilterConfig filterConfig ) throws ServletException {
	}

	@Override
	public void destroy () {

	}
}