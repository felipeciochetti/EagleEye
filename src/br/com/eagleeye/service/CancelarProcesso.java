package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Processos;

public class CancelarProcesso implements Serializable{
	
		
	Processos dao = new Processos();
	
	public CancelarProcesso() {
	}
		
	public void cancelar(Processo proc){
		dao.atualizar(proc);
		new Mensagem().mensagensGeralAviso("Processo cancelado com sucesso!");
	}
	
}
