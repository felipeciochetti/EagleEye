package br.com.eagleeye.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernatePersistence;
import org.hibernate.internal.SessionImpl;
import org.hibernate.stat.Statistics;

public class JPAUtil {

	static Logger logger = Logger.getLogger(JPAUtil.class);

	public final static String UNIT_NAME = "persistencia";
	private static EntityManagerFactory entityManagerFactory;
	private static Properties props;
	private static final String NOME_FILE = "configBD.properties";
	public static Map<String, Object> configPersistence = new HashMap<String, Object>();

	//TRUE apenas para desenvolvimento. 
	//Para liberar versao, DEVERA SER FALSE
	private static Statistics statistics;

	public static void carregarConfigsBD(String pathConfigBysoft){
		try{
			props = new Properties();
			File f = new File(pathConfigBysoft + NOME_FILE);
			try{
				if ( f.exists() && f.isFile() ){
					try {  
						props.load( new FileInputStream( ContextUtils.getInstance().getPathConfigEagle() + NOME_FILE ) );  
					} catch (Exception ex) {  
						ex.printStackTrace();  
					}  
					
				}else{
					//carregarFixo();
				}
			}catch(Exception ee){
				throw ee;
			}finally{
				f = null;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void setPostGre(){
		configPersistence.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		configPersistence.put("javax.persistence.jdbc.driver", "org.postgresql.Driver" );
		configPersistence.put("javax.persistence.jdbc.url", "jdbc:postgresql://" + props.getProperty("ip.server.bd") + ":" + props.getProperty("porta.server.bd") + "/" + props.getProperty("nome.bd") );
	}

	public static void createEntityManagerFactory(){
		if ( props == null ){
			System.err.println(">> Arquivo de configuracao do BD nao foi carregado !");
		}else{

			if ( "true".equalsIgnoreCase(props.getProperty("datasource.use")) ){

				logger.info(">> Using DATA SOURCE...");

				if ( "true".equalsIgnoreCase(props.getProperty("datasource.test")) ){
					logger.info(">>>> Testing DATA SOURCE...");
					try{
						Context initContext = new InitialContext();  
						Context envContext  = (Context)initContext.lookup("java:/comp/env");  
						DataSource ds = (DataSource)envContext.lookup(props.getProperty("datasource.name"));
						Connection conn = ds.getConnection();
						PreparedStatement ps = conn.prepareStatement("Select Count(*) From Usuario");
						if ( ps.execute() ){
							logger.info(">> Executou");
						}else{
							logger.info(">> NAO Executou");
						}
						ResultSet rs = ps.getResultSet();
						if ( rs != null ){
							rs.next();
							logger.info(">>CONEC: " + rs.getLong(1));
						}else{
							logger.info(">>CONEC: NULL");
						}

					}catch(Exception e){
						e.printStackTrace();
					}

					return;
				}else{				
					configPersistence.put(HibernatePersistence.NON_JTA_DATASOURCE, props.getProperty("datasource.name") );
				}

			}else{

				logger.info(">> USING CONFIGBD.properties...");

				 if ( props.getProperty("tipo.bd").equalsIgnoreCase("POSTGRE") ){
					setPostGre();
				}

				configPersistence.put("javax.persistence.jdbc.user", props.getProperty("user.bd") );
				configPersistence.put("javax.persistence.jdbc.password", props.getProperty("password.bd") );
			}


			configPersistence.put("hibernate.hbm2ddl.auto", "update");			
			configPersistence.put("hibernate.show_sql", "false");
			configPersistence.put("hibernate.connection.autocommit", "false");

			if ( "true".equalsIgnoreCase( props.getProperty("audit") ) ){
				configPersistence.put("hibernate.listeners.envers.autoRegister", "true" );
			}else{
				configPersistence.put("hibernate.listeners.envers.autoRegister", "false" );
			}


			boolean usarStatistics = false;

			for( Object key : props.keySet() ){
				if ( key.toString().startsWith("JPA.") ){
					configPersistence.put( key.toString().substring(4, key.toString().length()), props.getProperty(key.toString()) );
					if ( key.toString().contains("hibernate.generate_statistics") ){
						if ( props.getProperty(key.toString()).equalsIgnoreCase("true") ){
							usarStatistics = true;
						}
					}
				}
			}

			entityManagerFactory = Persistence.createEntityManagerFactory(UNIT_NAME, configPersistence);

			if( usarStatistics ){
				EntityManager em = entityManagerFactory.createEntityManager();
				statistics = ((SessionImpl)em.getDelegate()).getSessionFactory().getStatistics();
				em.close();
			}


		}
	}


	public static Statistics getStatistics(){
		return statistics;
	}

	public static void printStatistics(String identificadorOpcional){
		if ( statistics != null ){
			logger.info(identificadorOpcional + "   ***********");
			logger.info("open: " + statistics.getSessionOpenCount());
			logger.info("close: " + statistics.getSessionCloseCount());
			logger.info("trans: " + statistics.getTransactionCount());
			logger.info("trans suc: " + statistics.getSuccessfulTransactionCount());
			logger.info("**************************************");
		}
	}

	public static void destroyEntityManagerFactory(){
		if ( entityManagerFactory != null ){
			entityManagerFactory.close();
		}
	}

	public static EntityManagerFactory getEntityManagerFactory(){
		return entityManagerFactory;
	}

}
