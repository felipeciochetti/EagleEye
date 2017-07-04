package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.repositorio.Processos;

public class ProcessoComplete {

	private Processos repositorio;
	String condicoes = "";
	public ProcessoComplete(Processos repositorio){
		this.repositorio = repositorio;
	}
	public ProcessoComplete(Processos repositorio , String condicoes){
		this.repositorio = repositorio;
	}
	
	private Processos getGrupoPessoaDAO(){		
		return repositorio;
	}
	
	public List<Processo> execute(String query){
		if ( query.length() > 0){
				return repositorio.listarByReferenciaMvw(query, 30);
		}
		return new ArrayList<Processo>();  
	}
	public List<Processo> executeNumerario(String query){
		if ( query.length() > 0){
			return repositorio.listarByReferenciaMvwRestricaoNumerario(query, 30);
		}
		return new ArrayList<Processo>();  
	}

	
}
