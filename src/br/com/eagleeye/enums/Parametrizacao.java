/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eagleeye.enums;

/**
 *
 */
public enum Parametrizacao {

	VERDE("VERDE","V"),VERMELHO("VERMELHO","M"), AMARELO("AMARELO","A"), PRETO("PRETO","P");
    
    private String label;
    private String valor;

    private  Parametrizacao(String label,String valor) {
        this.label = label;
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
    
}
