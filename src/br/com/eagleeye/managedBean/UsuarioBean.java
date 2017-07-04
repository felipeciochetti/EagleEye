package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import br.com.eagleeye.complete.ClienteComplete;
import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.repositorio.Clientes;
import br.com.eagleeye.repositorio.Usuarios;
import br.com.eagleeye.service.CadastrarUsuario;
import br.com.eagleeye.util.ContextUtils;


@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Usuarios repositorio = new Usuarios();
	Clientes repositorioCliente = new Clientes();
	Usuario usuario ;
	CadastrarUsuario cadastar = new CadastrarUsuario() ;
	boolean isUsuarioCliente = false;
	private int index;




	public UsuarioBean() {
	}
	@PostConstruct
	public void init(){
		
		usuario = new Usuario();
		if(!isAlterando()){
			valoresDefaultNovoRegistro();
		}
	}
	public void valoresDefaultNovoRegistro(){
		usuario.setDtCadastro(new Timestamp(System.currentTimeMillis()));
}
	public boolean isAlterando(){
		// alterar	
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){
			usuario = repositorio.get(Long.valueOf(id));
			isUsuarioCliente = usuario.getNivel().equals("C");
			return true;
		}
		return false;
		
	}

	public CadastrarUsuario getCadastar() {
		return cadastar;
	}

	public void setCadastar(CadastrarUsuario cadastar) {
		this.cadastar = cadastar;
	}


	public String gravar(){
		if(cadastar.adicionar(usuario)){
			usuario = new Usuario();
			return "ListaUsuario";
		}
		return "";
	}
	public void cancelar(){
		usuario = new Usuario();
	}


	public Usuario getUsuario() {
		if(usuario.getDtCadastro() == null){
			usuario.setDtCadastro(new Timestamp(System.currentTimeMillis()));
		}
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Cliente> completeCliente(String query) {
		return new ClienteComplete(repositorioCliente).execute(query);
	}
	public void mudouTipo(AjaxBehaviorEvent abe) {
		if(usuario.getNivel().equals("C")){
			setUsuarioCliente(true);
		}else{
			setUsuarioCliente(false);
		}
	}

	public boolean isUsuarioCliente() {
		return isUsuarioCliente;
	}
	public void setUsuarioCliente(boolean isUsuarioCliente) {
		this.isUsuarioCliente = isUsuarioCliente;
	}


	//@SuppressWarnings("unused")
	//public   EntityManager getManager() {
	//	FacesContext fc = FacesContext.getCurrentInstance();
	//	ExternalContext ec = fc.getExternalContext();
	//	HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	//	return (EntityManager) request.getAttribute("EntityManager");
	//}
}