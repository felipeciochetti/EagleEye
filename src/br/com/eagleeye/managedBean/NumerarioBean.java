package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.eagleeye.complete.MoedaComplete;
import br.com.eagleeye.complete.ProcessoComplete;
import br.com.eagleeye.jpa.Moeda;
import br.com.eagleeye.jpa.Numerario;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Moedas;
import br.com.eagleeye.repositorio.Numerarios;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.service.CadastrarNumerario;
import br.com.eagleeye.service.CalculoNumerario;
import br.com.eagleeye.util.ContextUtils;


@ManagedBean
@ViewScoped
public class NumerarioBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Numerario numerario = new Numerario();
	Moeda moeda;
	Processo processo;
	Moedas repositorioMoeda = new Moedas();
	Processos repositorioProcesso = new Processos();
	Numerarios repositorio = new Numerarios();
	CadastrarNumerario cadastrar = new CadastrarNumerario();


	public NumerarioBean() {
		
	}
	@PostConstruct
	public void init(){
		
		isAlterando();
	}
	public boolean isAlterando(){
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){
			numerario = repositorio.get(Long.valueOf(id));
			return true;
		}
		return false;
	}
	public Numerario getNumerario() {
		return numerario;
	}
	public void setNumerario(Numerario numerario) {
		this.numerario = numerario;
	}

	public String gravar(){
		
		String msg = cadastrar.adicionar(numerario); 
		if(msg.equals("")){
			new Mensagem().mensagensGeralAviso("Numerário salvo com sucesso!");
		}else{
			new Mensagem().mensagensGeralErro("Erro:",msg);
		}
		
		return "ListaNumerario";
	}
	public void calcular(){
		CalculoNumerario calculo = new CalculoNumerario(numerario);
		String ret = calculo.calculoNumerario();
		if(ret.equals("")){
			new Mensagem().mensagensGeralAviso("Calculo realizado com sucesso!");
		}else{
			new Mensagem().mensagensGeralErro("Erro:",ret);
		}
	}
	public List<Moeda> completeMoeda(String query) {
		return new MoedaComplete(repositorioMoeda).execute(query);
	}
	public List<Processo> completeProcesso(String query) {
		return new ProcessoComplete(repositorioProcesso).executeNumerario(query);
	}
	public Moeda getMoeda() {
		return moeda;
	}
	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}
	
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public Moedas getRepositorioMoeda() {
		return repositorioMoeda;
	}
	public void setRepositorioMoeda(Moedas repositorioMoeda) {
		this.repositorioMoeda = repositorioMoeda;
	}
	
}