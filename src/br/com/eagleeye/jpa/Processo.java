package br.com.eagleeye.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.eagleeye.enums.TipoEmbarque;

@NamedQuery(name="Processo.buscaTodosNaoCancelados", query="select c from Processo c where c.dtCancelamento is null   order by c.sequencialProcesso ")

@Entity
public class Processo  implements Serializable{

	@Id
	public int idProcesso;
	@Column
	public Long sequencialProcesso;
	@Column(length=20)
	public  String referenciaMvw ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCotacaoFreteInt ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCotacaoDesembAd ;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtCotacaoFrete;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtCotacaoDesembaraco;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtEmbarqueAut;
	@Column(length=20)
	public String invoice ;
	@Column(length=200)
	public String portoOrigem ;
	@Column(length=200)
	public String portoDestino ;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtAbertura;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtPrevisaoEmbarque;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtEmbarque;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtPrevisaoChegada;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtChegada;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtSolicitacaoNumerario;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtEntradaNumerario;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtDesembaraco;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtEntrega;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtEtd;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtEta;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtRealPartida;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtRealChegada;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtRegistroDi;
	@Column(length=30)
	public String numeroDi ;
	@Column(length=1)
	public String statusDi ;
	@JoinColumn(name = "idCliente")
	@ManyToOne(fetch = FetchType.EAGER)
	public Cliente cliente;
	@JoinColumn(name = "idExportador")
	@ManyToOne(fetch = FetchType.LAZY)
	public Exportador exportador;
	@JoinColumn(name = "idViaTransporte")
	@ManyToOne(fetch = FetchType.LAZY)
	public ViaTransporte viaTransporte;
	@JoinColumn(name = "idTransportadora")
	@ManyToOne(fetch = FetchType.LAZY)
	public Transportadora transportadora;
	@Column(length=100)
	public String referenciaCliente ;
	@Column(length=4000)
	public String observacao ;
	@Column(length=4000)
	public String descMercadoria ;
	@Column(length=30)
	public String house ;
	@Column(length=30)
	public String master ;
	@Column(length=2)
	public String statusCarga ;
	@Column(length=1)
	public String snAvarias;
	@Column(length=1000)
	public String descAvarias;
	@Column(length=1)
	public String snAcionarSeguradora;
	@Column(length=1)
	public String snRemocao;
	@Column(length=1000)
	public String descRemocao ;
	@Column(length=1)
	public String snDocPendentes;
	@Column(length=300)
	public String descDocPendentes;
	@Column(length=1)
	public String parametrizacao;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtSoliNfEntrada;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtRecNfEntrada;
	@Column
	public String tipoEmbarque;
	@Column(length=1)
	public String tpFaturamento;
	@Column(length=300)
	public String descFaturamento;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtCancelamento;
	@Column(length=1)
	public String snNotificarAbertura;
		
	@OneToMany(orphanRemoval=true, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, mappedBy="processoNcmPK.processo")
	public Set<ProcessoNcm> processoNcm;
	
//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//    @JoinTable(name="ProcessoNcm", joinColumns={@JoinColumn(name="idProcesso", referencedColumnName="idProcesso")}, inverseJoinColumns={@JoinColumn(name="idNcm", referencedColumnName="idNcm")})
//	public Set<Ncm> processoNcm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpaisOrigem")
	public Pais paisOrigem;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpaisDestino")
	public Pais paisDestino;
	
	@OneToMany(orphanRemoval=true, fetch=FetchType.LAZY ,cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, mappedBy="processo")
	public Set<DocumentoProcesso> documentoProcesso;
	
	@OneToMany(orphanRemoval=true, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, mappedBy="processo")
	public Set<LinhaTempoDocProcesso> linhaTempoProcesso;
	
	@OneToMany(orphanRemoval=true, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, mappedBy="processo")
	public Set<LinhaTempoEmbProcesso> linhaTempoEmbProcesso;
	
	
	public int getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public Date getDtAbertura() {
		return dtAbertura;
	}
	public void setDtAbertura(Date dtAbertura) {
		this.dtAbertura = dtAbertura;
	}
	public Date getDtEmbarque() {
		return dtEmbarque;
	}
	public void setDtEmbarque(Date dtEmbarque) {
		this.dtEmbarque = dtEmbarque;
	}
	public Date getDtPrevisaoChegada() {
		return dtPrevisaoChegada;
	}
	public void setDtPrevisaoChegada(Date dtPrevisaoChegada) {
		this.dtPrevisaoChegada = dtPrevisaoChegada;
	}
	public Date getDtChegada() {
		return dtChegada;
	}
	public void setDtChegada(Date dtChegada) {
		this.dtChegada = dtChegada;
	}
	public Date getDtSolicitacaoNumerario() {
		return dtSolicitacaoNumerario;
	}
	public void setDtSolicitacaoNumerario(Date dtSolicitacaoNumerario) {
		this.dtSolicitacaoNumerario = dtSolicitacaoNumerario;
	}
	public Date getDtEntradaNumerario() {
		return dtEntradaNumerario;
	}
	public void setDtEntradaNumerario(Date dtEntradaNumerario) {
		this.dtEntradaNumerario = dtEntradaNumerario;
	}
	public Date getDtDesembaraco() {
		return dtDesembaraco;
	}
	public void setDtDesembaraco(Date dtDesembaraco) {
		this.dtDesembaraco = dtDesembaraco;
	}
	public Date getDtEntrega() {
		return dtEntrega;
	}
	public void setDtEntrega(Date dtEntrega) {
		this.dtEntrega = dtEntrega;
	}
	
	public Date getDtRegistroDi() {
		return dtRegistroDi;
	}
	public void setDtRegistroDi(Date dtRegistroDi) {
		this.dtRegistroDi = dtRegistroDi;
	}
	public String getNumeroDi() {
		return numeroDi;
	}
	public void setNumeroDi(String numeroDi) {
		this.numeroDi = numeroDi;
	}
	public String getStatusDi() {
		return statusDi;
	}
	public void setStatusDi(String statusDi) {
		this.statusDi = statusDi;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getDtPrevisaoEmbarque() {
		return dtPrevisaoEmbarque;
	}
	public void setDtPrevisaoEmbarque(Date dtPrevisaoEmbarque) {
		this.dtPrevisaoEmbarque = dtPrevisaoEmbarque;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getReferenciaMvw() {
		return referenciaMvw;
	}
	public void setReferenciaMvw(String referenciaMvw) {
		this.referenciaMvw = referenciaMvw;
	}
	public BigDecimal getVlCotacaoFreteInt() {
		return vlCotacaoFreteInt;
	}
	public void setVlCotacaoFreteInt(BigDecimal vlCotacaoFreteInt) {
		this.vlCotacaoFreteInt = vlCotacaoFreteInt;
	}
	public BigDecimal getVlCotacaoDesembAd() {
		return vlCotacaoDesembAd;
	}
	public void setVlCotacaoDesembAd(BigDecimal vlCotacaoDesembAd) {
		this.vlCotacaoDesembAd = vlCotacaoDesembAd;
	}
	public Date getDtEtd() {
		return dtEtd;
	}
	public void setDtEtd(Date dtEtd) {
		this.dtEtd = dtEtd;
	}
	public Date getDtEta() {
		return dtEta;
	}
	public void setDtEta(Date dtEta) {
		this.dtEta = dtEta;
	}
	public Date getDtRealPartida() {
		return dtRealPartida;
	}
	public void setDtRealPartida(Date dtRealPartida) {
		this.dtRealPartida = dtRealPartida;
	}
	public Date getDtRealChegada() {
		return dtRealChegada;
	}
	public void setDtRealChegada(Date dtRealChegada) {
		this.dtRealChegada = dtRealChegada;
	}
	public Transportadora getTransportadora() {
		return transportadora;
	}
	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}
	public String getDescMercadoria() {
		return descMercadoria;
	}
	public void setDescMercadoria(String descMercadoria) {
		this.descMercadoria = descMercadoria;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getSnAvarias() {
		return snAvarias;
	}
	public void setSnAvarias(String snAvarias) {
		this.snAvarias = snAvarias;
	}
	public String getDescAvarias() {
		return descAvarias;
	}
	public void setDescAvarias(String descAvarias) {
		this.descAvarias = descAvarias;
	}
	public String getSnAcionarSeguradora() {
		return snAcionarSeguradora;
	}
	public void setSnAcionarSeguradora(String snAcionarSeguradora) {
		this.snAcionarSeguradora = snAcionarSeguradora;
	}
	public String getSnRemocao() {
		return snRemocao;
	}
	public void setSnRemocao(String snRemocao) {
		this.snRemocao = snRemocao;
	}
	public String getSnDocPendentes() {
		return snDocPendentes;
	}
	public void setSnDocPendentes(String snDocPendentes) {
		this.snDocPendentes = snDocPendentes;
	}
	public String getDescDocPendentes() {
		return descDocPendentes;
	}
	public void setDescDocPendentes(String descDocPendentes) {
		this.descDocPendentes = descDocPendentes;
	}
	public String getParametrizacao() {
		return parametrizacao;
	}
	public void setParametrizacao(String parametrizacao) {
		this.parametrizacao = parametrizacao;
	}
	public Date getDtSoliNfEntrada() {
		return dtSoliNfEntrada;
	}
	public void setDtSoliNfEntrada(Date dtSoliNfEntrada) {
		this.dtSoliNfEntrada = dtSoliNfEntrada;
	}
	public Date getDtRecNfEntrada() {
		return dtRecNfEntrada;
	}
	public void setDtRecNfEntrada(Date dtRecNfEntrada) {
		this.dtRecNfEntrada = dtRecNfEntrada;
	}
	public String getTpFaturamento() {
		return tpFaturamento;
	}
	public void setTpFaturamento(String tpFaturamento) {
		this.tpFaturamento = tpFaturamento;
	}
	public String getDescFaturamento() {
		return descFaturamento;
	}
	public void setDescFaturamento(String descFaturamento) {
		this.descFaturamento = descFaturamento;
	}
	
	
	public Set<ProcessoNcm> getProcessoNcm() {
		return processoNcm;
	}
	public void setProcessoNcm(Set<ProcessoNcm> processoNcm) {
		this.processoNcm = processoNcm;
	}
	public Pais getPaisOrigem() {
		return paisOrigem;
	}
	public void setPaisOrigem(Pais paisOrigem) {
		this.paisOrigem = paisOrigem;
	}
	public Pais getPaisDestino() {
		return paisDestino;
	}
	public void setPaisDestino(Pais paisDestino) {
		this.paisDestino = paisDestino;
	}
	public String getTipoEmbarque() {
		return tipoEmbarque;
	}
	public void setTipoEmbarque(String tipoEmbarque) {
		this.tipoEmbarque = tipoEmbarque;
	}
	public String getStatusCarga() {
		return statusCarga;
	}
	public void setStatusCarga(String statusCarga) {
		this.statusCarga = statusCarga;
	}
	public String getDescRemocao() {
		return descRemocao;
	}
	public void setDescRemocao(String descRemocao) {
		this.descRemocao = descRemocao;
	}
	public ViaTransporte getViaTransporte() {
		return viaTransporte;
	}
	public void setViaTransporte(ViaTransporte viaTransporte) {
		this.viaTransporte = viaTransporte;
	}
	public Set<DocumentoProcesso> getDocumentoProcesso() {
		return documentoProcesso;
	}
	public void setDocumentoProcesso(Set<DocumentoProcesso> documentoProcesso) {
		this.documentoProcesso = documentoProcesso;
	}
	public long getSequencialProcesso() {
		return sequencialProcesso == null?0:sequencialProcesso;
	}
	public void setSequencialProcesso(Long sequencialProcesso) {
		this.sequencialProcesso = sequencialProcesso;
	}

	public String getPortoOrigem() {
		return portoOrigem;
	}
	public void setPortoOrigem(String portoOrigem) {
		this.portoOrigem = portoOrigem;
	}
	public String getPortoDestino() {
		return portoDestino;
	}
	public void setPortoDestino(String portoDestino) {
		this.portoDestino = portoDestino;
	}
	
	public Exportador getExportador() {
		return exportador;
	}
	public void setExportador(Exportador exportador) {
		this.exportador = exportador;
	}
	public Set<LinhaTempoDocProcesso> getLinhaTempoProcesso() {
		return linhaTempoProcesso;
	}
	public void setLinhaTempoProcesso(Set<LinhaTempoDocProcesso> linhaTempoProcesso) {
		this.linhaTempoProcesso = linhaTempoProcesso;
	}
	public Date getDtCotacaoFrete() {
		return dtCotacaoFrete;
	}
	public void setDtCotacaoFrete(Date dtCotacaoFrete) {
		this.dtCotacaoFrete = dtCotacaoFrete;
	}
	public Date getDtCotacaoDesembaraco() {
		return dtCotacaoDesembaraco;
	}
	public void setDtCotacaoDesembaraco(Date dtCotacaoDesembaraco) {
		this.dtCotacaoDesembaraco = dtCotacaoDesembaraco;
	}
	public Date getDtEmbarqueAut() {
		return dtEmbarqueAut;
	}
	public void setDtEmbarqueAut(Date dtEmbarqueAut) {
		this.dtEmbarqueAut = dtEmbarqueAut;
	}
	public String getReferenciaCliente() {
		return referenciaCliente;
	}
	public void setReferenciaCliente(String referenciaCliente) {
		this.referenciaCliente = referenciaCliente;
	}
	public Set<LinhaTempoEmbProcesso> getLinhaTempoEmbProcesso() {
		return linhaTempoEmbProcesso;
	}
	public void setLinhaTempoEmbProcesso(Set<LinhaTempoEmbProcesso> linhaTempoEmbProcesso) {
		this.linhaTempoEmbProcesso = linhaTempoEmbProcesso;
	}
	public Date getDtCancelamento() {
		return dtCancelamento;
	}
	public void setDtCancelamento(Date dtCancelamento) {
		this.dtCancelamento = dtCancelamento;
	}
	
	
	public String getSnNotificarAbertura() {
		return snNotificarAbertura;
	}
	public void setSnNotificarAbertura(String snNotificarAbertura) {
		this.snNotificarAbertura = snNotificarAbertura;
	}
	public String getFormatadoProcesso(){
		if(getCliente() == null){
			return getReferenciaMvw();
		}
		
		return getReferenciaMvw() + " - " + getCliente().getNome();
	}
	
	
	
	
		
}
