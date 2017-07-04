package br.com.eagleeye.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@NamedQuery(name="Cliente.buscaTodos", query="select c from Cliente c")

@Entity
public class Cliente  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCliente;
	@Column(length=6)
	public  String codigo ;
	@Column(length=100)
	public String nome ;
	@Column
	@Temporal(TemporalType.DATE)	
	private Date dtCadastro;
	@Column(length=20)
	private String cpf ;
	@Column(length=20)
	public String cnpj ;
	@Column(length=50)
	private String email;
	@Column(length=20)
	private String telefone;
	@Column(length=100)
	private String cidade;
	@Column(length=30)
	private String referencia;
	@Column(length=800)
	private String observacao;
	@Column(length=1)
	private String ativo;
	@Column(length=200)
	private String endereco ;
	@Column(length=50)
	private String bairro ;
	@Column(length=20)
	private String cep ;
	@Column(length=20)
	private String numero ;
	@Transient
	private boolean isAtivo;
	
	@OneToMany(orphanRemoval=true, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, mappedBy="cliente")
	private Set<ClienteContato> clienteContato;
	
	
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
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
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Set<ClienteContato> getClienteContato() {
			return clienteContato;
	}
	public void setClienteContato(Set<ClienteContato> clienteContato) {
		this.clienteContato = clienteContato;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public boolean isAtivo() {
		if("S".equals(ativo)){
			setAtivo(true);
		}
		return isAtivo;
	}
	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
		ativo = isAtivo?"S":"N";
	}
	
	
	
	
		
}
