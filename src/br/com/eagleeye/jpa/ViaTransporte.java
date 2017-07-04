package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ViaTransporte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable=false)
	private int idViaTransporte;
	
	@Column(nullable=false,length=8)
	private String codigo;
	
	@Column(nullable=false,length=200)
	private String viaTransporte;

	public int getIdViaTransporte() {
		return idViaTransporte;
	}

	public void setIdViaTransporte(int idViaTransporte) {
		this.idViaTransporte = idViaTransporte;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getViaTransporte() {
		return viaTransporte;
	}

	public void setViaTransporte(String viaTransporte) {
		this.viaTransporte = viaTransporte;
	}




	
	
}
