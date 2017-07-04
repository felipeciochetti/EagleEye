package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Exportador;
import br.com.eagleeye.repositorio.Exportadores;

public class ExportadorComplete {

	private Exportadores repositorio;
	
	public ExportadorComplete(Exportadores repositorio){
		this.repositorio = repositorio;
	}
	
	private Exportadores getGrupoPessoaDAO(){		
		return repositorio;
	}
	
	public List<Exportador> execute(String query){
		if ( query.length() > 0){
				return repositorio.listarByNome(query, 30);
		}
		return new ArrayList<Exportador>();  
	}

	
}
