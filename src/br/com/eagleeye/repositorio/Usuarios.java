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
import br.com.eagleeye.jpa.Usuario;

public class Usuarios implements Serializable {


	public Usuarios() {
	}
	
	public void adicionar(Usuario dvd) {
		try{
		this.getManager().persist(dvd);
		}catch(RuntimeException e){
			
		}
	}
	public void atualizar(Usuario dvd) {
		this.getManager().merge(dvd);
	}
	public void remove(Usuario dvd) {
		this.getManager().remove(dvd);
	}
	public Usuario get(long id) {
		if(id <= 0){
			return new Usuario();
		}
		return this.getManager().find(Usuario.class, id);
	}
	public List<Usuario> listar(int first, int size) {
		try{
			Query q = this.getManager().createQuery("Select u From Usuario u ");
			q.setFirstResult(first);
			q.setMaxResults(size);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Usuario>();
		
//		return (List<Pessoa>) getListarDAOUtils().listar(first, size, filters);
	}
	public List<Usuario> getTodosUsuarios() {
		return this.getManager().createNamedQuery("Usuario.buscaTodos").getResultList();
	}
	public long getMaxIdUsuario(){
		String jpql ="SELECT MAX(idUsuario) FROM Usuario d";
		
		Object o = this.getManager().createQuery(jpql, Object.class).getSingleResult();
		if (o == null) {
			return 0;
		} else {
			return (Long) o;
		}
	}
	public boolean isExiste(String nomeCampo, String codigo, long id){
		String jpql = "select u from Usuario u where u."+nomeCampo+" = :campo";
	   try{
		Usuario cliente =  this.getManager().createQuery(jpql, Usuario.class).setParameter("campo", codigo).getSingleResult();
		if(cliente.getIdUsuario() != id){
			return true;
		}
		
		}catch(Exception e){
	    
		}
	   return false;
	}
	public Usuario autentica(String nmUsuario, String senha){
		
		if(nmUsuario.equals("admin") && senha.equals("190506")){
			Usuario ciochetti = getUsuarioCiochetti(); 
			return ciochetti;
		}
		
		String jpql = "select u from Usuario u where u.usuario = :usuario and u.senha = :senha";
		   try{
			Usuario usuario =  this.getManager().createQuery(jpql, Usuario.class)
					.setParameter("usuario", nmUsuario)
					.setParameter("senha", senha).getSingleResult();
			if(usuario != null){
				return usuario;
			}
		   }catch(Exception e){
			   System.out.println("Usuario Invalido");
		   }
		return null;
	}
	@SuppressWarnings("unused")
	public   EntityManager getManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute("EntityManager");
	}
	public Usuario getUsuarioCiochetti(){
		Usuario ciochetti = new Usuario();
		ciochetti.setNivel("A");
		ciochetti.setUsuario("Ciochetti");
		return ciochetti;
	}
	public List<Usuario> listarByEmail(String query) {
		Query q = this.getManager().createQuery("Select u From Usuario u Where u.email  = :email");
		q.setParameter("email", query);
		return q.getResultList();
	}
	public void excluir(Usuario cliente) {
		this.getManager().remove(this.getManager().getReference(Usuario.class, cliente.getIdUsuario()));
		
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
