package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProcessoNcmPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "idProcesso", nullable=false)
	@ManyToOne
	private Processo processo;
	
	@JoinColumn(name = "idNcm", nullable=false)
	@ManyToOne
	private Ncm ncm;
	

	
	public Processo getProcesso() {
		return processo;
	}


	public void setProcesso(Processo processo) {
		this.processo = processo;
	}


	public Ncm getNcm() {
		return ncm;
	}


	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}


	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Object obj) {
		return super.equals(obj);
	}	
}

