package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Versao  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idVersao;
	@Column(length=6)
	public  String versao ;
	public long getIdVersao() {
		return idVersao;
	}
	public void setIdVersao(long idVersao) {
		this.idVersao = idVersao;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	
}
