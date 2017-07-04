package br.com.eagleeye.agenda;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.eagleeye.filters.ControleDeAcesso;
import br.com.eagleeye.filters.JPAFilter;
import br.com.eagleeye.service.NotificacoesEmailAberturaProcesso;
import br.com.eagleeye.util.Functions;

public class TarefasAgendadas implements Job {
	
	static Logger logger = Logger.getLogger(TarefasAgendadas.class);
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		logger.info(Functions.dataParaTexto(new Timestamp(System.currentTimeMillis()), "hh:mm:ss") + "TarefasAgendadas sendo executada");
		logger.info("iniciando Transação");
		
		EntityManager entityManager = new JPAFilter().getEntityManager();
		
		entityManager.getTransaction().begin();
		
		new NotificacoesEmailAberturaProcesso(entityManager).notificar();
		
		try {
			logger.info("commitando Transação");
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			logger.fatal(e.getMessage());
		} finally {
			logger.info("fechando Transação");
			entityManager.close();
		}
		
	}

}
