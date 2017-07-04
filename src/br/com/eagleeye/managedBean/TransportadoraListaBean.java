package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.repositorio.Transportadoras;
import br.com.eagleeye.service.ExcluirTransportadora;
import br.com.eagleeye.table.TransportadoraTable;

@ManagedBean
@ViewScoped
public class TransportadoraListaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Transportadoras transportadora = new Transportadoras();
	private Transportadora selectTransportadora  ;
	private Transportadora transportadoraExcluir;
	ExcluirTransportadora excluir = new ExcluirTransportadora();
	List<Transportadora> lista;
	TransportadoraTable list;
	
	public TransportadoraListaBean() {
	}

	@PostConstruct
	public void init(){
		list = new TransportadoraTable(transportadora);
	}
	
	public List<Transportadora> getLista() {
		return transportadora.getTodosTransportadoras();
	}
	public TransportadoraTable getList() {
		return list ;//= new TransportadoraTable(getLista());
	}
	
	public String preparaAlterar(Transportadora transportadora) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("transportadoraAlterar", String.valueOf(transportadora.getIdTransportadora()));
		return "CadastroTransportadora";
	}
	public void preparaExcluir(Transportadora transportadora) {
		this.transportadoraExcluir = transportadora;
	}

	public void excluir() {
		excluir.excluir(transportadoraExcluir);
		transportadoraExcluir = null;
		list = new TransportadoraTable(transportadora);
	}
	
	public void setList(TransportadoraTable list) {
		this.list = list;
	}

	public Transportadora getSelectTransportadora() {
		return selectTransportadora;
	}

	public void setSelectTransportadora(Transportadora selectTransportadora) {
		this.selectTransportadora = selectTransportadora;
	}

	public Transportadora getTransportadoraExcluir() {
		return transportadoraExcluir;
	}

	public void setTransportadoraExcluir(Transportadora transportadoraExcluir) {
		this.transportadoraExcluir = transportadoraExcluir;
	}
	
	

	

}
