package br.com.eagleeye.mensagens;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Mensagem {
	
	public Mensagem() {

	}
	
	public void mensagensGeralErro(String titulo,String msg){
		addMessageGeral(titulo, msg, FacesMessage.SEVERITY_ERROR);
	}
	
	public void mensagensGeralAviso(String titulo , String msg){
		addMessageGeral(titulo, msg, FacesMessage.SEVERITY_INFO);
	}
	public void mensagensGeralErro(String msg){
		addMessageGeral("", msg, FacesMessage.SEVERITY_ERROR);
	}
	
	public void mensagensGeralAviso(String msg){
		addMessageGeral("",msg, FacesMessage.SEVERITY_INFO);
	}
	public void addMessage(String key, String msg, Severity sev){
		addMessage(key, msg, "", sev);
	}
	public void addMessageGeral(FacesMessage fm){
		FacesContext.getCurrentInstance ().addMessage(null,fm);
	}
	public void addMessage(String key,String titulo, String msg, Severity sev){
		try{
			FacesMessage fm = new FacesMessage (titulo,msg);
			fm. setSeverity ( sev );
			FacesContext.getCurrentInstance ().addMessage (key , fm);
		}catch (Exception e) {
			System.out.println("Nao ha Contexto para Add Mensagem !\nMensagem era: " + msg);
		}
	}
	public void addMessageGeral(String titulo, String msg, Severity sev){
		try{
			FacesMessage fm = new FacesMessage (titulo,msg);
			fm. setSeverity ( sev );
			FacesContext.getCurrentInstance ().addMessage (null , fm);
		}catch (Exception e) {
			System.out.println("Nao ha Contexto para Add Mensagem !\nMensagem era: " + msg);
		}
	}
}
