package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Ncm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable=false)
	private int idNcm;
	
	@Column(nullable=false,length=8)
	private String codigo;
	
	@Column(nullable=false,length=200)
	private String ncm;

	public int getIdNcm() {
		return idNcm;
	}

	public void setIdNcm(int idNcm) {
		this.idNcm = idNcm;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}


	public String getFormatadoNcm(){
		if(getCodigo() == null){
			return "";
		}
		
		return getCodigo() + " - " + getNcm();
	}

	@Override
	public String toString() {
		return "Ncm [codigo=" + codigo + ", ncm=" + ncm + "]";
	}

	
	
}
