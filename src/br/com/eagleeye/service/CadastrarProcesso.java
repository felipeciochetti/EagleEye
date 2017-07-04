package br.com.eagleeye.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;

import br.com.eagleeye.enums.FasesLinhaTempoDoc;
import br.com.eagleeye.enums.FasesLinhaTempoEmbarque;
import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;
import br.com.eagleeye.jpa.LinhaTempoDocProcesso;
import br.com.eagleeye.jpa.LinhaTempoEmbProcesso;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.util.Functions;

public class CadastrarProcesso implements Serializable{


	Processos dao = new Processos();

	public CadastrarProcesso() {
	}

	public boolean adicionar(Processo processo){

		boolean valida = validaDadosProcesso(processo);
		if(!valida){
			return false;
		}
		try {
			
			
			
			
			
			if(processo.getIdProcesso() <= 0 ){
				processo.setIdProcesso((int) (dao.getMaxIdProcesso()+1));
				processo.setReferenciaMvw(gerarReferenciaMVW(processo));
				
				dao.adicionar(processo);
				
				new Mensagem().mensagensGeralAviso("Processo salvo com sucesso! Referência Mvw: " + processo.getReferenciaMvw());
			}else{
				atualizaReferenciaMvw(processo);
				dao.atualizar(processo);
			}
			//FacesContext.getCurrentInstance().getExternalContext().redirect("ListaProcesso.xhtml");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public boolean validaDadosProcesso(Processo processo){
		String msg = "";

		if(processo.getCliente() == null){
			msg += "Cliente ";
		}
		if(processo.getViaTransporte() == null){
			msg += "Via Transporte ";
		}
		
		if(!msg.equals("")){
			new Mensagem().mensagensGeralErro("Campos obrigatório:",msg);
			return false;
		}
		
		return true;
	}
	
	public String gerarReferenciaMVW(Processo processo){
		
		long seque = processo.getSequencialProcesso();
		
		String seq = Functions.ColocaZeroInicio(seque+"", 4);
		
		String ano = Functions.dataParaTexto(new Timestamp(System.currentTimeMillis()), "yyyy");
		
		String viaTransporte = processo.getViaTransporte().getViaTransporte();
		viaTransporte = viaTransporte.substring(0,3);
		
		
		return seq+"/"+ano+viaTransporte.toUpperCase();
		
	}
	public void atualizaReferenciaMvw(Processo processo){
		
		String viaTransporte = processo.getViaTransporte().getViaTransporte();
		viaTransporte = viaTransporte.substring(0,3);
		if(!processo.getReferenciaMvw().endsWith(viaTransporte)){
			String ref = processo.getReferenciaMvw();
			ref = ref.substring(0,ref.length()-3);
			String via = (String) processo.getViaTransporte().getViaTransporte().subSequence(0, 3); 
			ref = ref + via.toUpperCase();
			processo.setReferenciaMvw(ref);
		}
		
	}
	public Set<LinhaTempoDocProcesso> gerarLinhaTempoDocumentoDefault(Processo processo){
		Set<LinhaTempoDocProcesso> listLinhaTempo  = new TreeSet<LinhaTempoDocProcesso>();
		
		for (FasesLinhaTempoDoc fase : FasesLinhaTempoDoc.values()) {
			
			LinhaTempoDocProcesso obj  = new LinhaTempoDocProcesso();
			obj.setProcesso(processo);
			obj.setFase(fase.getFase());
			obj.setDescricao(fase.getLabel());
			obj.setStatus(StatusFaseLinhaTempoDoc.AGUARDANDO.getValor());
			
			listLinhaTempo.add(obj);
			
		}
		
		return listLinhaTempo;
		
	}
	public Set<LinhaTempoEmbProcesso> gerarLinhaTempoEmbarqueDefault(Processo processo){
		Set<LinhaTempoEmbProcesso> listLinhaTempo  = new TreeSet<LinhaTempoEmbProcesso>();
		
		for (FasesLinhaTempoEmbarque fase : FasesLinhaTempoEmbarque.values()) {
			
			LinhaTempoEmbProcesso obj  = new LinhaTempoEmbProcesso();
			obj.setProcesso(processo);
			obj.setFase(fase.getFase());
			obj.setDescricao(fase.getLabel());
			obj.setStatus(StatusFaseLinhaTempoDoc.AGUARDANDO.getValor());
			
			listLinhaTempo.add(obj);
			
		}
		
		return listLinhaTempo;
		
	}

}
