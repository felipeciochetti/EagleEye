package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.repositorio.Clientes;

public class ClienteComplete {

	private Clientes repositorio;
	
	public ClienteComplete(Clientes repositorio){
		this.repositorio = repositorio;
	}
	
	private Clientes getGrupoPessoaDAO(){		
		return repositorio;
	}
	
	public List<Cliente> execute(String query){
		if ( query.length() > 0){
				return repositorio.listarByNome(query, 30);
		}
		return new ArrayList<Cliente>();  
	}

	
}
