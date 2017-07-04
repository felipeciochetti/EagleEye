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
import br.com.eagleeye.jpa.Numerario;

public class Numerarios implements Serializable {


	public Numerarios() {
	}
	
	public void adicionar(Numerario dvd) {
		try{
		this.getManager().persist(dvd);
		}catch(RuntimeException e){
			
		}
	}
	public void atualizar(Numerario dvd) {
		this.getManager().merge(dvd);
	}
	public void remove(Numerario dvd) {
		this.getManager().remove(dvd);
	}
	public Numerario get(long id) {
		if(id <= 0){
			return new Numerario();
		}
		return this.getManager().find(Numerario.class, id);
	}
	public List<Numerario> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Numerario u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Numerario>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Numerario> getTodosNumerarios() {
		return this.getManager().createNamedQuery("Numerario.buscaTodos").getResultList();
	}
	public long getMaxIdNumerario(){
		String jpql ="SELECT MAX(idNumerario) FROM Numerario d";
		
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, String codigo, long id){
		String jpql = "select u from Numerario u where u."+nomeCampo+" = :campo";
	   try{
		Numerario cliente =  this.getManager().createQuery(jpql, Numerario.class).setParameter("campo", codigo).getSingleResult();
		if(cliente.getIdNumerario() != id){
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
	public List<Numerario> listarByEmail(String query) {
		Query q = this.getManager().createQuery("Select u From Numerario u Where u.email  = :email");
		q.setParameter("email", query);
		return q.getResultList();
	}
	public boolean excluir(Numerario cliente) {
		this.getManager().remove(this.getManager().getReference(Numerario.class, cliente.getIdNumerario()));
		return true;
		
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
