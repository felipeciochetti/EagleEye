/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eagleeye.enums;

/**
 *
 */
public enum FasesLinhaTempoEmbarque {

	EMBARQUE("EMBARQUE","1","E"), CHEGADA("CHEGADA","2","C"), AVARIA("AVARIA","3","A"), 
		MAPA("MAPA","4","M"), MANTRA("MANTRA","5","MR") , NUMERARIO("NUMERARIO","6","N"),
		DI("REG DI","7","DI"), PARAMETRIZACAO("PARAM","8","P") , CANAL("CANAL","9","CA"),
		LIBERADO("LIBERADO","10","L")
		
		;
	
    
    private String label;
    private String fase;
    private String valor;
    
    private  FasesLinhaTempoEmbarque(String label,String fase ,String valor) {
        this.label = label;
        this.fase = fase;
        this.valor = valor;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}
    
}
