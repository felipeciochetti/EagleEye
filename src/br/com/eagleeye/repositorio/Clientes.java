package br.com.eagleeye.repositorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import br.com.eagleeye.jpa.Cliente;

public class Clientes implements Serializable {

	Processos repositorioProcesso = new Processos();
	
	
	public Clientes() {
	}
	
	public void adicionar(Cliente dvd) {
		this.getManager().persist(dvd);
	}
	public void atualizar(Cliente dvd) {
		this.getManager().merge(dvd);
	}
	public void remove(Cliente dvd) {
		this.getManager().remove(dvd);
	}
	public Cliente get(long id) {
		if(id <= 0){
			return new Cliente();
		}
		return this.getManager().find(Cliente.class, id);
	}
	public List<Cliente> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Cliente u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Cliente>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Cliente> listarByNome(String query, int max) {
		Query q = this.getManager().createQuery("Select u From Cliente u Where upper(u.nome) like :nome");
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("nome", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<Cliente> getTodosClientes() {
		return this.getManager().createNamedQuery("Cliente.buscaTodos").getResultList();
	}
	public long getMaxIdCliente(){
		String jpql ="SELECT MAX(idCliente) FROM Cliente d";
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, String codigo,long id){
		String jpql = "select u from Cliente u where u."+nomeCampo+" = :campo";
	   try{
		Cliente cliente =  this.getManager().createQuery(jpql, Cliente.class).setParameter("campo", codigo).getSingleResult();
		if(cliente.getIdCliente() != id){
			return true;
		}
		
		}catch(Exception e){
	    
		}
	   return false;
	}
	
	@SuppressWarnings("unused")
	public   EntityManager getManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute("EntityManager");
	}

	public boolean excluir(Cliente cliente) {
		
		if(!validaDadosAntesExcluir(cliente.getIdCliente())){
			this.getManager().remove(this.getManager().getReference(Cliente.class, cliente.getIdCliente()));
			return true;
		}else{
			return false;
		}
		
	}
	public boolean validaDadosAntesExcluir(Object id){
		
		boolean is = repositorioProcesso.isExiste("cliente.idCliente", id);
		
		return is;
	}
	private void setParams(Map filters, Query q) {
		if (filters == null || filters.keySet().size() == 0) {
			return;
		}
		for (Object o : filters.keySet()) {
			q.setParameter(o.toString().replace(".", "_"), filters.get(o).toString().toUpperCase() + "%");
		}
	}
	private String getWhere(Map filters) {
		if (filters == null || filters.keySet().size() == 0) {
			return "";
		}
		StringBuffer where = new StringBuffer(" Where ");
		for (Object o : filters.keySet()) {
			where.append("upper(u." + o.toString() + ") like :" + o.toString().replace(".", "_") + " and ");
		}
		where.delete(where.length() - 5, where.length());
		return where.toString();
	}
}
