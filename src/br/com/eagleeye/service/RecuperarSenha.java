package br.com.eagleeye.service;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;

import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Usuarios;
import br.com.eagleeye.util.SendMail;

public class RecuperarSenha {
	
	Usuarios repositorio = new Usuarios();
	SendMail email = new SendMail();
	
	Logger logger = Logger.getLogger(RecuperarSenha.class);
	
	
	public RecuperarSenha() {
	
	}
	
	
	public void recuperarSenha(String email){
		
		List<Usuario> listUsuario = (List<Usuario>) repositorio.listarByEmail(email);
		 
		if(listUsuario.size() == 0 ){
			new Mensagem().addMessage("msgRecuperar","Email não localizado na base de dados.",FacesMessage.SEVERITY_ERROR);
			return ;
		}
		
		
		String novaSenha = gerarSenha();
		
		Usuario usuario = listUsuario.get(0);
		usuario.setSenha(novaSenha);
		
		
		
		envioSenhaEmail(usuario);
		repositorio.atualizar(usuario);
		
		new Mensagem().addMessage("msgRecuperar","Senha enviada para o email.",FacesMessage.SEVERITY_INFO);
		logger.info("redefinindo senha para usuário.. OK");
		
	}
	private void envioSenhaEmail(Usuario destinatario){
		String assunto = "Eagle Eye - Recuperação de Senha";
		String msg = 	"<span style=\"font-weight:bold;font-size: 10pt;\">Dados de Acesso para o Eagle Eye.</span>\n"+	
						"<span style=\"font-weight:bold;font-size: 8pt;\">Usuário: </span>"+destinatario.getUsuario()+"\n"+
						"<span style=\"font-weight:bold;font-size: 8pt;\">Senha: </span>"+destinatario.getSenha();
		
		try {
			this.email.enviarEmailSimples(msg, assunto, destinatario.getEmail(),true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro ao enviar email recuperação de senha " + e.getMessage());
		}
	}
	public String gerarSenha() { 


		String[] carct ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 

		String senha=""; 


		for (int x=0; x<6; x++){ 
		int j = (int) (Math.random()*carct.length); 
		senha += carct[j]; 

		} 

		return senha;
	}

}
