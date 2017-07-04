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

import br.com.eagleeye.jpa.Transportadora;

public class Transportadoras implements Serializable {


	public Transportadoras() {
	}
	
	public void adicionar(Transportadora dvd) {
		this.getManager().persist(dvd);
	}
	public void atualizar(Transportadora dvd) {
		this.getManager().merge(dvd);
	}
	public void remove(Transportadora dvd) {
		this.getManager().remove(dvd);
	}
	public Transportadora get(long id) {
		if(id <= 0){
			return new Transportadora();
		}
		return this.getManager().find(Transportadora.class, id);
	}
	public List<Transportadora> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Transportadora u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Transportadora>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Transportadora> listarByNome(String query, int max) {
		Query q = this.getManager().createQuery("Select u From Transportadora u Where upper(u.nome) like :nome");
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("nome", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<Transportadora> getTodosTransportadoras() {
		return this.getManager().createNamedQuery("Transportadora.buscaTodos").getResultList();
	}
	public long getMaxIdTransportadora(){
		String jpql ="SELECT MAX(idTransportadora) FROM Transportadora d";
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, String codigo,long id){
		String jpql = "select u from Transportadora u where u."+nomeCampo+" = :campo";
	   try{
		Transportadora cliente =  this.getManager().createQuery(jpql, Transportadora.class).setParameter("campo", codigo).getSingleResult();
		if(cliente.getIdTransportadora() != id){
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

	public void excluir(Transportadora cliente) {
		this.getManager().remove(this.getManager().getReference(Transportadora.class, cliente.getIdTransportadora()));
		
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
