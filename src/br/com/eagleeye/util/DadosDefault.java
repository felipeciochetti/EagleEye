package br.com.eagleeye.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import br.com.eagleeye.jpa.Moeda;
import br.com.eagleeye.jpa.Ncm;
import br.com.eagleeye.jpa.Pais;
import br.com.eagleeye.jpa.ViaTransporte;
import br.com.eagleeye.repositorio.Versoes;

public class DadosDefault {

	String versao = "";
	Logger logger = Logger.getLogger(DadosDefault.class);
	Versoes repositorio = new Versoes();
	public DadosDefault(EntityManager entityManager) {

		repositorio = new Versoes( entityManager);

	}


	public void dadosDefault(){

		try {
			logger.info("Iniciando Dados Default");
			repositorio.getManager().getTransaction().begin();
		
			versao = repositorio.getVersaoBD();

			if(versao.equals("")){
				carregarPaises();
				carregarNcm();
				carregarViaTransporte();

				repositorio.setVersaoBD("1.1");
			}else if (versao.equals("1.1")){
				carregarMoedas();
				repositorio.setVersaoBD("1.2");
			}

			repositorio.getManager().getTransaction().commit();
			logger.info("Fim Dados Default");
		} catch (Exception e) {
			System.err.println("Erro Dados Default");
			e.printStackTrace();
		}
	}



	

	private void carregarNcm() throws Exception{
		logger.info("...Iniciando carga Ncm...");
		
		List<Ncm> list = (List<Ncm>) repositorio.getManager().createQuery("From Ncm").getResultList();
		if(list.size() > 0){
			return;
		}
		
		List<String> linhas = new ArrayList<String>();
		BufferedReader br = null;		
		try{
			URL url = this.getClass().getResource("default/ncm.txt");

			br = new BufferedReader(new FileReader( url.getFile() ));
			String linha = br.readLine();
			while( linha != null ){
				linhas.add(linha);
				linha = br.readLine();
			}

			int i = 1;
			for(String s : linhas){
				String[] campos = s.split("@");
				Ncm m = new Ncm();
				m.setIdNcm(i++);
				m.setCodigo(campos[0].trim());
				m.setNcm(campos[1].trim());

				repositorio.getManager().persist(m);
			}



		}catch (Exception e) {
			throw e;
		}finally{
			try{
				br.close();
				br = null;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Fim carga Ncm...");
	}
	private void carregarViaTransporte() throws Exception{
		logger.info("...Iniciando carga Via Transporte...");
		
		List<ViaTransporte> list = (List<ViaTransporte>) repositorio.getManager().createQuery("From ViaTransporte").getResultList();
		if(list.size() > 0){
			return;
		}
		
		List<String> linhas = new ArrayList<String>();
		BufferedReader br = null;		
		try{
			URL url = this.getClass().getResource("default/viaTransporte.txt");

			br = new BufferedReader(new FileReader( url.getFile() ));
			String linha = br.readLine();
			while( linha != null ){
				linhas.add(linha);
				linha = br.readLine();
			}

			int i = 1;
			for(String s : linhas){
				String[] campos = s.split(";");
				ViaTransporte m = new ViaTransporte();
				m.setIdViaTransporte(i++);
				m.setCodigo(campos[1].trim());
				m.setViaTransporte(campos[2].trim());

				repositorio.getManager().persist(m);
			}



		}catch (Exception e) {
			throw e;
		}finally{
			try{
				br.close();
				br = null;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Fim carga ViaTransporte...");
	}


	private void carregarPaises() throws Exception{
		logger.info("...Iniciando carga Paises...");

		List<Pais> list = (List<Pais>) repositorio.getManager().createQuery("From Pais").getResultList();
		if(list.size() > 0){
			return;
		}

		List<String> linhas = new ArrayList<String>();
		BufferedReader br = null;		
		try{
			URL url = this.getClass().getResource("default/paises.txt");

			br = new BufferedReader(new FileReader( url.getFile() ));
			String linha = br.readLine();
			while( linha != null ){
				linhas.add(linha);
				linha = br.readLine();
			}

			int i = 1;
			for(String s : linhas){
				String[] campos = s.split(";");
				Pais m = new Pais();
				m.setIdPais(i++);
				m.setCodigo( Functions.ColocaZeroInicio( campos[1].trim(), 3 ) );
				m.setNome(campos[0]);

				repositorio.getManager().persist(m);
			}

		}catch (Exception e) {
			throw e;
		}finally{
			try{
				br.close();
				br = null;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Fim carga Países...");
	}
	private void carregarMoedas() throws Exception{
		logger.info("## Iniciando carga Moedas...");

		List<String> linhas = new ArrayList<String>();
		BufferedReader br = null;		
		try{
			URL url = this.getClass().getResource("default/moedas.txt");

			br = new BufferedReader(new FileReader( url.getFile() ));
			String linha = br.readLine();
			while( linha != null ){
				linhas.add(linha);
				linha = br.readLine();
			}

			int i = 1;
			for(String s : linhas){
				String[] campos = s.split(";");
				Moeda m = new Moeda();
				m.setIdMoeda(i++);
				m.setCodigo( Functions.ColocaZeroInicio( campos[1].trim(), 3 ) );
				m.setNome(campos[0]);

				repositorio.getManager().persist(m);
			}

		}catch (Exception e) {
			throw e;
		}finally{
			try{
				br.close();
				br = null;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("## Fim carga Moedas...");
	}
}
