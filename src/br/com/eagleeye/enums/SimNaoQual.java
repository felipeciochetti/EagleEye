/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eagleeye.enums;

/**
 *
 */
public enum SimNaoQual {

	VAZIO("Selecione"," "),SIM("Sim","S"), NAO("N�o","N"), QUAL("Qual","Q");
    
    private String label;
    private String valor;

    private  SimNaoQual(String label,String valor) {
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
