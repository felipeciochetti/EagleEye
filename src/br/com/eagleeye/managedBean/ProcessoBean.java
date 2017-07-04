package br.com.eagleeye.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FileUploadEvent;

import br.com.eagleeye.complete.ClienteComplete;
import br.com.eagleeye.complete.ExportadorComplete;
import br.com.eagleeye.complete.NcmComplete;
import br.com.eagleeye.complete.PaisComplete;
import br.com.eagleeye.complete.TransportadoraComplete;
import br.com.eagleeye.complete.ViaTransporteComplete;
import br.com.eagleeye.enums.Parametrizacao;
import br.com.eagleeye.enums.SimNao;
import br.com.eagleeye.enums.SimNaoQual;
import br.com.eagleeye.enums.StatusCarga;
import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;
import br.com.eagleeye.enums.TipoDocumento;
import br.com.eagleeye.enums.TipoEmbarque;
import br.com.eagleeye.filtros.FiltroProcesso;
import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.jpa.DocumentoProcesso;
import br.com.eagleeye.jpa.Exportador;
import br.com.eagleeye.jpa.LinhaTempoDocProcesso;
import br.com.eagleeye.jpa.LinhaTempoEmbProcesso;
import br.com.eagleeye.jpa.Ncm;
import br.com.eagleeye.jpa.Pais;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.jpa.ProcessoNcm;
import br.com.eagleeye.jpa.ProcessoNcmPK;
import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.jpa.ViaTransporte;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Clientes;
import br.com.eagleeye.repositorio.Exportadores;
import br.com.eagleeye.repositorio.Ncms;
import br.com.eagleeye.repositorio.Paises;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.repositorio.Transportadoras;
import br.com.eagleeye.repositorio.ViaTransportes;
import br.com.eagleeye.service.AlterarStatusLinhaTempoDoc;
import br.com.eagleeye.service.AlterarStatusLinhaTempoEmb;
import br.com.eagleeye.service.CadastrarCliente;
import br.com.eagleeye.service.CadastrarExportador;
import br.com.eagleeye.service.CadastrarProcesso;
import br.com.eagleeye.service.GerarLinhaTempoDocumento;
import br.com.eagleeye.service.GerarLinhaTempoEmbarque;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.FormatUtils;
import br.com.eagleeye.util.UploadFileUtils;


@ManagedBean
@ViewScoped
public class ProcessoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Processos repositorio = new Processos();
	Processo processo = new Processo();
	CadastrarProcesso cadastar = new CadastrarProcesso() ;
	AlterarStatusLinhaTempoDoc alterarStatusDoc = new AlterarStatusLinhaTempoDoc();
	AlterarStatusLinhaTempoEmb alterarStatusEmb = new AlterarStatusLinhaTempoEmb();

	List<DocumentoProcesso> documentoProcesso;
	List<LinhaTempoDocProcesso> linhaTempoDocProcesso;
	List<LinhaTempoEmbProcesso> linhaTempoEmbProcesso;
	LinhaTempoDocProcesso linhaTempoDocAlterar;
	LinhaTempoEmbProcesso linhaTempoEmbAlterar;
	// enuns
	TipoEmbarque tiposEmbarques;
	StatusCarga statusCarga;
	SimNaoQual simNaoQual;
	Parametrizacao parametrizacao;
	SimNao simNao;
	TipoDocumento tipoDocumento;
	StatusFaseLinhaTempoDoc statusFaseLinhaTempo;
	
	
	boolean isAvarias;
	
	GerarLinhaTempoDocumento linhaTempoDoc ;
	GerarLinhaTempoEmbarque linhaTempoEmb ;

	private String tipoDoc;
	private String statusFase;
	private String linkArquivoAnexo;
	Exportador exportadorRapido = new Exportador();
	Cliente clienteRapido = new Cliente();
	Clientes repositorioCliente = new Clientes();
	Paises repositorioPais = new Paises();
	Exportadores repositorioExportadores = new Exportadores();
	Ncms repositorioNcm = new Ncms();
	Transportadoras repositorioTransportadora = new Transportadoras();
	ViaTransportes repositorioViaTransporte = new ViaTransportes();
	Ncm ncm;
	ArrayList<ProcessoNcm> processoNcm;

	String linhaTempoDocumento;

	private boolean isAlterando;

	public ProcessoBean() {
	}
	@PostConstruct
	public void init(){

		if(!isAlterandoProcesso()){
			valoresDefaultNovoRegistro();
		}
	}

	public void valoresDefaultNovoRegistro(){
		processo.setDtAbertura(new Timestamp(System.currentTimeMillis()));
		processo.setSequencialProcesso(repositorio.getSequencialProcesso()+1);
		processo.setLinhaTempoProcesso(cadastar.gerarLinhaTempoDocumentoDefault(processo));
		processo.setLinhaTempoEmbProcesso(cadastar.gerarLinhaTempoEmbarqueDefault(processo));
		processo.setSnNotificarAbertura("S");
	}
	public boolean isAlterandoProcesso(){
		// alterar	
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){
			processo = repositorio.get(Integer.valueOf(id));
			
			if(processo.getSnAvarias()!= null){
				setAvarias("S".equals(processo.getSnAvarias()));
			}
			
			isAlterando = true;
			
			return true;
		}
		return false;

	}
	public void abrirArquivo(String nome) {
		setLinkArquivoAnexo("exibeDocumento?nome=" + processo.getSequencialProcesso() + File.separator + nome + "&tipo=3");
	}
	public void handleFileUpload(FileUploadEvent event) {
		String url = new UploadFileUtils().handleFileUpload(event,String.valueOf(processo.getSequencialProcesso()));
		String fileName = event.getFile().getFileName();
		addAnexo(url , fileName);
	}
	public void addAnexo(String url , String fileName){

		if(processo.getDocumentoProcesso() == null){
			processo.setDocumentoProcesso(new HashSet<DocumentoProcesso>());
		}

		DocumentoProcesso doc = new DocumentoProcesso();
		doc.setProcesso(processo);
		doc.setTipoDocumento(getTipoDoc());
		doc.setNome(fileName);
		doc.setUrl(url);
		processo.getDocumentoProcesso().add(doc);

	}
	public CadastrarProcesso getCadastar() {
		return cadastar;
	}

	public void setCadastar(CadastrarProcesso cadastar) {
		this.cadastar = cadastar;
	}

	public void excluirArquivo( DocumentoProcesso doc) {
		File f = null;
		try {
			String nome = doc.getNome();
			String pathFileName = ContextUtils.getInstance().getPathFilesAttacheds() + String.valueOf(processo.getSequencialProcesso()) + File.separator + nome; 
			f = new File(pathFileName);
			f.delete();

			processo.getDocumentoProcesso().remove(doc);

			new Mensagem().mensagensGeralAviso("Arquivo excluído com sucesso ! " + nome);
		} catch (Exception e) {
			e.printStackTrace();
			new Mensagem().mensagensGeralAviso("Erro durante operação: " + e.getMessage());
		} finally {
			f = null;
		}
	}

	public String gravar(){
		if(cadastar.adicionar(processo)){
			
				processo = new Processo();
				return "ListaProcesso";
		}
		return "";
	}
	public void cancelar(){
		processo = new Processo();
	}
	public void gravarClienteRapido(){
		CadastrarCliente cadastroCliente = new CadastrarCliente();
		clienteRapido.setAtivo(true);
		String ret = 	cadastroCliente.adicionar(clienteRapido);
	if(ret.equals("")){
		new Mensagem().addMessage("msgRapido", "Cliente salvo com sucesso!", FacesMessage.SEVERITY_INFO);
		processo.setCliente(repositorioCliente.get(clienteRapido.getIdCliente()));
		completeCliente("");
		clienteRapido = new Cliente();
	}else{
		new Mensagem().addMessage("msgRapido", ret, FacesMessage.SEVERITY_ERROR);
	}
	
	}
	public void gravarExportadorRapido(){
		CadastrarExportador cadastroExportador = new CadastrarExportador();
		exportadorRapido.setAtivo(true);
		String ret = 	cadastroExportador.adicionar(exportadorRapido);
	if(ret.equals("")){
		new Mensagem().addMessage("msgRapido", "Exportador salvo com sucesso!", FacesMessage.SEVERITY_INFO);
		processo.setExportador(repositorioExportadores.get(exportadorRapido.getIdExportador()));
		completeCliente("");
		exportadorRapido = new Exportador();
	}else{
		new Mensagem().addMessage("msgRapido", ret, FacesMessage.SEVERITY_ERROR);
	}
	
	}
	public void inserirNcm() {
		if (ncm != null && ncm.getIdNcm() > 0 ) {
			if (processo.getProcessoNcm() == null) {
				processo.setProcessoNcm(new HashSet<ProcessoNcm>());
			}

			for (ProcessoNcm vo : processo.getProcessoNcm()) {
				if (vo.getProcessoNcmPK().getNcm().getIdNcm() == ncm.getIdNcm()) {
					return;
				}
			}
			ProcessoNcm processoPessoa = new ProcessoNcm();
			processoPessoa.setProcessoNcmPK(new ProcessoNcmPK());
			processoPessoa.getProcessoNcmPK().setProcesso(processo);
			processoPessoa.getProcessoNcmPK().setNcm(ncm);

			processo.getProcessoNcm().add(processoPessoa);
			ncm = new Ncm();
		}
	}
	
	public void excluirNcm(ProcessoNcm processoNcm) {
		processo.getProcessoNcm().remove(processoNcm);
	}
	public List<Cliente> completeCliente(String query) {
		return new ClienteComplete(repositorioCliente).execute(query);
	}

	public List<Ncm> completeNcm(String query) {
		return new NcmComplete(repositorioNcm).execute(query);
	}


	public List<ViaTransporte> completeViaTransporte(String query) {
		return new ViaTransporteComplete(repositorioViaTransporte).execute(query);
	}


	public List<Transportadora> completeTransportadora(String query) {
		return new TransportadoraComplete(repositorioTransportadora).execute(query);
	}
	public List<Pais> completePais(String query) {
		return new PaisComplete(repositorioPais).execute(query);
	}
	public List<Exportador> completeExportador(String query) {
		return new ExportadorComplete(repositorioExportadores).execute(query);
	}
	
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public Processos getRepositorio() {
		return repositorio;
	}
	public void setRepositorio(Processos repositorio) {
		this.repositorio = repositorio;
	}
	public String getLinkArquivoAnexo() {
		return linkArquivoAnexo;
	}
	public void setLinkArquivoAnexo(String linkArquivoAnexo) {
		this.linkArquivoAnexo = linkArquivoAnexo;
	}

	public String formataData(Date data) {
		return FormatUtils.getInstance().formatarData(data);
	}


	public Clientes getRepositorioCliente() {
		return repositorioCliente;
	}
	public void setRepositorioCliente(Clientes repositorioCliente) {
		this.repositorioCliente = repositorioCliente;
	}
	public Ncms getRepositorioNcm() {
		return repositorioNcm;
	}
	public void setRepositorioNcm(Ncms repositorioNcm) {
		this.repositorioNcm = repositorioNcm;
	}
	public Ncm getNcm() {
		return ncm;
	}
	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}

	public List<ProcessoNcm> getProcessoNcm() {
		this.processoNcm = this.processo.getProcessoNcm() == null ? new ArrayList<ProcessoNcm>() : new ArrayList<ProcessoNcm>(this.processo.getProcessoNcm());
		return this.processoNcm;
	}

	public TipoEmbarque[] getTiposEmbarques(){
		return TipoEmbarque.values();
	}
	public StatusCarga[] getStatusCarga(){
		return StatusCarga.values();
	}
	public SimNaoQual[] getSimNaoQual(){
		return SimNaoQual.values();
	}
	public Parametrizacao[] getParametrizacao(){
		return Parametrizacao.values();
	}
	public SimNao[] getSimNao(){
		return SimNao.values();
	}
	public TipoDocumento[] getTipoDocumento(){
		return TipoDocumento.values();
	}
	public StatusFaseLinhaTempoDoc[] getStatusFaseLinhaTempoDoc(){
		return StatusFaseLinhaTempoDoc.values();
	}

	public void setTiposEmbarques(TipoEmbarque tiposEmbarques) {
		this.tiposEmbarques = tiposEmbarques;
	}

	public void tipoDoc(AjaxBehaviorEvent abe) {
		//
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public Paises getRepositorioPais() {
		return repositorioPais;
	}
	public void setRepositorioPais(Paises repositorioPais) {
		this.repositorioPais = repositorioPais;
	}
	public Transportadoras getRepositorioTransportadora() {
		return repositorioTransportadora;
	}
	public void setRepositorioTransportadora(
			Transportadoras repositorioTransportadora) {
		this.repositorioTransportadora = repositorioTransportadora;
	}
	public ViaTransportes getRepositorioViaTransporte() {
		return repositorioViaTransporte;
	}
	public void setRepositorioViaTransporte(ViaTransportes repositorioViaTransporte) {
		this.repositorioViaTransporte = repositorioViaTransporte;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setStatusCarga(StatusCarga statusCarga) {
		this.statusCarga = statusCarga;
	}
	public void setSimNaoQual(SimNaoQual simNaoQual) {
		this.simNaoQual = simNaoQual;
	}
	
	public void setParametrizacao(Parametrizacao parametrizacao) {
		this.parametrizacao = parametrizacao;
	}
	public void setSimNao(SimNao simNao) {
		this.simNao = simNao;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public void setProcessoNcm(ArrayList<ProcessoNcm> processoNcm) {
		this.processoNcm = processoNcm;
	}


	public List<DocumentoProcesso> getDocumentoProcesso() {
		documentoProcesso = processo.getDocumentoProcesso()== null ? new ArrayList<DocumentoProcesso>() : new ArrayList<DocumentoProcesso>(this.processo.getDocumentoProcesso());
		return documentoProcesso;
	}
	public void setDocumentoProcesso(List<DocumentoProcesso> documentoProcesso) {
		this.documentoProcesso = documentoProcesso;
	}
	
	public List<LinhaTempoEmbProcesso> getLinhaTempoEmbProcesso() {
		linhaTempoEmbProcesso = processo.getLinhaTempoEmbProcesso()== null ? new ArrayList<LinhaTempoEmbProcesso>() : new ArrayList<LinhaTempoEmbProcesso>(this.processo.getLinhaTempoEmbProcesso());
		 Collections.sort(linhaTempoEmbProcesso);
		return linhaTempoEmbProcesso;
	}
	public void setLinhaTempoEmbProcesso(
			List<LinhaTempoEmbProcesso> linhaTempoEmbProcesso) {
		this.linhaTempoEmbProcesso = linhaTempoEmbProcesso;
	}
	public LinhaTempoEmbProcesso getLinhaTempoEmbAlterar() {
		return linhaTempoEmbAlterar;
	}
	public void setLinhaTempoEmbAlterar(LinhaTempoEmbProcesso linhaTempoEmbAlterar) {
		this.linhaTempoEmbAlterar = linhaTempoEmbAlterar;
	}
	public List<LinhaTempoDocProcesso> getLinhaTempoDocProcesso() {
		linhaTempoDocProcesso = processo.getLinhaTempoProcesso()== null ? new ArrayList<LinhaTempoDocProcesso>() : new ArrayList<LinhaTempoDocProcesso>(this.processo.getLinhaTempoProcesso());
		 Collections.sort(linhaTempoDocProcesso);
		 
		 return linhaTempoDocProcesso;
	}
	public void setLinhaTempoDocProcesso(List<LinhaTempoDocProcesso> linhaTempoDocProcesso) {
		this.linhaTempoDocProcesso = linhaTempoDocProcesso;
	}
	public java.lang.String getTipoDocumentoFormatado(String tipo) {

		for (TipoDocumento t: TipoDocumento.values()) {
			if(tipo.equals(t.getValor())){
				return t.getLabel();
			}
		}
		return "";
	}

	public java.lang.String getStatusLinhaTempoDoc(String tipo) {

		for (StatusFaseLinhaTempoDoc t: StatusFaseLinhaTempoDoc.values()) {
			if(tipo.equals(t.getValor())){
				return t.getLabel();
			}
		}
		return "";
	}
	public java.lang.String getStatusLinhaTempoEmb(String tipo) {

		for (StatusFaseLinhaTempoDoc t: StatusFaseLinhaTempoDoc.values()) {
			if(tipo.equals(t.getValor())){
				return t.getLabel();
			}
		}
		return "";
	}


	public void editarStatusLinhatempoDoc(LinhaTempoDocProcesso obj){
		linhaTempoDocAlterar = obj;
	}
	public void editarStatusLinhatempoEmb(LinhaTempoEmbProcesso obj){
		linhaTempoEmbAlterar = obj;
	}
	public String paginalista(){
		
		return "ListaProcesso";
	}
	public String  gravarAlteracaoStatusLinhaTempoDoc(){
		return alterarStatusDoc.gravarAlteracaoStatusLinhaTempoDoc(processo,linhaTempoDocAlterar);
	}
	public String  gravarAlteracaoStatusLinhaTempoEmb(){
		return alterarStatusEmb.gravarAlteracaoStatusLinhaTempoEmb(processo,linhaTempoEmbAlterar);
	}
	public String getLinhaTempoDocumento() {
		linhaTempoDoc = new GerarLinhaTempoDocumento(getLinhaTempoDocProcesso());
		return linhaTempoDoc.gerarLinhaTempoDocumentos();
	}
	public String getLinhaTempoEmbarque() {
		linhaTempoEmb = new GerarLinhaTempoEmbarque(getLinhaTempoEmbProcesso());
		return linhaTempoEmb.gerarLinhaTempoEmbarque();
	}
	public List<String> getFasesLinhaTempoDoc(){
		
		List<String> list = new ArrayList<String>();
		
		for (TipoDocumento t : TipoDocumento.values()) {
			list.add(t.getLabel());
		}
		
		return list;
	}
	public void mudouTipoAvarias(){
		if(processo.getSnAvarias().equals("S")){
			setAvarias(true);
		}else{
			setAvarias(false);
		}
	}
	public void setLinhaTempoDocumento(String linhaTempo) {
		this.linhaTempoDocumento = linhaTempo;
	}
	public LinhaTempoDocProcesso getLinhaTempoDocAlterar() {
		return linhaTempoDocAlterar;
	}
	public void setLinhaTempoDocAlterar(LinhaTempoDocProcesso linhaTempoDocAlterar) {
		this.linhaTempoDocAlterar = linhaTempoDocAlterar;
	}
	public GerarLinhaTempoDocumento getLinhaTempoDoc() {
		return linhaTempoDoc;
	}
	public void setLinhaTempoDoc(GerarLinhaTempoDocumento linhaTempoDoc) {
		this.linhaTempoDoc = linhaTempoDoc;
	}
	public Exportadores getRepositorioExportadores() {
		return repositorioExportadores;
	}
	public void setRepositorioExportadores(Exportadores repositorioExportadores) {
		this.repositorioExportadores = repositorioExportadores;
	}
	public AlterarStatusLinhaTempoDoc getAlterarStatusDoc() {
		return alterarStatusDoc;
	}
	public void setAlterarStatusDoc(AlterarStatusLinhaTempoDoc alterarStatusDoc) {
		this.alterarStatusDoc = alterarStatusDoc;
	}
	public StatusFaseLinhaTempoDoc getStatusFaseLinhaTempo() {
		return statusFaseLinhaTempo;
	}
	public void setStatusFaseLinhaTempo(StatusFaseLinhaTempoDoc statusFaseLinhaTempo) {
		this.statusFaseLinhaTempo = statusFaseLinhaTempo;
	}
	public String getStatusFase() {
		return statusFase;
	}
	public void setStatusFase(String statusFase) {
		this.statusFase = statusFase;
	}
	public boolean isAlterando() {
		return isAlterando;
	}
	public void setAlterando(boolean isAlterando) {
		this.isAlterando = isAlterando;
	}
	public boolean isAvarias() {
		return isAvarias;
	}
	public void setAvarias(boolean isAvarias) {
		this.isAvarias = isAvarias;
	}
	public Cliente getClienteRapido() {
		return clienteRapido;
	}
	public void setClienteRapido(Cliente clienteRapido) {
		this.clienteRapido = clienteRapido;
	}
	public Exportador getExportadorRapido() {
		return exportadorRapido;
	}
	public void setExportadorRapido(Exportador exportadorRapido) {
		this.exportadorRapido = exportadorRapido;
	}
	
	
}