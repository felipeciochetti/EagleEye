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
import br.com.eagleeye.jpa.Exportador;
import br.com.eagleeye.jpa.ExportadorContato;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Exportadores;
import br.com.eagleeye.service.CadastrarExportador;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.Functions;


@ManagedBean
@ViewScoped
public class ExportadorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Exportadores repositorio = new Exportadores();
	Exportador exportador = new Exportador();
	ExportadorContato exportadorContatoNew = new ExportadorContato();
	CadastrarExportador cadastar = new CadastrarExportador() ;
	List<ExportadorContato> exportadorContato;
	boolean incluindoCLienteContato = false;
	private int index;




	public ExportadorBean() {
	}
	@PostConstruct
	public void init(){
		// alterar
		if(!isAlterando()){
			valoresDefaultNovoRegistro();
		}
	}
	public void valoresDefaultNovoRegistro(){
			exportador.setDtCadastro(new Timestamp(System.currentTimeMillis()));
			exportador.setAtivo(true);
	}
	public boolean isAlterando(){
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){
			exportador = repositorio.get(Long.valueOf(id));
			return true;
		}
		return false;
	}
	public ExportadorContato getExportadorContatoNew() {
		return exportadorContatoNew;
	}

	public void setExportadorContatoNew(ExportadorContato exportadorContatoNew) {
		this.exportadorContatoNew = exportadorContatoNew;
	}

	public CadastrarExportador getCadastar() {
		return cadastar;
	}

	public void setCadastar(CadastrarExportador cadastar) {
		this.cadastar = cadastar;
	}


	public String gravar(){
		
		String ret = cadastar.adicionar(exportador); 
		if(ret.equals("")){
			exportador = new Exportador();
			new Mensagem().mensagensGeralAviso("Exportador salvo com sucesso!");
			return "ListaExportador";
		}else{
			new Mensagem().mensagensGeralErro("Erro:",ret);
		}
		return "";
		
	}
	public void cancelar(){
		exportador = new Exportador();
	}
	public void excluirPessoaContato(ExportadorContato exportadorContato) {
		exportador.getExportadorContato().remove(exportadorContato);
	}
	public String gravarPessoaContato() {


		if(exportadorContatoNew.getExportador() != null){
			gravarAlterandoExportadorContato();
		}else{
			gravarIncluindoExportadorContato();
		}

		setIndex(0);
		incluindoCLienteContato = false;
		return "";
	}
	public void gravarIncluindoExportadorContato(){
		this.exportadorContatoNew.setExportador(exportador);
		//this.exportadorContatoNew.setIdContato(getExportadorContato() == null?1:getExportadorContato().size()+1);

		if (exportador.getExportadorContato() == null) {
			exportador.setExportadorContato(new HashSet<ExportadorContato>());
		}

		if(exportadorContatoNew.getCodigo().equals("")){
			exportadorContatoNew.setCodigo(Functions.ColocaZeroInicio(getExportadorContato() == null?String.valueOf(1):String.valueOf(getExportadorContato().size()+1), 6));
		}

		exportador.getExportadorContato().add(this.exportadorContatoNew);
		this.exportadorContatoNew = new ExportadorContato();
	}
	public void gravarAlterandoExportadorContato(){
		for(ExportadorContato contatos : exportador.getExportadorContato()){
			if(contatos.getIdContato() == exportadorContatoNew.getIdContato()){
				contatos = exportadorContatoNew;
				break;
			}
		}
		this.exportadorContatoNew = new ExportadorContato();
	}
	public void editarPessoaContato(ExportadorContato contato){
		exportadorContatoNew = contato;
		incluindoCLienteContato = true;
		setIndex(1);
	}
	

	public Exportador getExportador() {
		
		return exportador;
	}
	public void setExportador(Exportador exportador) {
		this.exportador = exportador;
	}


	public List<ExportadorContato> getExportadorContato() {
		exportadorContato = exportador.getExportadorContato()== null ? new ArrayList<ExportadorContato>() : new ArrayList<ExportadorContato>(this.exportador.getExportadorContato());
		return exportadorContato;
	}
	public void setExportadorContato(List<ExportadorContato> exportadorContato) {
		this.exportadorContato = exportadorContato;
	}
	public boolean habilitarDetalhesContato(){
		return incluindoCLienteContato;
	}
	public void inserirExportadorContato(){
		incluindoCLienteContato = true;
		exportadorContatoNew = new ExportadorContato();
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