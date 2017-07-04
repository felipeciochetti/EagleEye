package br.com.eagleeye.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.faces.application.FacesMessage;

import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;
import br.com.eagleeye.jpa.LinhaTempoEmbProcesso;
import br.com.eagleeye.jpa.LinhaTempoEmbProcesso;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.util.Functions;

public class AlterarStatusLinhaTempoEmb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	StatusFaseLinhaTempoDoc statusFaseAnterior;
	StatusFaseLinhaTempoDoc statusFaseAlterada;

	public String  gravarAlteracaoStatusLinhaTempoEmb(Processo processo ,LinhaTempoEmbProcesso faseAlterada ){

		if(faseAlterada.getFase().equals("1")){
			faseAlterada.setDtRealizacao(new Date(System.currentTimeMillis()));
			new Mensagem().addMessage("msgLinhaTempoDoc", "Fase alterada com sucesso. " , FacesMessage.SEVERITY_INFO);
			return "";
		}

		Set<LinhaTempoEmbProcesso> list = processo.getLinhaTempoEmbProcesso();

		for (LinhaTempoEmbProcesso faseAnterior : list) {

			if(Integer.valueOf(faseAnterior.getFase())== Integer.valueOf(faseAlterada.getFase())-1){
				statusFaseAnterior = Functions.getValorLinhaTempo(faseAnterior.getStatus());
				statusFaseAlterada = Functions.getValorLinhaTempo(faseAlterada.getStatus());

				if(statusFaseAnterior.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)||statusFaseAnterior.equals(StatusFaseLinhaTempoDoc.PROCESSANDO )){
					if(statusFaseAlterada.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
						//faseAlterada.setDtRealizacao(new Date(System.currentTimeMillis()));
						new Mensagem().addMessage("msgLinhaTempoDoc", "Fase alterada com sucesso. " , FacesMessage.SEVERITY_INFO);
						return "";
					}else{
						faseAlterada.setStatus(StatusFaseLinhaTempoDoc.AGUARDANDO.getValor());
						new Mensagem().addMessage("msgLinhaTempoDoc", "Fase Não pode ser alterada." , FacesMessage.SEVERITY_ERROR);
						return "";
					}

				}else if(statusFaseAnterior.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
					faseAlterada.setDtRealizacao(new Date(System.currentTimeMillis()));
					new Mensagem().addMessage("msgLinhaTempoDoc", "Fase alterada com sucesso. " , FacesMessage.SEVERITY_INFO);
					return "";
				}else if (statusFaseAnterior.equals(StatusFaseLinhaTempoDoc.CANCELADA)){
					faseAlterada.setStatus(StatusFaseLinhaTempoDoc.AGUARDANDO.getValor());
					new Mensagem().addMessage("msgLinhaTempoDoc", "Fase Não pode ser alterada." , FacesMessage.SEVERITY_ERROR);
					return "";
				}

			}
		}
		return "";
	}

	public StatusFaseLinhaTempoDoc getStatusFaseAnterior() {
		return statusFaseAnterior;
	}

	public void setStatusFaseAnterior(StatusFaseLinhaTempoDoc statusFaseAnterior) {
		this.statusFaseAnterior = statusFaseAnterior;
	}
	

}
