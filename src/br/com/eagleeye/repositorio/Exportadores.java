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
import br.com.eagleeye.jpa.Exportador;

public class Exportadores implements Serializable {

	Processos repositorioProcesso = new Processos();
	public Exportadores() {
	}
	
	public void adicionar(Exportador dvd) {
		this.getManager().persist(dvd);
	}
	public void atualizar(Exportador dvd) {
		this.getManager().merge(dvd);
	}
	public void remove(Exportador dvd) {
		this.getManager().remove(dvd);
	}
	public Exportador get(long id) {
		if(id <= 0){
			return new Exportador();
		}
		return this.getManager().find(Exportador.class, id);
	}
	public List<Exportador> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Exportador u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Exportador>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Exportador> listarByNome(String query, int max) {
		Query q = this.getManager().createQuery("Select u From Exportador u Where upper(u.nome) like :nome");
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("nome", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<Exportador> getTodosExportadores() {
		return this.getManager().createNamedQuery("Exportador.buscaTodos").getResultList();
	}
	public long getMaxIdExportador(){
		String jpql ="SELECT MAX(idExportador) FROM Exportador d";
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, String codigo,long id){
		String jpql = "select u from Exportador u where u."+nomeCampo+" = :campo";
	   try{
		Exportador exportador =  this.getManager().createQuery(jpql, Exportador.class).setParameter("campo", codigo).getSingleResult();
		if(exportador.getIdExportador() != id){
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

	public boolean excluir(Exportador exportador) {
		
		if(!validaDadosAntesExcluir(exportador.getIdExportador())){
			this.getManager().remove(this.getManager().getReference(Cliente.class, exportador.getIdExportador()));
			return true;
		}else{
			return false;
		}
		
	}
	public boolean validaDadosAntesExcluir(Object id){
		
		boolean is = repositorioProcesso.isExiste("exportador.idExportador", id);
		
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
