package br.com.eagleeye.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;
import br.com.eagleeye.jpa.LinhaTempoEmbProcesso;
import br.com.eagleeye.util.Functions;

public class GerarLinhaTempoEmbarque implements Serializable {
	List<LinhaTempoEmbProcesso> listItens;
	LinhaTempoEmbarque html   = new LinhaTempoEmbarque();
	
	StatusFaseLinhaTempoDoc statusFase;
	StatusFaseLinhaTempoDoc statusFaseAnterior;
	public GerarLinhaTempoEmbarque(List<LinhaTempoEmbProcesso> listItens) {
		this.listItens = listItens;
	}
	
	public String gerarLinhaTempoEmbarque(){
		StringBuffer sb = new StringBuffer("");
		
		if(listItens == null || listItens.size() == 0){
			return "";
		}
		
		
		sb.append(html.gerarInicioCabecalho(listItens.size()));
		
		for (LinhaTempoEmbProcesso fase : listItens) {
			sb.append(html.gerarCabecalho(fase.getDescricao()));
		}
		
		sb.append(html.gerarFimCabecalho());

		for (LinhaTempoEmbProcesso fase : listItens) {
			if(fase.getFase().equals("1")){ // primeira fase
				
				statusFase = getValorEnum(fase.getStatus());
				sb.append(html.gerarPrimeriaFase(statusFase));
	
			}else if(fase.getFase().equals(String.valueOf(listItens.size()))){ // ultima fase
			
				statusFase = getValorEnum(fase.getStatus());

				sb.append(html.gerarFaseEntreFase(statusFaseAnterior, statusFase));
				
				sb.append(html.gerarUltimaFase(statusFase));
			
			}else{
				
				statusFase = getValorEnum(fase.getStatus());

				sb.append(html.gerarFaseEntreFase(statusFaseAnterior, statusFase));
				
				sb.append(html.gerarFase(statusFase));
			}
			
			setStatusFaseAnterior(statusFase);
		}
		
		
		int i =1;
		for (LinhaTempoEmbProcesso fase : listItens) {
			
			sb.append(html.gerarRodapeFase(i++,fase.getDtRealizacao(),getValorEnum(fase.getStatus())));
		}
		
		sb.append(html.getFimLinhaTempo());
		
		return sb.toString();
		
		
	}
	

	
	private StatusFaseLinhaTempoDoc getValorEnum(String status) {
		return Functions.getValorLinhaTempo(status);
	}

	public StatusFaseLinhaTempoDoc getStatusFaseAnterior() {
		return statusFaseAnterior;
	}

	public void setStatusFaseAnterior(StatusFaseLinhaTempoDoc statusFaseAnterior) {
		this.statusFaseAnterior = statusFaseAnterior;
	}
	
}
