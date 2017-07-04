package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.jpa.TransportadoraContato;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Transportadoras;
import br.com.eagleeye.service.CadastrarTransportadora;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.Functions;


@ManagedBean
@ViewScoped
public class TransportadoraBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Transportadoras repositorio = new Transportadoras();
	Transportadora transportadora = new Transportadora();
	TransportadoraContato transportadoraContatoNew = new TransportadoraContato();
	CadastrarTransportadora cadastar = new CadastrarTransportadora() ;
	List<TransportadoraContato> transportadoraContato;
	boolean incluindoCLienteContato = false;
	private int index;




	public TransportadoraBean() {
	}
	@PostConstruct
	public void init(){
		// alterar
		if(!isAlterando()){
			valoresDefaultNovoRegistro();
		}
	}
	public void valoresDefaultNovoRegistro(){
			transportadora.setDtCadastro(new Timestamp(System.currentTimeMillis()));
			transportadora.setAtivo(true);
	}
	public boolean isAlterando(){
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){
			transportadora = repositorio.get(Long.valueOf(id));
			return true;
		}
		return false;
	}
	public TransportadoraContato getTransportadoraContatoNew() {
		return transportadoraContatoNew;
	}

	public void setTransportadoraContatoNew(TransportadoraContato transportadoraContatoNew) {
		this.transportadoraContatoNew = transportadoraContatoNew;
	}

	public CadastrarTransportadora getCadastar() {
		return cadastar;
	}

	public void setCadastar(CadastrarTransportadora cadastar) {
		this.cadastar = cadastar;
	}


	public String gravar(){
		String ret = cadastar.adicionar(transportadora); 
		if(ret.equals("")){
			transportadora = new Transportadora();
			new Mensagem().mensagensGeralAviso("Transportadora salvo com sucesso!");
			return "ListaTransportadora";
		}else{
			new Mensagem().mensagensGeralErro("Erro:",ret);
		}
		return "";
	}
	public void cancelar(){
		transportadora = new Transportadora();
	}
	public void excluirPessoaContato(TransportadoraContato transportadoraContato) {
		transportadora.getTransportadoraContato().remove(transportadoraContato);
	}
	public String gravarPessoaContato() {

		if(transportadoraContatoNew.getTransportadora() != null){
			gravarAlterandoTransportadoraContato();
		}else{
			gravarIncluindoTransportadoraContato();
		}

		setIndex(0);
		incluindoCLienteContato = false;
		return "";
	}
	public void gravarIncluindoTransportadoraContato(){
		this.transportadoraContatoNew.setTransportadora(transportadora);
		//this.transportadoraContatoNew.setIdContato(getTransportadoraContato() == null?1:getTransportadoraContato().size()+1);

		if (transportadora.getTransportadoraContato() == null) {
			transportadora.setTransportadoraContato(new HashSet<TransportadoraContato>());
		}

		if(transportadoraContatoNew.getCodigo().equals("")){
			transportadoraContatoNew.setCodigo(Functions.ColocaZeroInicio(getTransportadoraContato() == null?String.valueOf(1):String.valueOf(getTransportadoraContato().size()+1), 6));
		}

		transportadora.getTransportadoraContato().add(this.transportadoraContatoNew);
		this.transportadoraContatoNew = new TransportadoraContato();
	}
	public void gravarAlterandoTransportadoraContato(){
		for(TransportadoraContato contatos : transportadora.getTransportadoraContato()){
			if(contatos.getIdContato() == transportadoraContatoNew.getIdContato()){
				contatos = transportadoraContatoNew;
				break;
			}
		}
		this.transportadoraContatoNew = new TransportadoraContato();
	}
	public void editarPessoaContato(TransportadoraContato contato){
		transportadoraContatoNew = contato;
		incluindoCLienteContato = true;
		setIndex(1);
	}


	public Transportadora getTransportadora() {
		
		return transportadora;
	}
	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}


	public List<TransportadoraContato> getTransportadoraContato() {
		transportadoraContato = transportadora.getTransportadoraContato()== null ? new ArrayList<TransportadoraContato>() : new ArrayList<TransportadoraContato>(this.transportadora.getTransportadoraContato());
		return transportadoraContato;
	}
	public void setTransportadoraContato(List<TransportadoraContato> transportadoraContato) {
		this.transportadoraContato = transportadoraContato;
	}
	public boolean habilitarDetalhesContato(){
		return incluindoCLienteContato;
	}
	public void inserirTransportadoraContato(){
		incluindoCLienteContato = true;
		transportadoraContatoNew = new TransportadoraContato();
		setIndex(1);
	}
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	public void refresh() {  
		FacesContext context = FacesContext.getCurrentInstance();  
		Application application = context.getApplication();  
		ViewHandler viewHandler = application.getViewHandler();  
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());  
		context.setViewRoot(viewRoot);  
		context.renderResponse();  
	}  


	//@SuppressWarnings("unused")
	//public   EntityManager getManager() {
	//	FacesContext fc = FacesContext.getCurrentInstance();
	//	ExternalContext ec = fc.getExternalContext();
	//	HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	//	return (EntityManager) request.getAttribute("EntityManager");
	//}
}