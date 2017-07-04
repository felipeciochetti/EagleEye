package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Numerario;
import br.com.eagleeye.repositorio.Numerarios;
import br.com.eagleeye.service.ExcluirNumerario;
import br.com.eagleeye.table.NumerarioTable;

@ManagedBean
@ViewScoped
public class NumerarioListaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Numerarios Numerario = new Numerarios();
	private Numerario selectNumerario  ;
	private Numerario NumerarioExcluir;
	ExcluirNumerario excluir = new ExcluirNumerario();
	List<Numerario> lista;
	NumerarioTable list;
	
	public NumerarioListaBean() {
	}

	@PostConstruct
	public void init(){
		list = new NumerarioTable(Numerario);
	}
	
	public List<Numerario> getLista() {
		return Numerario.getTodosNumerarios();
	}
	public NumerarioTable getList() {
		return list ;//= new NumerarioTable(getLista());
	}
	
	public String preparaAlterar(Numerario Numerario) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("NumerarioAlterar", String.valueOf(Numerario.getIdNumerario()));
		return "CadastroNumerario.xhtml?faces-redirect=true";
	}
	public void preparaExcluir(Numerario Numerario) {
		this.NumerarioExcluir = Numerario;
	}

	public void excluir() {
		excluir.excluir(NumerarioExcluir);
		NumerarioExcluir = null;
		list = new NumerarioTable(Numerario);
	}
	
	public void setList(NumerarioTable list) {
		this.list = list;
	}

	public Numerario getSelectNumerario() {
		return selectNumerario;
	}

	public void setSelectNumerario(Numerario selectNumerario) {
		this.selectNumerario = selectNumerario;
	}

	public Numerario getNumerarioExcluir() {
		return NumerarioExcluir;
	}

	public void setNumerarioExcluir(Numerario NumerarioExcluir) {
		this.NumerarioExcluir = NumerarioExcluir;
	}
	
	

	

}
