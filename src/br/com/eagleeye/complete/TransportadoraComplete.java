package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.repositorio.Transportadoras;

public class TransportadoraComplete {

	private Transportadoras repositorio;
	
	public TransportadoraComplete(Transportadoras repositorio){
		this.repositorio = repositorio;
	}
	
	private Transportadoras getGrupoPessoaDAO(){		
		return repositorio;
	}
	
	public List<Transportadora> execute(String query){
		if ( query.length() > 0){
				return repositorio.listarByNome(query, 30);
		}
		return new ArrayList<Transportadora>();  
	}

	
}
