package br.com.eagleeye.managedBean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.eagleeye.enums.TipoDocumento;
import br.com.eagleeye.jpa.DocumentoProcesso;
import br.com.eagleeye.jpa.LinhaTempoDocProcesso;
import br.com.eagleeye.jpa.LinhaTempoEmbProcesso;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.service.CadastrarProcesso;
import br.com.eagleeye.service.GerarLinhaTempoDocumento;
import br.com.eagleeye.service.GerarLinhaTempoEmbarque;
import br.com.eagleeye.util.ContextUtils;
import br.com.eagleeye.util.FormatUtils;
import br.com.eagleeye.util.Functions;
import br.com.eagleeye.util.UploadFileUtils;
import br.com.eagleeye.util.ZiperUtils;


@ManagedBean
@ViewScoped
public class ProcessoViewBean implements Serializable{

	private static final long serialVersionUID = 1L;
	Processos repositorio = new Processos();
	Processo processo = new Processo();
	CadastrarProcesso cadastar = new CadastrarProcesso() ;

	private String linkArquivoAnexo;
	List<DocumentoProcesso> documentoProcesso;
	List<LinhaTempoDocProcesso> linhaTempoDocProcesso;
	List<LinhaTempoEmbProcesso> linhaTempoEmbProcesso;
	
	String linhaTempoDocumento;
	String linhaTempoEmbarque;

	private GerarLinhaTempoDocumento linhaTempoDoc;
	private GerarLinhaTempoEmbarque linhaTempoEmb;
	public ProcessoViewBean() {
	}
	@PostConstruct
	public void init(){

		getProcessoView();

	}

	public void getProcessoView(){
		// alterar	
		String id =  ContextUtils.getParemeterMap("id"); 
		if(id != null){

			Usuario usuario = ContextUtils.getInstance().getUsuarioLogado();
			if(usuario.getNivel().equals("C")){
				processo = repositorio.getProcessoPorCliente(Integer.valueOf(id),usuario.getCliente().getIdCliente());
			}else{
				processo = repositorio.get(Integer.valueOf(id));
			}


		}

	}
	public void baixarDocumentos(){
		List<String> listFiles  = new ArrayList<String>();
		for (DocumentoProcesso doc : processo.getDocumentoProcesso()){
			listFiles.add(doc.getUrl());
		} 
		
		if(listFiles.size() > 0){
			Functions.limparPasta(ContextUtils.getInstance().getPathTemporarios());
			String zipFileName = FormatUtils.getInstance().formatarDataNomeArquivo(new Date()) + "eagle_eye.zip";
			String urlZip = ContextUtils.getInstance().getPathTemporarios() +zipFileName;
			new ZiperUtils().compactar(urlZip , listFiles);
			setLinkArquivoAnexo("/exibeDocumento?nome=" + zipFileName + "&tipo=1");
		}
		
	}

	public void handleFileUpload(FileUploadEvent event) {
		new UploadFileUtils().handleFileUpload(event,String.valueOf(processo.getSequencialProcesso()));
		String fileName = event.getFile().getFileName();
	}
	public CadastrarProcesso getCadastar() {
		return cadastar;
	}

	public void setCadastar(CadastrarProcesso cadastar) {
		this.cadastar = cadastar;
	}

	public void excluirArquivo(DocumentoProcesso doc) {
		File f = null;
		try {
			String nome = doc.getNome();
			String pathFileName = ContextUtils.getInstance().getPathFilesAttacheds() + processo.getSequencialProcesso() + File.separator + nome; 
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
	public List<DocumentoProcesso> getDocumentoProcesso() {
		documentoProcesso = processo.getDocumentoProcesso()== null ? new ArrayList<DocumentoProcesso>() : new ArrayList<DocumentoProcesso>(this.processo.getDocumentoProcesso());
		return documentoProcesso;
	}
	public void setDocumentoProcesso(List<DocumentoProcesso> documentoProcesso) {
		this.documentoProcesso = documentoProcesso;
	}


	public String formataData(Date data) {
		return FormatUtils.getInstance().formatarData(data);
	}
	public String getLinhaTempoDocumento() {
		linhaTempoDoc = new GerarLinhaTempoDocumento(getLinhaTempoDocProcesso());
		return linhaTempoDoc.gerarLinhaTempoDocumentos();
	}
	
	public String getLinhaTempoEmbarque() {
		linhaTempoEmb = new GerarLinhaTempoEmbarque(getLinhaTempoDocEmbarque());
		return linhaTempoEmb.gerarLinhaTempoEmbarque();
	}
	public void setLinhaTempoEmbarque(String linhaTempoEmbarque) {
		this.linhaTempoEmbarque = linhaTempoEmbarque;
	}
	public List<String> getFasesLinhaTempoDoc(){

		List<String> list = new ArrayList<String>();

		for (TipoDocumento t : TipoDocumento.values()) {
			list.add(t.getLabel());
		}

		return list;
	}

	public java.lang.String getTipoDocumentoFormatado(String tipo) {

		for (TipoDocumento t: TipoDocumento.values()) {
			if(tipo.equals(t.getValor())){
				return t.getLabel();
			}
		}
		return "";
	}
	public void setLinhaTempoDocumento(String conteudo) {
		this.linhaTempoDocumento = conteudo;
	}
	public List<LinhaTempoDocProcesso> getLinhaTempoDocProcesso() {
		linhaTempoDocProcesso = processo.getLinhaTempoProcesso()== null ? new ArrayList<LinhaTempoDocProcesso>() : new ArrayList<LinhaTempoDocProcesso>(this.processo.getLinhaTempoProcesso());
		 Collections.sort(linhaTempoDocProcesso);
		 
		 return linhaTempoDocProcesso;
	}
	
	public void setLinhaTempoDocProcesso(List<LinhaTempoDocProcesso> linhaTempoDocProcesso) {
		this.linhaTempoDocProcesso = linhaTempoDocProcesso;
	}
	public List<LinhaTempoEmbProcesso> getLinhaTempoDocEmbarque() {
		linhaTempoEmbProcesso = processo.getLinhaTempoEmbProcesso()== null ? new ArrayList<LinhaTempoEmbProcesso>() : new ArrayList<LinhaTempoEmbProcesso>(this.processo.getLinhaTempoEmbProcesso());
		 Collections.sort(linhaTempoEmbProcesso);
		 
		 return linhaTempoEmbProcesso;
	}
	public void baixarArquivo(String nome) {
		setLinkArquivoAnexo("exibeDocumento?nome=" + processo.getSequencialProcesso() + File.separator + nome + "&tipo=3");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(linkArquivoAnexo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}