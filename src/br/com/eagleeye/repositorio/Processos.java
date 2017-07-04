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
import br.com.eagleeye.jpa.Processo;

public class Processos implements Serializable {


	public Processos() {
	}
	
	public void adicionar(Processo processo) {
		this.getManager().persist(processo);
	}
	public void atualizar(Processo processo) {
		this.getManager().merge(processo);
	}
	public void remove(Processo processo) {
		this.getManager().remove(processo);
	}

	public Processo getProcessoPorCliente(int id, long idCliente) {
		if(id <= 0){
			return new Processo();
		}
		
		try{
		// validando se o usuario cliente é o msm do processo
		Processo processo = this.getManager().find(Processo.class, id);
		if(processo.getCliente().getIdCliente() == idCliente){
			return processo;
		}
		return new Processo();
		
		}catch(Exception e){
			return new Processo();
		}
	}
	
	public Processo get(int id) {
		if(id <= 0){
			return new Processo();
		}
		return this.getManager().find(Processo.class, id);
	}
	
	public List<Processo> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Processo u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Processo>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Processo> listarPorCliente(int idCliente ,int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Processo u where u.cliente.idCliente = :idCliente ");
			q.setParameter("idCliente", idCliente);
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Processo>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Processo> listarByInvoice(String query, int max) {
		Query q = this.getManager().createQuery("Select u From Processo u Where upper(u.invoice) like :invoice");
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("nome", query.toUpperCase() + "%");
		return q.getResultList();
	}
	
	public List<Processo> getTodosProcessosNaoCancelados() {
		return this.getManager().createNamedQuery("Processo.buscaTodosNaoCancelados").getResultList();
	}
	public List<Processo> getProcessosPorCampo(String nomeCampo  ,String valor) {
		Query q = this.getManager().createQuery("Select u From Processo u Where u."+nomeCampo+" like :campo");
		q.setParameter("campo", valor+"%");
		return q.getResultList();
	}
	public List<Processo> getProcessosPorCampos(Map filters) {
		Query q = this.getManager().createQuery("Select u From Processo" + getWhere(filters));
		setParams(filters, q);
		
		return q.getResultList();
	}
	public List<Processo> listarByReferenciaMvw(String query, int max) {
		Query q = this.getManager().createQuery("Select u From Processo u Where upper(u.referenciaMvw) like :referenciaMvw");
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("referenciaMvw", query.toUpperCase() + "%");
		return q.getResultList();
	}
	public List<Processo> listarByReferenciaMvwRestricaoNumerario(String query, int max) {
		String restricao = " and not exists (From Numerario n where n.processo.idProcesso = u.idProcesso )";
		
		Query q = this.getManager().createQuery("Select u From Processo u Where upper(u.referenciaMvw) like :referenciaMvw" + restricao );
		q.setFirstResult(0);
		q.setMaxResults(max);
		q.setParameter("referenciaMvw", query.toUpperCase() + "%");
		return q.getResultList();
		
	}
	public List<Processo> getProcessosPorCampos(String where , List<Serializable> params) {
		Query q = this.getManager().createQuery("From Processo " + where);
		int i = 1;
		for (Serializable p : params) {
				q.setParameter(i++, p );
		}
		
		return q.getResultList();
	}
	public List<Processo> getProcessosPorCampoECliente(long idCliente , String nomeCampo  ,String valor) {
		Query q = this.getManager().createQuery("Select u From Processo u Where u.cliente.idCliente = :idCliente and  u."+nomeCampo+" like :campo");
		q.setParameter("idCliente", idCliente);
		q.setParameter("campo", valor+"%");
		return q.getResultList();
	}
	public List<Processo> getProcessosPorCliente(long idCliente) {
		Query q = this.getManager().createQuery("Select u From Processo u Where u.cliente.idCliente = :idCliente");
		q.setParameter("idCliente", idCliente);
		return q.getResultList();
	}
	public long getSequencialProcesso(){
		String jpql ="SELECT MAX(sequencialProcesso) FROM Processo d";
		
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, Object codigo){
		String jpql = "select u from Processo u where u."+nomeCampo+" = :campo";
	   try{
		List<Processo> processo =  (List<Processo>) this.getManager().createQuery(jpql, Processo.class).setParameter("campo", codigo).getResultList();
		
		return processo.size() > 0;
		
		}catch(Exception e){
			return false;
		}
	}
	public int getMaxIdProcesso(){
		String jpql ="SELECT MAX(idProcesso) FROM Processo d";
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Integer) o;
		}
	}
	@SuppressWarnings("unused")
	public   EntityManager getManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute("EntityManager");
	}

	public void excluir(Processo cliente) {
		this.getManager().remove(this.getManager().getReference(Processo.class, cliente.getIdProcesso()));
		
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
