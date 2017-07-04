package br.com.eagleeye.teste;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Test;

import br.com.eagleeye.jpa.Numerario;

public class TesteCalculoNumerario  {
	
	static Logger logger = Logger.getLogger(TesteCalculoNumerario.class);
	Numerario numerario = new Numerario();
	
	@Test
	public void testRemoverZeros(){
		
		
		
	}
	
	public void calculaFOB(){
		double vlFobReais = numerario.getVlFobMoeda().doubleValue() * numerario.getVlTaxaMoedaFob().doubleValue();
		double vlFobDolar = vlFobReais / numerario.getVlTaxaMoedaFob().doubleValue();
		
		numerario.setVlFobReais(new BigDecimal(vlFobReais));
		numerario.setVlFobDolar(new BigDecimal(vlFobDolar));
	
	}
	
}
