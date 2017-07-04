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

import br.com.eagleeye.jpa.Versao;

public class Versoes implements Serializable {


	/**
	 * 
	 */
	EntityManager entityManager;
	
	private static final long serialVersionUID = 1L;
	public Versoes() {
	}
	
	public Versoes(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public void adicionar(Versao processo) {
		this.getManager().persist(processo);
	}
	public void atualizar(Versao processo) {
		this.getManager().merge(processo);
	}
	public void remove(Versao processo) {
		this.getManager().remove(processo);
	}
	public String getVersaoBD(){
		Query q = this.getManager().createNativeQuery("Select max(idVersao) From Versao");
		Object id = q.getSingleResult();
		long i = (id == null ? 0 : Integer.parseInt( id.toString() ) );
		if ( i == 0 ){
			return "";
		}else{
			Versao versaoBD = this.getManager().find(Versao.class, i);
			return versaoBD.getVersao()==null?"":versaoBD.getVersao();
		}

	}

	public void setVersaoBD(String versao){
		Object id = null;

		Versao versaoBD = new Versao();
		versaoBD.setVersao(versao);
		System.out.println("Atualiza versão BD : " + versao);
		adicionar(versaoBD);
		
	}
	
	public  EntityManager getManager() {
			return entityManager;
		}
}
