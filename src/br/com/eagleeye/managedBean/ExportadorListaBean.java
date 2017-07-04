package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Exportador;
import br.com.eagleeye.repositorio.Exportadores;
import br.com.eagleeye.service.ExcluirExportador;
import br.com.eagleeye.table.ExportadorTable;

@ManagedBean
@ViewScoped
public class ExportadorListaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Exportadores exportador = new Exportadores();
	private Exportador selectExportador  ;
	private Exportador exportadorExcluir;
	ExcluirExportador excluir = new ExcluirExportador();
	List<Exportador> lista;
	ExportadorTable list;
	
	public ExportadorListaBean() {
	}

	@PostConstruct
	public void init(){
		list = new ExportadorTable(exportador);
	}
	
	public List<Exportador> getLista() {
		return exportador.getTodosExportadores();
	}
	public ExportadorTable getList() {
		return list ;//= new ExportadorTable(getLista());
	}
	
	public String preparaAlterar(Exportador exportador) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("exportadorAlterar", String.valueOf(exportador.getIdExportador()));
		return "CadastroExportador.xhtml?faces-redirect=true";
	}
	public void preparaExcluir(Exportador exportador) {
		this.exportadorExcluir = exportador;
	}

	public void excluir() {
		excluir.excluir(exportadorExcluir);
		exportadorExcluir = null;
		list = new ExportadorTable(exportador);
	}
	
	public void setList(ExportadorTable list) {
		this.list = list;
	}

	public Exportador getSelectExportador() {
		return selectExportador;
	}

	public void setSelectExportador(Exportador selectExportador) {
		this.selectExportador = selectExportador;
	}

	public Exportador getExportadorExcluir() {
		return exportadorExcluir;
	}

	public void setExportadorExcluir(Exportador exportadorExcluir) {
		this.exportadorExcluir = exportadorExcluir;
	}
	
	

	

}
