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
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@NamedQuery(name="Usuario.buscaTodos", query="select c from Usuario c")
@Entity
public class Usuario implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long idUsuario; 
	@Column
	public String codigo;
	@Column
	public String nome;
	@Column
	public String usuario;
	@Column
	private String senha;
	@Transient
	private String novaSenha;
	@Transient
	private String confSenha;
	@Column
	private String email;
	@JoinColumn(name = "idCliente")
	@ManyToOne
	public Cliente cliente;
	@Column(length = 64, nullable = false)
    private String nivel;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCadastro;
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getConfSenha() {
		return confSenha;
	}
	public void setConfSenha(String confSenha) {
		this.confSenha = confSenha;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	@Override
	public String toString() {
		return "[ " + usuario + "]";
	}
	
	
}
