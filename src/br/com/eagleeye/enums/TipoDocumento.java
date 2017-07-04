/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eagleeye.enums;

/**
 *
 */
public enum TipoDocumento {

	HAWB("HAWB","H"), BL("BL","B"), CRT("CRT","C"), INVOICE("INVOICE","V"),
	
	PACKING_LIST("PACKING LIST","P");
	
//	
//	CERTIFICADOS("CERTIFICADOS","F");
//	
//	, CI("CI","CI"), DI("DI","DI"),
//	
//	NF("NOTA FISCAL","NF"), OUTROS("OUTROS","O") ;
	
    
    private String label;
    private String valor;

    private  TipoDocumento(String label,String valor) {
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
