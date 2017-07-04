package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class DocumentoProcesso  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Integer idDocumento;
	@JoinColumn(name = "idProcesso")
	@ManyToOne
	private Processo processo;
	@Column(nullable=true,length=30)
	private java.lang.String nome;
	@Column(nullable=true,length=200)
	private java.lang.String url;
	@Column(nullable=true,length=4)
	private java.lang.String tipoDocumento;
	
	public java.lang.Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(java.lang.Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public java.lang.String getNome() {
		return nome;
	}
	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}
	public java.lang.String getUrl() {
		return url;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	public java.lang.String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(java.lang.String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	

	
	

	
	
	
		
}
