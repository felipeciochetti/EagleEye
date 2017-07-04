/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eagleeye.enums;

/**
 *
 */
public enum FasesLinhaTempoDoc {

	PO("PO","1","P"), INVOICE("INVOICE","2","V"), PACKINGLIST("PACKINGLIST","3","P"), 
		CERTIFICADOS("CERTIFICADOS","4","H"), CATALOGO("CATALOGO","5","D");
	
    
    private String label;
    private String fase;
    private String valor;

    private  FasesLinhaTempoDoc(String label,String fase ,String valor) {
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
