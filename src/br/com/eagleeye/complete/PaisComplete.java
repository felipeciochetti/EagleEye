package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Pais;
import br.com.eagleeye.repositorio.Paises;

public class PaisComplete {

	private Paises repositorio;
	
	public PaisComplete(Paises repositorio){
		this.repositorio = repositorio;
	}
	
	
	public List<Pais> execute(String query){
		if (query.length() > 0) {
				return repositorio.listarNome(query, 30);
		}
		return new ArrayList<Pais>();  
	}

	
}
