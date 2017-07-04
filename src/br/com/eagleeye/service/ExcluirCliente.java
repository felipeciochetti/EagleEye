package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Clientes;

public class ExcluirCliente implements Serializable{
	
		
	Clientes dao = new Clientes();
	
	public ExcluirCliente() {
	}
		
	public void excluir(Cliente cliente){
		if(dao.excluir(cliente)){
			new Mensagem().mensagensGeralAviso("Cliente excluido com sucesso!");
		}else{
			new Mensagem().mensagensGeralErro("Cliente não pode ser excluido!");
		}
	}
	
}
