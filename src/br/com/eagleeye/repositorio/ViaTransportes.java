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
import br.com.eagleeye.jpa.ViaTransporte;

public class ViaTransportes implements Serializable {


	public ViaTransportes() {
	}
	
	public ViaTransporte get(long id) {
		if(id <= 0){
			return new ViaTransporte();
		}
		return this.getManager().find(ViaTransporte.class, id);
	}
	
	public List<ViaTransporte> listarNome(String query, int max) {
		
		Query q = this.getManager().createQuery("Select u From ViaTransporte u Where  upper(u.viaTransporte) like :nome" );
		
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("nome", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<ViaTransporte> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From ViaTransporte u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<ViaTransporte>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public   EntityManager getManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute("EntityManager");
	}

	public void excluir(ViaTransporte evento) {
		this.getManager().remove(this.getManager().getReference(ViaTransporte.class, evento.getIdViaTransporte()));
		
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
