package br.com.eagleeye.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sun.jmx.snmp.Timestamp;

import br.com.eagleeye.jpa.Numerario;
import br.com.eagleeye.repositorio.Numerarios;

public class CadastrarNumerario implements Serializable{


	Numerarios dao = new Numerarios();


	public CadastrarNumerario() {
	}

	public String adicionar(Numerario numerario){


		String ret = validaDadosNumerario(numerario);
		necessarioCalcularNumerario(numerario);

		if(!ret.equals("")){
			return ret;
		}

		try {
			if(numerario.getIdNumerario() <= 0 ){
				dao.adicionar(numerario);
			}else{
				dao.atualizar(numerario);
			}
			return "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private String validaDadosNumerario(Numerario numerario) {
		// TODO Auto-generated method stub
		return "";
	}

	private void necessarioCalcularNumerario(Numerario numerario) {

		if(numerario.getIdNumerario() > 0){
			Numerario compare = dao.get(numerario.getIdNumerario());

			boolean isAlterou = isObjAlterado(compare,numerario);

			if(isAlterou){
				numerario.setSnCalculado("N");
				numerario.setDtCalculo(null);
				zerarCamposCalculo(numerario);
			}

		}



	}

	public boolean isObjAlterado(Numerario obj1,Numerario obj2){
		
		Field[] fields = Numerario.class.getDeclaredFields();
		try{
		for(int i = 0; i < fields.length; i++){
			
			if(fields[i].getType().equals(BigDecimal.class)){
				if(((BigDecimal)fields[i].get(obj1)).doubleValue() != ((BigDecimal)fields[i].get(obj2)).doubleValue() ){
					return true;
				}
			}
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}

		return false;
		
	}
	public void zerarCamposCalculo(Numerario numerario){
		try {

			List<String> listCamposReais = getListCamposReais();


			Field[] fields = Numerario.class.getDeclaredFields();

			for(int i = 0; i < fields.length; i++){
				if(fields[i].getName().contains("Dolar")){
					fields[i].set(numerario, new BigDecimal(0));
				}else if(listCamposReais.contains(fields[i].getName())){
					fields[i].set(numerario, new BigDecimal(0));
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public List<String> getListCamposReais(){
		List<String> listCamposReais = new ArrayList<String>();
		
		listCamposReais.add("vlFobReais");
		listCamposReais.add("vlFreteIntReais");
		listCamposReais.add("vlSeguroReais");
		listCamposReais.add("vlCifReais");
		listCamposReais.add("vlAduaneiroReais");
		listCamposReais.add("vlImpostoImpReais");
		listCamposReais.add("vlImpostoProdReais");
		listCamposReais.add("vlPisReais");
		listCamposReais.add("vlCofinsReais");
		listCamposReais.add("vlIcmsReais");
		listCamposReais.add("vlTotalImpostosReais");
		listCamposReais.add("vlTotalDespesaMvwReais");
		listCamposReais.add("vlTotalDespesaImpReais");
		
		
		
		
		
		
		return listCamposReais;
		
	}
}
