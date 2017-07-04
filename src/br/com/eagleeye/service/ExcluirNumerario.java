package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.Numerario;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Numerarios;

public class ExcluirNumerario implements Serializable{
	
		
	Numerarios dao = new Numerarios();
	
	public ExcluirNumerario() {
	}
		
	public void excluir(Numerario Numerario){
		if(dao.excluir(Numerario)){
			new Mensagem().mensagensGeralAviso("Numerario excluido com sucesso!");
		}else{
			new Mensagem().mensagensGeralErro("Numerario não pode ser excluido!");
		}
	}
	
}
