package br.com.eagleeye.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;
import br.com.eagleeye.jpa.LinhaTempoDocProcesso;
import br.com.eagleeye.util.Functions;

public class GerarLinhaTempoDocumento implements Serializable {
	List<LinhaTempoDocProcesso> listItens;
	LinhaTempoDocumento html   = new LinhaTempoDocumento();
	
	StatusFaseLinhaTempoDoc statusFase;
	StatusFaseLinhaTempoDoc statusFaseAnterior;
	public GerarLinhaTempoDocumento(List<LinhaTempoDocProcesso> listItens) {
		this.listItens = listItens;
	}
	
	public String gerarLinhaTempoDocumentos(){
		StringBuffer sb = new StringBuffer("");
		
		
		sb.append(html.gerarInicioCabecalho(listItens.size()));
		
		for (LinhaTempoDocProcesso fase : listItens) {
			sb.append(html.gerarCabecalho(fase.getDescricao()));
		}
		
		sb.append(html.gerarFimCabecalho());

		for (LinhaTempoDocProcesso fase : listItens) {
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
		for (LinhaTempoDocProcesso fase : listItens) {
			
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
