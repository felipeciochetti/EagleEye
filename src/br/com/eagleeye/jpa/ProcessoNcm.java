package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ProcessoNcm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProcessoNcmPK processoNcmPK;

	@Column(insertable=false, updatable=false)
	private int idProcesso;
	
	@Column(insertable=false, updatable=false)
	private int idNcm;
	
	

	public ProcessoNcmPK getProcessoNcmPK() {
		return processoNcmPK;
	}

	public void setProcessoNcmPK(ProcessoNcmPK processoNcmPK) {
		this.processoNcmPK = processoNcmPK;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
	}

	public int getIdNcm() {
		return idNcm;
	}

	public void setIdNcm(int idNcm) {
		this.idNcm = idNcm;
	}

	
	
}
