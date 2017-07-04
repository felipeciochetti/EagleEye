package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.repositorio.Clientes;
import br.com.eagleeye.service.ExcluirCliente;
import br.com.eagleeye.table.ClienteTable;

@ManagedBean
@ViewScoped
public class ClienteListaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Clientes cliente = new Clientes();
	private Cliente selectCliente  ;
	private Cliente clienteExcluir;
	ExcluirCliente excluir = new ExcluirCliente();
	List<Cliente> lista;
	ClienteTable list;
	
	public ClienteListaBean() {
	}

	@PostConstruct
	public void init(){
		list = new ClienteTable(cliente);
	}
	
	public List<Cliente> getLista() {
		return cliente.getTodosClientes();
	}
	public ClienteTable getList() {
		return list ;//= new ClienteTable(getLista());
	}
	
	public String preparaAlterar(Cliente cliente) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("clienteAlterar", String.valueOf(cliente.getIdCliente()));
		return "CadastroCliente.xhtml?faces-redirect=true";
	}
	public void preparaExcluir(Cliente cliente) {
		this.clienteExcluir = cliente;
	}

	public void excluir() {
		excluir.excluir(clienteExcluir);
		clienteExcluir = null;
		list = new ClienteTable(cliente);
	}
	
	public void setList(ClienteTable list) {
		this.list = list;
	}

	public Cliente getSelectCliente() {
		return selectCliente;
	}

	public void setSelectCliente(Cliente selectCliente) {
		this.selectCliente = selectCliente;
	}

	public Cliente getClienteExcluir() {
		return clienteExcluir;
	}

	public void setClienteExcluir(Cliente clienteExcluir) {
		this.clienteExcluir = clienteExcluir;
	}
	
	

	

}
