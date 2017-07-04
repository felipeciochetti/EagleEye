package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.filtros.FiltroProcesso;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.FormatUtils;

@ManagedBean
@ViewScoped
public class ProcessoListaViewBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Processos repositorio ;
	Usuario usuario ;
	List<Processo> lista;
	String campoPesquisa = "";
	String valorPesquisa = "";
	//FiltroProcesso enumfiltroProcesso;
	FiltroProcesso filtro;
	
	
	public ProcessoListaViewBean() {
	}

	@PostConstruct
	public void init(){
		repositorio = new Processos();
		usuario = ContextUtils.getInstance().getUsuarioLogado();
		
		String ref =  ContextUtils.getParemeterMap("ref"); 
		if(ref != null){
			valorPesquisa = ref;
		}
		
		getLista();
		
	}
	

	public List<Processo> getLista() {
		
		
		if(valorPesquisa == null || valorPesquisa.equals("")){
			if(usuario.getNivel().equals("C")){
				lista = repositorio.getProcessosPorCliente(usuario.getCliente().getIdCliente());
			}else{
				lista = repositorio.getTodosProcessosNaoCancelados();
			}
		}else if(usuario.getNivel().equals("C")){
				lista = repositorio.getProcessosPorCampoECliente(usuario.getCliente().getIdCliente() ,"referenciaMvw",valorPesquisa);
		}else{
			lista = repositorio.getProcessosPorCampo("referenciaMvw", valorPesquisa);
		}
		return lista;
	}
	
	public String preparaAlterar(Processo processo) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("processoView", String.valueOf(processo.getIdProcesso()));
		return "ProcessoView";
	}
	public void executaFiltro(){
		Map map = filtro.getFiltro();
		
	 lista =repositorio.getProcessosPorCampos((String)map.get("where"), (List)map.get("param"));
	
		
	}
	public void pesquisarProcesso(){
		//lista = repositorio.getProcessosPorCampo("referenciaMvw", valorPesquisa);
		//atualizar Lista
	}
	
	public String formataData(Date data) {
		return FormatUtils.getInstance().formatarData(data);
	}

	public Processos getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Processos repositorio) {
		this.repositorio = repositorio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	

	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}

	public String getValorPesquisa() {
		return valorPesquisa;
	}

	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;
	}


	public void setLista(List<Processo> lista) {
		this.lista = lista;
	}

}
