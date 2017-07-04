package br.com.eagleeye.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.sun.jmx.snmp.Timestamp;

import br.com.eagleeye.jpa.Numerario;
import br.com.eagleeye.repositorio.Numerarios;

public class CalculoNumerario {
	
	
	Logger logger = Logger.getLogger(CalculoNumerario.class);
	
	Numerario numerario;
	Numerarios repositorio = new Numerarios();

	private Method setter;
	
		public CalculoNumerario(Numerario numerario ) {
			this.numerario = numerario;
		}
		
		
		public String calculoNumerario(){
			
			logger.info("inicio do calculo do numerario...");
			
			String retorno = "";
			try{
			
			if(numerario == null){
				throw new Exception("Numerário Inválido");
			}
			String ret = validaCamposObrigatorio(); 
			if(!ret.equals("")){
				throw new Exception("Campos obrigatorios: " + ret);
			}
			
			tratarNull();
			
			
			calculaFOB();
			calculaFreteInternacional();
			calculaSeguro();
			calculaCIF();
			calculaValorAduaneiro();
			calculaValorTaxaSiscomex();
			calculaImpostoImportacao();
			calculaImpostoProdutos();
			calculaPIS();
			calculaCofins();
			
			
			calculaAfrmm();
			
			calculaIcms();
			calculaTotalImpostos();
			calculaDespesaFreteInternacional();
			calculaLiberacaoBL();
			calculaDesconsolidacao();
			calculaTaxaIsps();
			calculaTaxaSiscarga();
			calculaCapatazia();
			
			calculaArmazenagemPorto();
			calculaTransporteRemocao();
			calculaArmazenagemPortoSeco();
			calculaTransporteEntrega();
			calculaDesembaraco();
			calculaDespesasMVW();
			
			calculaTotalDespesasMVW();
			calculaTotalDespesasImpostos();
			
			numerario.setSnCalculado("S");
			numerario.setDtCalculo(new Timestamp(System.currentTimeMillis()).getDate());
			
			salvarNumerario();
			
			logger.info("fim do calculo do numerario...");
			
			return retorno;
			
			}catch(Exception e){
				e.printStackTrace();
				return e.getMessage();
			}
		}
		public void salvarNumerario(){

			if(numerario.getIdNumerario() == 0){
				repositorio.adicionar(numerario);
			}else{
				repositorio.atualizar(numerario);
			}
		}
		public void calculaFOB(){
			double vlFobReais = numerario.getVlFobMoeda().doubleValue() * numerario.getVlTaxaMoedaFob().doubleValue();
			double vlFobDolar = vlFobReais / numerario.getVlTaxaMoedaFob().doubleValue();
			
			numerario.setVlFobReais(new BigDecimal(vlFobReais));
			numerario.setVlFobDolar(new BigDecimal(vlFobDolar));
		
		}
		public void calculaFreteInternacional(){
			double vlFreteReais = numerario.getVlFreteIntMoeda().doubleValue() * numerario.getVlTaxaMoedaFrete().doubleValue();
			double vlFreteDolar = vlFreteReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlFreteIntReais(new BigDecimal(vlFreteReais));
			numerario.setVlFreteIntDolar(new BigDecimal(vlFreteDolar));
		}
		public void calculaSeguro(){
			double vlSeguroReais = (numerario.getVlFobReais().doubleValue() + numerario.getVlFreteIntReais().doubleValue()) * 0.25 / 100;
			double vlFreteDolar = vlSeguroReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlSeguroReais(new BigDecimal(vlSeguroReais));
			numerario.setVlSeguroDolar(new BigDecimal(vlFreteDolar));
		}
		
		public void calculaCIF(){
			double vlCifReais = numerario.getVlFobReais().doubleValue() + numerario.getVlFreteIntReais().doubleValue() + numerario.getVlSeguroReais().doubleValue();
			double vlCifDolar = vlCifReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlCifReais(new BigDecimal(vlCifReais));
			numerario.setVlCifDolar(new BigDecimal(vlCifDolar));
		}
		
		public void calculaValorAduaneiro(){
			double vlAduaneiroReais = numerario.getVlCifReais().doubleValue() ;
			double vlAduaneiroDolar = vlAduaneiroReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlAduaneiroReais(new BigDecimal(vlAduaneiroReais));
			numerario.setVlAduaneiroDolar(new BigDecimal(vlAduaneiroDolar));
		}
		
		public void calculaValorTaxaSiscomex(){
			//double vlSiscomexReais = numerario.getVlCifReais().doubleValue() + numerario.getVlCifDolar().doubleValue() + numerario.getVlSeguroReais().doubleValue();
			double vlSiscomexDolar = numerario.getVlTxSiscomexReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTxSiscomexDolar(new BigDecimal(vlSiscomexDolar));
		}
		
		public void calculaImpostoImportacao(){
			double vlImpReais = (numerario.getVlAduaneiroReais().doubleValue() * numerario.getVlImpostoImpMoeda().doubleValue()) / 100;
			double vlImpDolar = vlImpReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlImpostoImpReais(new BigDecimal(vlImpReais));
			numerario.setVlImpostoImpDolar(new BigDecimal(vlImpDolar));
		}
		

		public void calculaImpostoProdutos(){
			double vlImpReais = (numerario.getVlAduaneiroReais().doubleValue() + numerario.getVlImpostoImpReais().doubleValue()) * numerario.getVlImpostoProdMoeda().doubleValue()/100 ;
			double vlImpDolar = vlImpReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlImpostoProdReais(new BigDecimal(vlImpReais));
			numerario.setVlImpostoProdDolar(new BigDecimal(vlImpDolar));
		}
		
		public void calculaPIS(){
			double vlPisReais = numerario.getVlAduaneiroReais().doubleValue()  * numerario.getVlPisMoeda().doubleValue()/100 ;
			double vlPisDolar = vlPisReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlPisReais(new BigDecimal(vlPisReais));
			numerario.setVlPisDolar(new BigDecimal(vlPisDolar));
		}
		
		public void calculaCofins(){
			double vlCofinsReais = numerario.getVlAduaneiroReais().doubleValue()  * numerario.getVlCofinsMoeda().doubleValue()/100 ;
			double vlCofinsDolar = vlCofinsReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlCofinsReais(new BigDecimal(vlCofinsReais));
			numerario.setVlCofinsDolar(new BigDecimal(vlCofinsDolar));
		}
		
		public void calculaIcms(){
			
			/**
			 * ajustar
			 */
			
			double vlCofinsReais = numerario.getVlAduaneiroReais().doubleValue()+
									numerario.getVlTxSiscomexReais().doubleValue()+
									numerario.getVlImpostoImpReais().doubleValue()+
								    numerario.getVlImpostoProdReais().doubleValue()+
								    numerario.getVlPisReais().doubleValue()+
								    numerario.getVlCofinsReais().doubleValue();
			
			vlCofinsReais = vlCofinsReais * 0.18;
			double fator = 1-0.18;
			double result = vlCofinsReais / fator;
			
			vlCofinsReais = result ;			
									
									
			double vlCofinsDolar = vlCofinsReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlIcmsReais(new BigDecimal(vlCofinsReais));
			numerario.setVlIcmsDolar(new BigDecimal(vlCofinsDolar));
		}
		
		public void calculaTotalImpostos(){
			double vlTotalImpReais = numerario.getVlTxSiscomexReais().doubleValue() +
										numerario.getVlImpostoImpReais().doubleValue()+
										numerario.getVlImpostoProdReais().doubleValue()+
										numerario.getVlPisReais().doubleValue() +
										numerario.getVlCofinsReais().doubleValue() +
										numerario.getVlIcmsReais().doubleValue() ;
										
										
			
								
			double vlTotalImpDolar = vlTotalImpReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTotalImpostosReais(new BigDecimal(vlTotalImpReais));
			numerario.setVlTotalImpostosDolar(new BigDecimal(vlTotalImpDolar));
		}
		
		public void calculaDespesaFreteInternacional(){
			double vlDespesaDolar = numerario.getVlDesconsoReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlDespFreteIntDolar(new BigDecimal(vlDespesaDolar));
		}
		
		public void calculaLiberacaoBL(){
			double vlLibDolar = numerario.getVlLibBlReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlLibBlDolar(new BigDecimal(vlLibDolar));
		}
		
		public void calculaDesconsolidacao(){
			double vlLibDolar = numerario.getVlDesconsoReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlDesconsoDolar(new BigDecimal(vlLibDolar));
		}
		
		public void calculaTaxaIsps(){
			double vlTaxaIspsDolar = numerario.getVlTxIspsReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTxIspsDolar(new BigDecimal(vlTaxaIspsDolar));
		}
		
		public void calculaTaxaSiscarga(){
			double vlTaxasiscargaDolar = numerario.getVlTxSiscargaReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTxSiscargaDolar(new BigDecimal(vlTaxasiscargaDolar));
		}
		
		public void calculaCapatazia(){
			double vlCapatazaiDolar = numerario.getVlCapataziaReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlCapataziaDolar(new BigDecimal(vlCapatazaiDolar));
		}
		
		public void calculaAfrmm(){
			double vlAfrmmDolar = numerario.getVlAfrmmReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlAfrmmDolar(new BigDecimal(vlAfrmmDolar));
		}
		
		public void calculaArmazenagemPorto(){
			double vlArmazPortoDolar = numerario.getVlArmazPortoReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlArmazPortoDolar(new BigDecimal(vlArmazPortoDolar));
		}
		
		public void calculaTransporteRemocao(){
			double vlTranspRemocaoDolar = numerario.getVlTranspRemocaoReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTranspRemocaoDolar(new BigDecimal(vlTranspRemocaoDolar));
		}
		
		public void calculaArmazenagemPortoSeco(){
			double vlArmazPortoSecoDolar = numerario.getVlArmazPortoSecoReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlArmazPortoSecoDolar(new BigDecimal(vlArmazPortoSecoDolar));
		}
		
		public void calculaTransporteEntrega(){
			double vlTranspEntregaDolar = numerario.getVlTranspEntregaReais().doubleValue() * numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTranspEntregaDolar(new BigDecimal(vlTranspEntregaDolar));
		}
		
		public void calculaDesembaraco(){
			double vlDesembaracoDolar = numerario.getVlDesembaracoReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlDesembaracoDolar(new BigDecimal(vlDesembaracoDolar));
		}
		
		public void calculaDespesasMVW(){
			double vlMvwDolar = numerario.getVlMvwReais().doubleValue() / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlMvwDolar(new BigDecimal(vlMvwDolar));
		}
		
		public void calculaTotalDespesasMVW(){
			double vlTotalDespesaMvwReais = numerario.getVlDespFreteIntReais().doubleValue()+
											numerario.getVlLibBlReais().doubleValue() + 
											numerario.getVlDesconsoReais().doubleValue() +
											numerario.getVlTxIspsReais().doubleValue() + 
											numerario.getVlTxSiscargaReais().doubleValue() + 
											numerario.getVlCapataziaReais().doubleValue() + 
											numerario.getVlAfrmmReais().doubleValue() +
											numerario.getVlArmazPortoReais().doubleValue() +
											numerario.getVlTranspRemocaoReais().doubleValue() +
											numerario.getVlArmazPortoSecoReais().doubleValue() +
											numerario.getVlTranspEntregaReais().doubleValue() + 
											numerario.getVlDesembaracoReais().doubleValue() + 
											numerario.getVlMvwReais().doubleValue();
			
				double vlTotalDespesaMvwDolar = vlTotalDespesaMvwReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
											
			numerario.setVlTotalDespesaMvwReais(new BigDecimal(vlTotalDespesaMvwReais));
			numerario.setVlTotalDespesaMvwDolar(new BigDecimal(vlTotalDespesaMvwDolar));
			
		}
		
		public void calculaTotalDespesasImpostos(){
			
			double vlTotalDespesaImpReais = numerario.getVlTotalDespesaMvwReais().doubleValue() + numerario.getVlTotalImpostosReais().doubleValue() ;
			double vlTotalDespesaImpDolar = vlTotalDespesaImpReais / numerario.getVlTaxaMoedaFiscal().doubleValue();
			
			numerario.setVlTotalDespesaImpReais(new BigDecimal(vlTotalDespesaImpReais));
			numerario.setVlTotalDespesaImpDolar(	new BigDecimal(vlTotalDespesaImpDolar));
		}
		
		
		
		
		public String validaCamposObrigatorio(){
			StringBuffer retorno = new StringBuffer("");
			
			if(numerario.getVlTaxaMoedaFob() == null  || numerario.getVlTaxaMoedaFob().doubleValue() == 0){
				retorno.append("Taxa Moeda FOB ,");
			}
			if(numerario.getVlTaxaMoedaFrete() == null  || numerario.getVlTaxaMoedaFrete().doubleValue() == 0){
				retorno.append("Taxa Moeda Frete ,");
			}
			if(numerario.getVlTaxaMoedaFiscal() == null  || numerario.getVlTaxaMoedaFiscal().doubleValue() == 0){
				retorno.append("Taxa Moeda Fiscal ,");
			}
			
			
			
			return retorno.toString();
		}
		
		public void tratarNull(){
			Field[] fields = Numerario.class.getDeclaredFields();
			
			for(int i = 0; i < fields.length; i++){
				 try {
					 if(fields[i].getType().equals(BigDecimal.class)){
						 
						 if(((BigDecimal)fields[i].get(numerario)) == null){
							 fields[i].set(numerario, new BigDecimal(0));
						 }
						 
					 }
				 } catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}
		
}
