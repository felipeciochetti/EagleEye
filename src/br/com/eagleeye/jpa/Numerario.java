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
import javax.persistence.GenerationType;
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

@NamedQuery(name="Numerario.buscaTodos", query="select c from Numerario c  ")

@Entity
public class Numerario  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long idNumerario;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlFobReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlFobDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlFobMoeda ;

	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlFreteIntReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlFreteIntDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlFreteIntMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlSeguroReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlSeguroDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlSeguroMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCifReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCifDolar ;
	
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlAduaneiroReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlAduaneiroDolar ;
	
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxSiscomexReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxSiscomexDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxSiscomexMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlImpostoImpReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlImpostoImpDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlImpostoImpMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlImpostoProdReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlImpostoProdDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlImpostoProdMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlPisReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlPisDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlPisMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCofinsReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCofinsDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCofinsMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlIcmsReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlIcmsDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlIcmsMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalImpostosReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalImpostosDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalImpostosMoeda ;
	
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDespFreteIntReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDespFreteIntDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDespFreteIntMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlLibBlReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlLibBlDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlLibBlMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDesconsoReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDesconsoDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDesconsoMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxIspsReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxIspsDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxIspsMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxSiscargaReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxSiscargaDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTxSiscargaMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCapataziaReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCapataziaDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlCapataziaMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlAfrmmReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlAfrmmDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlAfrmmMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlArmazPortoReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlArmazPortoDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlArmazPortoMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTranspRemocaoReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTranspRemocaoDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTranspRemocaoMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlArmazPortoSecoReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlArmazPortoSecoDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlArmazPortoSecoMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTranspEntregaReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTranspEntregaDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTranspEntregaMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDesembaracoReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDesembaracoDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlDesembaracoMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlMvwReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlMvwDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlMvwMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalDespesaMvwReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalDespesaMvwDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalDespesaMvwMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalDespesaImpReais ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalDespesaImpDolar ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTotalDespesaImpMoeda ;
	
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTaxaMoedaFiscal ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTaxaMoedaFob ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTaxaMoedaFrete ;
	@Column(precision=18,scale=2, nullable=true)
	public BigDecimal vlTaxaMoedaSeguro ;
	
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtAbertura;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtCalculo;
	@Column
	@Temporal(TemporalType.DATE)	
	public Date dtCancelamento;
	@Column(length=1)
	public String snCalculado ;
	@JoinColumn(name = "idProcesso")
	@ManyToOne(fetch = FetchType.EAGER)
	public Processo processo;
	@JoinColumn(name = "idMoedaFiscal")
	@ManyToOne(fetch = FetchType.EAGER)
	public Moeda moedaFiscal;
	@JoinColumn(name = "idMoedaFob")
	@ManyToOne(fetch = FetchType.EAGER)
	public Moeda moedaFob;
	@JoinColumn(name = "idMoedaFrete")
	@ManyToOne(fetch = FetchType.EAGER)
	public Moeda moedaFrete;
	@JoinColumn(name = "idMoedaSeguro")
	@ManyToOne(fetch = FetchType.EAGER)
	public Moeda moedaSeguro;
	
	
	public long getIdNumerario() {
		return idNumerario;
	}
	public void setIdNumerario(long idNumerario) {
		this.idNumerario = idNumerario;
	}
	public BigDecimal getVlFobReais() {
		return vlFobReais;
	}
	public void setVlFobReais(BigDecimal vlFobReais) {
		this.vlFobReais = vlFobReais;
	}
	public BigDecimal getVlFobDolar() {
		return vlFobDolar;
	}
	public void setVlFobDolar(BigDecimal vlFobDolar) {
		this.vlFobDolar = vlFobDolar;
	}
	public BigDecimal getVlFobMoeda() {
		return vlFobMoeda;
	}
	public void setVlFobMoeda(BigDecimal vlFobMoeda) {
		this.vlFobMoeda = vlFobMoeda;
	}
	public BigDecimal getVlFreteIntReais() {
		return vlFreteIntReais;
	}
	public void setVlFreteIntReais(BigDecimal vlFreteIntReais) {
		this.vlFreteIntReais = vlFreteIntReais;
	}
	public BigDecimal getVlFreteIntDolar() {
		return vlFreteIntDolar;
	}
	public void setVlFreteIntDolar(BigDecimal vlFreteIntDolar) {
		this.vlFreteIntDolar = vlFreteIntDolar;
	}
	public BigDecimal getVlFreteIntMoeda() {
		return vlFreteIntMoeda;
	}
	public void setVlFreteIntMoeda(BigDecimal vlFreteIntMoeda) {
		this.vlFreteIntMoeda = vlFreteIntMoeda;
	}
	public BigDecimal getVlSeguroReais() {
		return vlSeguroReais;
	}
	public void setVlSeguroReais(BigDecimal vlSeguroReais) {
		this.vlSeguroReais = vlSeguroReais;
	}
	public BigDecimal getVlSeguroDolar() {
		return vlSeguroDolar;
	}
	public void setVlSeguroDolar(BigDecimal vlSeguroDolar) {
		this.vlSeguroDolar = vlSeguroDolar;
	}
	public BigDecimal getVlSeguroMoeda() {
		return vlSeguroMoeda;
	}
	public void setVlSeguroMoeda(BigDecimal vlSeguroMoeda) {
		this.vlSeguroMoeda = vlSeguroMoeda;
	}
	public BigDecimal getVlCifReais() {
		return vlCifReais;
	}
	public void setVlCifReais(BigDecimal vlCifReais) {
		this.vlCifReais = vlCifReais;
	}
	public BigDecimal getVlCifDolar() {
		return vlCifDolar;
	}
	public void setVlCifDolar(BigDecimal vlCifDolar) {
		this.vlCifDolar = vlCifDolar;
	}
	public BigDecimal getVlAduaneiroReais() {
		return vlAduaneiroReais;
	}
	public void setVlAduaneiroReais(BigDecimal vlAduaneiroReais) {
		this.vlAduaneiroReais = vlAduaneiroReais;
	}
	public BigDecimal getVlAduaneiroDolar() {
		return vlAduaneiroDolar;
	}
	public void setVlAduaneiroDolar(BigDecimal vlAduaneiroDolar) {
		this.vlAduaneiroDolar = vlAduaneiroDolar;
	}
	public BigDecimal getVlTxSiscomexReais() {
		return vlTxSiscomexReais;
	}
	public void setVlTxSiscomexReais(BigDecimal vlTxSiscomexReais) {
		this.vlTxSiscomexReais = vlTxSiscomexReais;
	}
	public BigDecimal getVlTxSiscomexDolar() {
		return vlTxSiscomexDolar;
	}
	public void setVlTxSiscomexDolar(BigDecimal vlTxSiscomexDolar) {
		this.vlTxSiscomexDolar = vlTxSiscomexDolar;
	}
	public BigDecimal getVlTxSiscomexMoeda() {
		return vlTxSiscomexMoeda;
	}
	public void setVlTxSiscomexMoeda(BigDecimal vlTxSiscomexMoeda) {
		this.vlTxSiscomexMoeda = vlTxSiscomexMoeda;
	}
	public BigDecimal getVlImpostoImpReais() {
		return vlImpostoImpReais;
	}
	public void setVlImpostoImpReais(BigDecimal vlImpostoImpReais) {
		this.vlImpostoImpReais = vlImpostoImpReais;
	}
	public BigDecimal getVlImpostoImpDolar() {
		return vlImpostoImpDolar;
	}
	public void setVlImpostoImpDolar(BigDecimal vlImpostoImpDolar) {
		this.vlImpostoImpDolar = vlImpostoImpDolar;
	}
	public BigDecimal getVlImpostoImpMoeda() {
		return vlImpostoImpMoeda;
	}
	public void setVlImpostoImpMoeda(BigDecimal vlImpostoImpMoeda) {
		this.vlImpostoImpMoeda = vlImpostoImpMoeda;
	}
	public BigDecimal getVlImpostoProdReais() {
		return vlImpostoProdReais;
	}
	public void setVlImpostoProdReais(BigDecimal vlImpostoProdReais) {
		this.vlImpostoProdReais = vlImpostoProdReais;
	}
	public BigDecimal getVlImpostoProdDolar() {
		return vlImpostoProdDolar;
	}
	public void setVlImpostoProdDolar(BigDecimal vlImpostoProdDolar) {
		this.vlImpostoProdDolar = vlImpostoProdDolar;
	}
	public BigDecimal getVlImpostoProdMoeda() {
		return vlImpostoProdMoeda;
	}
	public void setVlImpostoProdMoeda(BigDecimal vlImpostoProdMoeda) {
		this.vlImpostoProdMoeda = vlImpostoProdMoeda;
	}
	public BigDecimal getVlPisReais() {
		return vlPisReais;
	}
	public void setVlPisReais(BigDecimal vlPisReais) {
		this.vlPisReais = vlPisReais;
	}
	public BigDecimal getVlPisDolar() {
		return vlPisDolar;
	}
	public void setVlPisDolar(BigDecimal vlPisDolar) {
		this.vlPisDolar = vlPisDolar;
	}
	public BigDecimal getVlPisMoeda() {
		return vlPisMoeda;
	}
	public void setVlPisMoeda(BigDecimal vlPisMoeda) {
		this.vlPisMoeda = vlPisMoeda;
	}
	public BigDecimal getVlCofinsReais() {
		return vlCofinsReais;
	}
	public void setVlCofinsReais(BigDecimal vlCofinsReais) {
		this.vlCofinsReais = vlCofinsReais;
	}
	public BigDecimal getVlCofinsDolar() {
		return vlCofinsDolar;
	}
	public void setVlCofinsDolar(BigDecimal vlCofinsDolar) {
		this.vlCofinsDolar = vlCofinsDolar;
	}
	public BigDecimal getVlCofinsMoeda() {
		return vlCofinsMoeda;
	}
	public void setVlCofinsMoeda(BigDecimal vlCofinsMoeda) {
		this.vlCofinsMoeda = vlCofinsMoeda;
	}
	public BigDecimal getVlIcmsReais() {
		return vlIcmsReais;
	}
	public void setVlIcmsReais(BigDecimal vlIcmsReais) {
		this.vlIcmsReais = vlIcmsReais;
	}
	public BigDecimal getVlIcmsDolar() {
		return vlIcmsDolar;
	}
	public void setVlIcmsDolar(BigDecimal vlIcmsDolar) {
		this.vlIcmsDolar = vlIcmsDolar;
	}
	public BigDecimal getVlIcmsMoeda() {
		return vlIcmsMoeda;
	}
	public void setVlIcmsMoeda(BigDecimal vlIcmsMoeda) {
		this.vlIcmsMoeda = vlIcmsMoeda;
	}
	public BigDecimal getVlTotalImpostosReais() {
		return vlTotalImpostosReais;
	}
	public void setVlTotalImpostosReais(BigDecimal vlTotalImpostosReais) {
		this.vlTotalImpostosReais = vlTotalImpostosReais;
	}
	public BigDecimal getVlTotalImpostosDolar() {
		return vlTotalImpostosDolar;
	}
	public void setVlTotalImpostosDolar(BigDecimal vlTotalImpostosDolar) {
		this.vlTotalImpostosDolar = vlTotalImpostosDolar;
	}
	public BigDecimal getVlTotalImpostosMoeda() {
		return vlTotalImpostosMoeda;
	}
	public void setVlTotalImpostosMoeda(BigDecimal vlTotalImpostosMoeda) {
		this.vlTotalImpostosMoeda = vlTotalImpostosMoeda;
	}
	public BigDecimal getVlDespFreteIntReais() {
		return vlDespFreteIntReais;
	}
	public void setVlDespFreteIntReais(BigDecimal vlDespFreteIntReais) {
		this.vlDespFreteIntReais = vlDespFreteIntReais;
	}
	public BigDecimal getVlDespFreteIntDolar() {
		return vlDespFreteIntDolar;
	}
	public void setVlDespFreteIntDolar(BigDecimal vlDespFreteIntDolar) {
		this.vlDespFreteIntDolar = vlDespFreteIntDolar;
	}
	public BigDecimal getVlDespFreteIntMoeda() {
		return vlDespFreteIntMoeda;
	}
	public void setVlDespFreteIntMoeda(BigDecimal vlDespFreteIntMoeda) {
		this.vlDespFreteIntMoeda = vlDespFreteIntMoeda;
	}
	public BigDecimal getVlLibBlReais() {
		return vlLibBlReais;
	}
	public void setVlLibBlReais(BigDecimal vlLibBlReais) {
		this.vlLibBlReais = vlLibBlReais;
	}
	public BigDecimal getVlLibBlDolar() {
		return vlLibBlDolar;
	}
	public void setVlLibBlDolar(BigDecimal vlLibBlDolar) {
		this.vlLibBlDolar = vlLibBlDolar;
	}
	public BigDecimal getVlLibBlMoeda() {
		return vlLibBlMoeda;
	}
	public void setVlLibBlMoeda(BigDecimal vlLibBlMoeda) {
		this.vlLibBlMoeda = vlLibBlMoeda;
	}
	public BigDecimal getVlDesconsoReais() {
		return vlDesconsoReais;
	}
	public void setVlDesconsoReais(BigDecimal vlDesconsoReais) {
		this.vlDesconsoReais = vlDesconsoReais;
	}
	public BigDecimal getVlDesconsoDolar() {
		return vlDesconsoDolar;
	}
	public void setVlDesconsoDolar(BigDecimal vlDesconsoDolar) {
		this.vlDesconsoDolar = vlDesconsoDolar;
	}
	public BigDecimal getVlDesconsoMoeda() {
		return vlDesconsoMoeda;
	}
	public void setVlDesconsoMoeda(BigDecimal vlDesconsoMoeda) {
		this.vlDesconsoMoeda = vlDesconsoMoeda;
	}
	public BigDecimal getVlTxIspsReais() {
		return vlTxIspsReais;
	}
	public void setVlTxIspsReais(BigDecimal vlTxIspsReais) {
		this.vlTxIspsReais = vlTxIspsReais;
	}
	public BigDecimal getVlTxIspsDolar() {
		return vlTxIspsDolar;
	}
	public void setVlTxIspsDolar(BigDecimal vlTxIspsDolar) {
		this.vlTxIspsDolar = vlTxIspsDolar;
	}
	public BigDecimal getVlTxIspsMoeda() {
		return vlTxIspsMoeda;
	}
	public void setVlTxIspsMoeda(BigDecimal vlTxIspsMoeda) {
		this.vlTxIspsMoeda = vlTxIspsMoeda;
	}
	public BigDecimal getVlTxSiscargaReais() {
		return vlTxSiscargaReais;
	}
	public void setVlTxSiscargaReais(BigDecimal vlTxSiscargaReais) {
		this.vlTxSiscargaReais = vlTxSiscargaReais;
	}
	public BigDecimal getVlTxSiscargaDolar() {
		return vlTxSiscargaDolar;
	}
	public void setVlTxSiscargaDolar(BigDecimal vlTxSiscargaDolar) {
		this.vlTxSiscargaDolar = vlTxSiscargaDolar;
	}
	public BigDecimal getVlTxSiscargaMoeda() {
		return vlTxSiscargaMoeda;
	}
	public void setVlTxSiscargaMoeda(BigDecimal vlTxSiscargaMoeda) {
		this.vlTxSiscargaMoeda = vlTxSiscargaMoeda;
	}
	public BigDecimal getVlCapataziaReais() {
		return vlCapataziaReais;
	}
	public void setVlCapataziaReais(BigDecimal vlCapataziaReais) {
		this.vlCapataziaReais = vlCapataziaReais;
	}
	public BigDecimal getVlCapataziaDolar() {
		return vlCapataziaDolar;
	}
	public void setVlCapataziaDolar(BigDecimal vlCapataziaDolar) {
		this.vlCapataziaDolar = vlCapataziaDolar;
	}
	public BigDecimal getVlCapataziaMoeda() {
		return vlCapataziaMoeda;
	}
	public void setVlCapataziaMoeda(BigDecimal vlCapataziaMoeda) {
		this.vlCapataziaMoeda = vlCapataziaMoeda;
	}
	public BigDecimal getVlAfrmmReais() {
		return vlAfrmmReais;
	}
	public void setVlAfrmmReais(BigDecimal vlAfrmmReais) {
		this.vlAfrmmReais = vlAfrmmReais;
	}
	public BigDecimal getVlAfrmmDolar() {
		return vlAfrmmDolar;
	}
	public void setVlAfrmmDolar(BigDecimal vlAfrmmDolar) {
		this.vlAfrmmDolar = vlAfrmmDolar;
	}
	public BigDecimal getVlAfrmmMoeda() {
		return vlAfrmmMoeda;
	}
	public void setVlAfrmmMoeda(BigDecimal vlAfrmmMoeda) {
		this.vlAfrmmMoeda = vlAfrmmMoeda;
	}
	public BigDecimal getVlArmazPortoReais() {
		return vlArmazPortoReais;
	}
	public void setVlArmazPortoReais(BigDecimal vlArmazPortoReais) {
		this.vlArmazPortoReais = vlArmazPortoReais;
	}
	public BigDecimal getVlArmazPortoDolar() {
		return vlArmazPortoDolar;
	}
	public void setVlArmazPortoDolar(BigDecimal vlArmazPortoDolar) {
		this.vlArmazPortoDolar = vlArmazPortoDolar;
	}
	public BigDecimal getVlArmazPortoMoeda() {
		return vlArmazPortoMoeda;
	}
	public void setVlArmazPortoMoeda(BigDecimal vlArmazPortoMoeda) {
		this.vlArmazPortoMoeda = vlArmazPortoMoeda;
	}
	public BigDecimal getVlTranspRemocaoReais() {
		return vlTranspRemocaoReais;
	}
	public void setVlTranspRemocaoReais(BigDecimal vlTranspRemocaoReais) {
		this.vlTranspRemocaoReais = vlTranspRemocaoReais;
	}
	public BigDecimal getVlTranspRemocaoDolar() {
		return vlTranspRemocaoDolar;
	}
	public void setVlTranspRemocaoDolar(BigDecimal vlTranspRemocaoDolar) {
		this.vlTranspRemocaoDolar = vlTranspRemocaoDolar;
	}
	public BigDecimal getVlTranspRemocaoMoeda() {
		return vlTranspRemocaoMoeda;
	}
	public void setVlTranspRemocaoMoeda(BigDecimal vlTranspRemocaoMoeda) {
		this.vlTranspRemocaoMoeda = vlTranspRemocaoMoeda;
	}
	public BigDecimal getVlArmazPortoSecoReais() {
		return vlArmazPortoSecoReais;
	}
	public void setVlArmazPortoSecoReais(BigDecimal vlArmazPortoSecoReais) {
		this.vlArmazPortoSecoReais = vlArmazPortoSecoReais;
	}
	public BigDecimal getVlArmazPortoSecoDolar() {
		return vlArmazPortoSecoDolar;
	}
	public void setVlArmazPortoSecoDolar(BigDecimal vlArmazPortoSecoDolar) {
		this.vlArmazPortoSecoDolar = vlArmazPortoSecoDolar;
	}
	public BigDecimal getVlArmazPortoSecoMoeda() {
		return vlArmazPortoSecoMoeda;
	}
	public void setVlArmazPortoSecoMoeda(BigDecimal vlArmazPortoSecoMoeda) {
		this.vlArmazPortoSecoMoeda = vlArmazPortoSecoMoeda;
	}
	public BigDecimal getVlTranspEntregaReais() {
		return vlTranspEntregaReais;
	}
	public void setVlTranspEntregaReais(BigDecimal vlTranspEntregaReais) {
		this.vlTranspEntregaReais = vlTranspEntregaReais;
	}
	public BigDecimal getVlTranspEntregaDolar() {
		return vlTranspEntregaDolar;
	}
	public void setVlTranspEntregaDolar(BigDecimal vlTranspEntregaDolar) {
		this.vlTranspEntregaDolar = vlTranspEntregaDolar;
	}
	public BigDecimal getVlTranspEntregaMoeda() {
		return vlTranspEntregaMoeda;
	}
	public void setVlTranspEntregaMoeda(BigDecimal vlTranspEntregaMoeda) {
		this.vlTranspEntregaMoeda = vlTranspEntregaMoeda;
	}
	public BigDecimal getVlDesembaracoReais() {
		return vlDesembaracoReais;
	}
	public void setVlDesembaracoReais(BigDecimal vlDesembaracoReais) {
		this.vlDesembaracoReais = vlDesembaracoReais;
	}
	public BigDecimal getVlDesembaracoDolar() {
		return vlDesembaracoDolar;
	}
	public void setVlDesembaracoDolar(BigDecimal vlDesembaracoDolar) {
		this.vlDesembaracoDolar = vlDesembaracoDolar;
	}
	public BigDecimal getVlDesembaracoMoeda() {
		return vlDesembaracoMoeda;
	}
	public void setVlDesembaracoMoeda(BigDecimal vlDesembaracoMoeda) {
		this.vlDesembaracoMoeda = vlDesembaracoMoeda;
	}
	public BigDecimal getVlMvwReais() {
		return vlMvwReais;
	}
	public void setVlMvwReais(BigDecimal vlMvwReais) {
		this.vlMvwReais = vlMvwReais;
	}
	public BigDecimal getVlMvwDolar() {
		return vlMvwDolar;
	}
	public void setVlMvwDolar(BigDecimal vlMvwDolar) {
		this.vlMvwDolar = vlMvwDolar;
	}
	public BigDecimal getVlMvwMoeda() {
		return vlMvwMoeda;
	}
	public void setVlMvwMoeda(BigDecimal vlMvwMoeda) {
		this.vlMvwMoeda = vlMvwMoeda;
	}
	public BigDecimal getVlTotalDespesaMvwReais() {
		return vlTotalDespesaMvwReais;
	}
	public void setVlTotalDespesaMvwReais(BigDecimal vlTotalDespesaMvwReais) {
		this.vlTotalDespesaMvwReais = vlTotalDespesaMvwReais;
	}
	public BigDecimal getVlTotalDespesaMvwDolar() {
		return vlTotalDespesaMvwDolar;
	}
	public void setVlTotalDespesaMvwDolar(BigDecimal vlTotalDespesaMvwDolar) {
		this.vlTotalDespesaMvwDolar = vlTotalDespesaMvwDolar;
	}
	public BigDecimal getVlTotalDespesaMvwMoeda() {
		return vlTotalDespesaMvwMoeda;
	}
	public void setVlTotalDespesaMvwMoeda(BigDecimal vlTotalDespesaMvwMoeda) {
		this.vlTotalDespesaMvwMoeda = vlTotalDespesaMvwMoeda;
	}
	public BigDecimal getVlTotalDespesaImpReais() {
		return vlTotalDespesaImpReais;
	}
	public void setVlTotalDespesaImpReais(BigDecimal vlTotalDespesaImpReais) {
		this.vlTotalDespesaImpReais = vlTotalDespesaImpReais;
	}
	public BigDecimal getVlTotalDespesaImpDolar() {
		return vlTotalDespesaImpDolar;
	}
	public void setVlTotalDespesaImpDolar(BigDecimal vlTotalDespesaImpDolar) {
		this.vlTotalDespesaImpDolar = vlTotalDespesaImpDolar;
	}
	public BigDecimal getVlTotalDespesaImpMoeda() {
		return vlTotalDespesaImpMoeda;
	}
	public void setVlTotalDespesaImpMoeda(BigDecimal vlTotalDespesaImpMoeda) {
		this.vlTotalDespesaImpMoeda = vlTotalDespesaImpMoeda;
	}
	public Date getDtAbertura() {
		return dtAbertura;
	}
	public void setDtAbertura(Date dtAbertura) {
		this.dtAbertura = dtAbertura;
	}
	public Date getDtCalculo() {
		return dtCalculo;
	}
	public void setDtCalculo(Date dtCalculo) {
		this.dtCalculo = dtCalculo;
	}
	public Date getDtCancelamento() {
		return dtCancelamento;
	}
	public void setDtCancelamento(Date dtCancelamento) {
		this.dtCancelamento = dtCancelamento;
	}
	public String getSnCalculado() {
		return snCalculado;
	}
	public void setSnCalculado(String snCalculado) {
		this.snCalculado = snCalculado;
	}
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public BigDecimal getVlTaxaMoedaFiscal() {
		return vlTaxaMoedaFiscal;
	}
	public void setVlTaxaMoedaFiscal(BigDecimal vlTaxaMoedaFiscal) {
		this.vlTaxaMoedaFiscal = vlTaxaMoedaFiscal;
	}
	public BigDecimal getVlTaxaMoedaFob() {
		return vlTaxaMoedaFob;
	}
	public void setVlTaxaMoedaFob(BigDecimal vlTaxaMoedaFob) {
		this.vlTaxaMoedaFob = vlTaxaMoedaFob;
	}
	public BigDecimal getVlTaxaMoedaFrete() {
		return vlTaxaMoedaFrete;
	}
	public void setVlTaxaMoedaFrete(BigDecimal vlTaxaMoedaFrete) {
		this.vlTaxaMoedaFrete = vlTaxaMoedaFrete;
	}
	public BigDecimal getVlTaxaMoedaSeguro() {
		return vlTaxaMoedaSeguro;
	}
	public void setVlTaxaMoedaSeguro(BigDecimal vlTaxaMoedaSeguro) {
		this.vlTaxaMoedaSeguro = vlTaxaMoedaSeguro;
	}
	public Moeda getMoedaFiscal() {
		return moedaFiscal;
	}
	public void setMoedaFiscal(Moeda moedaFiscal) {
		this.moedaFiscal = moedaFiscal;
	}
	public Moeda getMoedaFob() {
		return moedaFob;
	}
	public void setMoedaFob(Moeda moedaFob) {
		this.moedaFob = moedaFob;
	}
	public Moeda getMoedaFrete() {
		return moedaFrete;
	}
	public void setMoedaFrete(Moeda moedaFrete) {
		this.moedaFrete = moedaFrete;
	}
	public Moeda getMoedaSeguro() {
		return moedaSeguro;
	}
	public void setMoedaSeguro(Moeda moedaSeguro) {
		this.moedaSeguro = moedaSeguro;
	}
	
	
	
		
}
