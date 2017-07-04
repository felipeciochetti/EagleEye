package br.com.eagleeye.complete;

import java.util.ArrayList;
import java.util.List;

import br.com.eagleeye.jpa.Pais;
import br.com.eagleeye.jpa.ViaTransporte;
import br.com.eagleeye.repositorio.ViaTransportes;

public class ViaTransporteComplete {

	private ViaTransportes repositorio;
	
	public ViaTransporteComplete(ViaTransportes repositorio){
		this.repositorio = repositorio;
	}
	
	
	public List<ViaTransporte> execute(String query){
		if (query.length() > 0) {
				return repositorio.listarNome(query, 30);
		}
		return new ArrayList<ViaTransporte>();  
	}

	
}
