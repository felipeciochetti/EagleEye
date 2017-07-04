package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Moeda;
import br.com.eagleeye.jpa.Ncm;
import br.com.eagleeye.repositorio.Moedas;

public class MoedaComplete {

	private Moedas repositorio;
	
	public MoedaComplete(Moedas repositorio){
		this.repositorio = repositorio;
	}
	
	
	public ArrayList<Moeda> execute(String query){
		if (query.length() > 0) {
			if ("0123456789".indexOf(query.substring(0, 1)) >= 0) {
				return (ArrayList<Moeda>) repositorio.listarCodigo(query, 30);
			} else {
				return (ArrayList<Moeda>) repositorio.listarNome(query, 30);
			}
		}
		return new ArrayList<Moeda>();  
	}

	
}
