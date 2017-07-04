package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Transportadoras;

public class ExcluirTransportadora implements Serializable{
	
		
	Transportadoras dao = new Transportadoras();
	
	public ExcluirTransportadora() {
	}
		
	public void excluir(Transportadora transportadora){
		dao.excluir(transportadora);
		new Mensagem().mensagensGeralAviso("Transportadora excluido com sucesso!");
	}
	
}
