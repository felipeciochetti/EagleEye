package br.com.eagleeye.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.eagleeye.jpa.Processo;

public class TarefasAgendadas {
	
	public EntityManager entityManager;
	
	public TarefasAgendadas(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Processo> getProcessosPorCampo(String nomeCampo  ,String valor) {
		Query q = entityManager.createQuery("Select u From Processo u Where u."+nomeCampo+" like :campo");
		q.setParameter("campo", valor+"%");
		return q.getResultList();
	}
	public void atualizarProcesso(Processo processo) {
		entityManager.merge(processo);
	}
	public void fecharConexao(){
		entityManager.close();
	}
}
