package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.jpa.ClienteContato;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Clientes;
import br.com.eagleeye.service.CadastrarCliente;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.Functions;


@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Clientes repositorio = new Clientes();
	Cliente cliente = new Cliente();
	ClienteContato clienteContatoNew = new ClienteContato();
	CadastrarCliente cadastar = new CadastrarCliente() ;
	List<ClienteContato> clienteContato;
	boolean incluindoCLienteContato = false;
	private int index;




	public ClienteBean() {
	}
	@PostConstruct
	public void init(){
		// alterar
		if(!isAlterando()){
			valoresDefaultNovoRegistro();
		}
	}
	public void valoresDefaultNovoRegistro(){
			cliente.setDtCadastro(new Timestamp(System.currentTimeMillis()));
			cliente.setAtivo(true);
	}
	public boolean isAlterando(){
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){
			cliente = repositorio.get(Long.valueOf(id));
			return true;
		}
		return false;
	}
	public ClienteContato getClienteContatoNew() {
		return clienteContatoNew;
	}

	public void setClienteContatoNew(ClienteContato clienteContatoNew) {
		this.clienteContatoNew = clienteContatoNew;
	}

	public CadastrarCliente getCadastar() {
		return cadastar;
	}

	public void setCadastar(CadastrarCliente cadastar) {
		this.cadastar = cadastar;
	}


	public String gravar(){
		
		
		String ret = cadastar.adicionar(cliente); 
		if(ret.equals("")){
			cliente = new Cliente();
			new Mensagem().mensagensGeralAviso("Cliente salvo com sucesso!");
			return "ListaCliente";
		}else{
			new Mensagem().mensagensGeralErro("Erro:",ret);
		}
		return "";
	}
	public void cancelar(){
		cliente = new Cliente();
	}
	public void excluirPessoaContato(ClienteContato clienteContato) {
		cliente.getClienteContato().remove(clienteContato);
	}
	public String gravarPessoaContato() {


		if(clienteContatoNew.getCliente() != null){
			gravarAlterandoClienteContato();
		}else{
			gravarIncluindoClienteContato();
		}

		setIndex(0);
		incluindoCLienteContato = false;
		return "";
	}
	public void gravarIncluindoClienteContato(){
		this.clienteContatoNew.setCliente(cliente);
		//this.clienteContatoNew.setIdContato(getClienteContato() == null?1:getClienteContato().size()+1);

		if (cliente.getClienteContato() == null) {
			cliente.setClienteContato(new HashSet<ClienteContato>());
		}

		if(clienteContatoNew.getCodigo().equals("")){
			clienteContatoNew.setCodigo(Functions.ColocaZeroInicio(getClienteContato() == null?String.valueOf(1):String.valueOf(getClienteContato().size()+1), 6));
		}

		cliente.getClienteContato().add(this.clienteContatoNew);
		this.clienteContatoNew = new ClienteContato();
	}
	public void gravarAlterandoClienteContato(){
		for(ClienteContato contatos : cliente.getClienteContato()){
			if(contatos.getIdContato() == clienteContatoNew.getIdContato()){
				contatos = clienteContatoNew;
				break;
			}
		}
		this.clienteContatoNew = new ClienteContato();
	}
	public void editarPessoaContato(ClienteContato contato){
		clienteContatoNew = contato;
		incluindoCLienteContato = true;
		setIndex(1);
	}
	

	public Cliente getCliente() {
		
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public List<ClienteContato> getClienteContato() {
		clienteContato = cliente.getClienteContato()== null ? new ArrayList<ClienteContato>() : new ArrayList<ClienteContato>(this.cliente.getClienteContato());
		return clienteContato;
	}
	public void setClienteContato(List<ClienteContato> clienteContato) {
		this.clienteContato = clienteContato;
	}
	public boolean habilitarDetalhesContato(){
		return incluindoCLienteContato;
	}
	public void inserirClienteContato(){
		incluindoCLienteContato = true;
		clienteContatoNew = new ClienteContato();
		setIndex(1);
	}
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	public void refresh() {  
		FacesContext context = FacesContext.getCurrentInstance();  
		Application application = context.getApplication();  
		ViewHandler viewHandler = application.getViewHandler();  
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());  
		context.setViewRoot(viewRoot);  
		context.renderResponse();  
	}  


	//@SuppressWarnings("unused")
	//public   EntityManager getManager() {
	//	FacesContext fc = FacesContext.getCurrentInstance();
	//	ExternalContext ec = fc.getExternalContext();
	//	HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	//	return (EntityManager) request.getAttribute("EntityManager");
	//}
}