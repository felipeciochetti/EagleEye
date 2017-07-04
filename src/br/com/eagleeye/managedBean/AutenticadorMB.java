package br.com.eagleeye.managedBean;

import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;

import java.io.FileInputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;

import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Usuarios;
import br.com.eagleeye.service.RecuperarSenha;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.SendMail;

@ManagedBean
@RequestScoped
public class AutenticadorMB  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomeUsuario;
	private String senha;
	private String emailRecuperarSenha;

	private Usuarios repositorio;
	

	private String repeteSenha ;
	private String novaSenha;
	private String confNovaSenha ;

	public String entrar() {

		try{
			if (this.nomeUsuario.trim().length() == 0) {
				new Mensagem().addMessage("msgLogin", "Informe usuário", FacesMessage.SEVERITY_WARN);
			} else if (this.senha.trim().length() == 0) {
				new Mensagem().addMessage("msgLogin", "Informe senha", FacesMessage.SEVERITY_WARN);
			} else {
				
				if(repositorio == null){
					repositorio = getRepositorio();
				}

				  Usuario usuario = repositorio.autentica(nomeUsuario, senha);
				  
				  
				  boolean isUsuarioExiste = usuario != null;

				if(isUsuarioExiste){
					ContextUtils.getInstance().getSession().setAttribute("usuario", usuario);
					
					if(usuario.getNivel().equals("C")){
						return "IndexUsuario";
					}
					
					return "Index";
				}else{
					new Mensagem().addMessage("msgLogin", "Verifique seu usuario e senha", FacesMessage.SEVERITY_ERROR);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String getUsuario() {
		return nomeUsuario;
	}

	public void setUsuario(String usuario) {
		this.nomeUsuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuarios getRepositorio() {
		if (repositorio == null) {
			repositorio = new Usuarios();
		}
		return repositorio;
	}
	public void enviarEmailRecuperarSenha(){
		
		if("".equals(emailRecuperarSenha)){
			new Mensagem().addMessage("msgRecuperar", "Informe o email", FacesMessage.SEVERITY_WARN);
			return;
		}
		
		RecuperarSenha recuperarSenha = new RecuperarSenha();
		
		recuperarSenha.recuperarSenha(emailRecuperarSenha);
		
	}
	public void gravarAlteracaoDados(){

		if(repositorio == null){
			repositorio = getRepositorio();
		}

		Usuario usuarioAlterar = getUsuarioAlterarDados();
		
		if((repeteSenha == null || "".equals(repeteSenha)) || (novaSenha == null || "".equals(novaSenha)) || (confNovaSenha == null || "".equals(confNovaSenha))){
			new Mensagem().addMessage("msgAlterar", "Informe os campos de Senha", FacesMessage.SEVERITY_WARN);
			return ;
		}
		
		if(!repeteSenha.equals(usuarioAlterar.getSenha()) || !novaSenha.equals(confNovaSenha)){
			new Mensagem().addMessage("msgAlterar", "Campos de senha não conferem", FacesMessage.SEVERITY_ERROR);
			return ;
		}
		
		
		usuarioAlterar.setSenha(novaSenha);
		repositorio.atualizar(usuarioAlterar);
		new Mensagem().addMessage("msgAlterar", "Dados Alterados com Sucesso", FacesMessage.SEVERITY_INFO);
		
	}
	public Usuario getUsuarioAlterarDados(){
		return  ContextUtils.getInstance().getUsuarioLogado();
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}




	public String getEmailRecuperarSenha() {
		return emailRecuperarSenha;
	}

	public void setEmailRecuperarSenha(String emailRecuperarSenha) {
		this.emailRecuperarSenha = emailRecuperarSenha;
	}

	public void setRepositorio(Usuarios repositorio) {
		this.repositorio = repositorio;
	}
	
	public String getRepeteSenha() {
		return repeteSenha;
	}


	public void setRepeteSenha(String repeteSenha) {
		this.repeteSenha = repeteSenha;
	}


	public String getNovaSenha() {
		return novaSenha;
	}


	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}


	public String getConfNovaSenha() {
		return confNovaSenha;
	}


	public void setConfNovaSenha(String confNovaSenha) {
		this.confNovaSenha = confNovaSenha;
	}


	public String fazerLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login";
    }
	
}