package br.com.eagleeye.managedBean;

import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;

import java.io.FileInputStream;
import java.io.IOException;
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
public class ProcessoFiltroBean  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String valorPesquisa;

	
	public void filtroProcessoDepachante() {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("ListaProcesso.xhtml?ref="+valorPesquisa);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void filtroProcessoCliente() {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("ListaProcessoView.xhtml?ref="+valorPesquisa);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String getValorPesquisa() {
		return valorPesquisa;
	}

	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;
	}
						
	
	
	
}