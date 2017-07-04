package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Usuarios;

public class ExcluirUsuario implements Serializable{
	
		
	Usuarios dao = new Usuarios();
	
	public ExcluirUsuario() {
	}
		
	public void excluir(Usuario usuario){
		dao.excluir(usuario);
		new Mensagem().mensagensGeralAviso("Usúario excluido com sucesso!");
	}
	
}
