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
import br.com.eagleeye.jpa.Ncm;

public class Ncms implements Serializable {


	public Ncms() {
	}
	
	public Ncm get(long id) {
		if(id <= 0){
			return new Ncm();
		}
		return this.getManager().find(Ncm.class, id);
	}
	
	public List<Ncm> listarNome(String query, int max) {
		
		Query q = this.getManager().createQuery("Select u From Ncm u Where  upper(u.ncm) like :ncm" );
		
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("ncm", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<Ncm> listarCodigo(String query, int max) {
		
		Query q = this.getManager().createQuery("Select u From Ncm u Where  upper(u.codigo) like :codigo" );
		
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("codigo", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<Ncm> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Ncm u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Ncm>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Ncm> getTodosNcms() {
		return this.getManager().createNamedQuery("Ncm.buscaTodos").getResultList();
	}
	public long getMaxIdNcm(){
		String jpql ="SELECT MAX(idNcm) FROM Ncm d";
		
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, String codigo, long id){
		String jpql = "select u from Ncm u where u."+nomeCampo+" = :campo";
	   try{
		Ncm evento =  this.getManager().createQuery(jpql, Ncm.class).setParameter("campo", codigo).getSingleResult();
		if(evento.getIdNcm() != id){
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

	public void excluir(Ncm evento) {
		this.getManager().remove(this.getManager().getReference(Ncm.class, evento.getIdNcm()));
		
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
