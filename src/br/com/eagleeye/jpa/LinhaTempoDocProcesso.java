package br.com.eagleeye.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class LinhaTempoDocProcesso  implements Serializable , Comparable<LinhaTempoDocProcesso>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Integer idLinhaTempo;
	@JoinColumn(name = "idProcesso")
	@ManyToOne
	private Processo processo;
	@Column(nullable=true,length=1)
	private java.lang.String fase;
	@Column(nullable=true,length=20)
	private java.lang.String descricao;
	@Column(nullable=true,length=20)
	private java.lang.String status;
	@Column(nullable=true,length=1)
	private java.lang.String tipoLinhaTempo;
	@Column
	@Temporal(TemporalType.TIMESTAMP)	
	private Date dtRealizacao;
	
	public java.lang.Integer getIdLinhaTempo() {
		return idLinhaTempo;
	}
	public void setIdLinhaTempo(java.lang.Integer idLinhaTempo) {
		this.idLinhaTempo = idLinhaTempo;
	}
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public java.lang.String getFase() {
		return fase;
	}
	public void setFase(java.lang.String fase) {
		this.fase = fase;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getTipoLinhaTempo() {
		return tipoLinhaTempo;
	}
	public void setTipoLinhaTempo(java.lang.String tipoLinhaTempo) {
		this.tipoLinhaTempo = tipoLinhaTempo;
	}
	public java.lang.String getDescricao() {
		return descricao;
	}
	public void setDescricao(java.lang.String descricao) {
		this.descricao = descricao;
	}
	
	public Date getDtRealizacao() {
		return dtRealizacao;
	}
	public void setDtRealizacao(Date dtRealizacao) {
		this.dtRealizacao = dtRealizacao;
	}
	@Override
	public int compareTo(LinhaTempoDocProcesso o) {
		return Integer.valueOf(this.getFase()) - Integer.valueOf(o.getFase());
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	
	

	
	
	
		
}
