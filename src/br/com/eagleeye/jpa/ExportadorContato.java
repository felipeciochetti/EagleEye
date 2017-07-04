package br.com.eagleeye.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
public class ExportadorContato  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Integer idContato;
	@JoinColumn(name = "idExportador")
	@ManyToOne
	private Exportador cliente;
	@Column(nullable=true,length=6)
	private java.lang.String codigo;
	@Column(nullable=true,length=100)
	private java.lang.String nome;
	@Column(nullable=true,length=50)
	private java.lang.String cargo;
	@Column(nullable=true,length=50)
	private java.lang.String departamento;
	@Column(nullable=true,length=50)
	private java.lang.String telefone;
	@Column(nullable=true,length=50)
	private java.lang.String celular;
	@Column(nullable=true,length=50)
	private java.lang.String fax;
	@Column(nullable=true,length=50)
	private java.lang.String email;
	@Column(nullable=true,length=1)
	private java.lang.String emailStatus;
	@Transient
	private boolean isReceberEmail;
	@Column(nullable=true,length=200)
	private String observacao;
	public java.lang.Integer getIdContato() {
		return idContato;
	}
	public void setIdContato(java.lang.Integer idContato) {
		this.idContato = idContato;
	}
	
	public java.lang.String getCodigo() {
		return codigo;
	}
	public void setCodigo(java.lang.String codigo) {
		this.codigo = codigo;
	}
	public Exportador getExportador() {
		return cliente;
	}
	public void setExportador(Exportador cliente) {
		this.cliente = cliente;
	}
	
	public java.lang.String getNome() {
		return nome;
	}
	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}
	public java.lang.String getCargo() {
		return cargo;
	}
	public void setCargo(java.lang.String cargo) {
		this.cargo = cargo;
	}
	public java.lang.String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(java.lang.String departamento) {
		this.departamento = departamento;
	}
	public java.lang.String getTelefone() {
		return telefone;
	}
	public void setTelefone(java.lang.String telefone) {
		this.telefone = telefone;
	}
	public java.lang.String getCelular() {
		return celular;
	}
	public void setCelular(java.lang.String celular) {
		this.celular = celular;
	}
	public java.lang.String getFax() {
		return fax;
	}
	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public boolean isReceberEmail() {
		if("S".equals(emailStatus)){
			setReceberEmail(true);
		}
		return isReceberEmail;
	}
	public void setReceberEmail(boolean isReceberEmail) {
		this.isReceberEmail = isReceberEmail;
		emailStatus = (isReceberEmail?"S":"N");
	}
	public java.lang.String getEmailStatus() {
		return emailStatus;
	}
	public void setEmailStatus(java.lang.String emailStatus) {
		this.emailStatus = emailStatus;
	}
	
	

	
	
	
		
}
