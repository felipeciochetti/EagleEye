package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.filtros.FiltroProcesso;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.service.CancelarProcesso;
import br.com.eagleeye.table.ProcessoTable;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.FormatUtils;

@ManagedBean
@ViewScoped
public class ProcessoListaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Processos repositorio = new Processos();
	private Processo selectProcesso  ;
	private Processo processoCancelar;
	String valorPesquisa;
	CancelarProcesso cancelar = new CancelarProcesso();
	List<Processo> lista;
	ProcessoTable list;
	FiltroProcesso filtro = new FiltroProcesso();
	
	public ProcessoListaBean() {
	}

	@PostConstruct
	public void init(){
		
		String ref =  ContextUtils.getParemeterMap("ref"); 
		if(ref != null){
			valorPesquisa = ref;
		}
		
		
		list = new ProcessoTable(repositorio,valorPesquisa);
	}
	
	public List<Processo> getLista() {
		
		if(valorPesquisa != null){
			return repositorio.getProcessosPorCampo("referenciaMvw", valorPesquisa);
		}else{
			return repositorio.getTodosProcessosNaoCancelados();
		}
	}
	public ProcessoTable getList() {
		return list ;//= new ProcessoTable(getLista());
	}
	
	public String preparaAlterar(Processo processo) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("processoAlterar", String.valueOf(processo.getIdProcesso()));
		return "CadastroProcesso.xhtml?faces-redirect=true";
	}
	public void preparaCancelar(Processo processo) {
		this.processoCancelar = processo;
	}

	public void cancelar() {
		if(processoCancelar.getDtCancelamento() != null){
			new Mensagem().mensagensGeralAviso("Processo já esta cancelado!");
			return ;
		}
		processoCancelar.setDtCancelamento(new Timestamp(System.currentTimeMillis()));
		cancelar.cancelar(processoCancelar);
		list = new ProcessoTable(repositorio,valorPesquisa);
		
		
		
//		processoExcluir = null;
	}
	public void executaFiltro(){
		Map map = filtro.getFiltro();
		
		list = new ProcessoTable(repositorio,(String)map.get("where"), (List)map.get("param"));
	
		filtro = new FiltroProcesso();
		
		
	}
	public void setList(ProcessoTable list) {
		this.list = list;
	}

	public Processo getSelectProcesso() {
		return selectProcesso;
	}

	public void setSelectProcesso(Processo selectProcesso) {
		this.selectProcesso = selectProcesso;
	}

	public Processo getProcessoExcluir() {
		return processoCancelar;
	}

	public void setProcessoExcluir(Processo processoExcluir) {
		this.processoCancelar = processoExcluir;
	}
	public String formataData(Date data) {
		return FormatUtils.getInstance().formatarData(data);
	}

	public FiltroProcesso getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroProcesso filtro) {
		this.filtro = filtro;
	}
	
	
	

}
