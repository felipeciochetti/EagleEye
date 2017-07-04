package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.Exportador;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Exportadores;

public class ExcluirExportador implements Serializable{
	
		
	Exportadores dao = new Exportadores();
	
	public ExcluirExportador() {
	}
		
	public void excluir(Exportador cliente){
		if(dao.excluir(cliente)){
			new Mensagem().mensagensGeralAviso("Exportador excluido com sucesso!");
		}else{
			new Mensagem().mensagensGeralErro("Exportador não pode ser excluido!");
		}
	}
	
}
