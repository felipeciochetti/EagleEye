package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Moeda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable=false)
	private int idMoeda;
	
	@Column(nullable=false,length=3)
	private String codigo;
	
	@Column(nullable=false,length=200)
	private String nome;

	public int getIdMoeda() {
		return idMoeda;
	}

	public void setIdMoeda(int idMoeda) {
		this.idMoeda = idMoeda;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFormatado(){
		return getCodigo() + " - " + getNome();
	}
	
	public String getFormatadoMoeda(){
		if(getCodigo() == null){
			return "";
		}
		
		return getCodigo() + " - " + getNome();
	}
}
