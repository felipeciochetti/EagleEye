package br.com.eagleeye.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import br.com.eagleeye.util.DadosDefault;

public class JPAFilter implements Filter {
	private static EntityManagerFactory entityManagerFactory;
	Logger logger = Logger.getLogger(JPAFilter.class);



	@Override
	public void init(FilterConfig fc) throws ServletException {
		logger.warn("Criando EM Factory");
		this.entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		
		new DadosDefault(this.entityManagerFactory.createEntityManager()).dadosDefault();
		
	}
	@Override
	public void destroy() {
		logger.warn("Destroindo EM Factory");
		this.entityManagerFactory.close();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)throws IOException, ServletException {
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		
		req.setAttribute("EntityManager", entityManager);
			
		logger.info("iniciando Transação");
		entityManager.getTransaction().begin();

			fc.doFilter(req, res);

		try {
			logger.info("commitando Transação");
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			logger.fatal(e.getMessage());
			throw new ServletException(e.getCause() + e.toString());
		} finally {
			logger.info("fechando Transação");
			entityManager.close();
		}
	}
	public EntityManager  getEntityManager(){
		return this.entityManagerFactory.createEntityManager();
	}
	
}