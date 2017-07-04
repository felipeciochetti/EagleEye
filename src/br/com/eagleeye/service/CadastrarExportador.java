package br.com.eagleeye.service;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.ClienteContato;
import br.com.eagleeye.jpa.Exportador;
import br.com.eagleeye.jpa.ExportadorContato;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Exportadores;
import br.com.eagleeye.util.Functions;

public class CadastrarExportador implements Serializable{


	Exportadores dao = new Exportadores();

	public CadastrarExportador() {
	}

	public String adicionar(Exportador cliente){

		String ret = validaDadosExportador(cliente);
		
		if(!ret.equals("")){
			return ret;
		}

		try {
			if(cliente.getIdExportador() <= 0 ){
				dao.adicionar(cliente);
			}else{
				dao.atualizar(cliente);
			}
			//FacesContext.getCurrentInstance().getExternalContext().redirect("ListaCliente");
			return "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		
		
		
	}

	public String validaDadosExportador(Exportador cliente){
		String msg = "";

		if(cliente.getCodigo().equals("")){
			cliente.setCodigo(Functions.ColocaZeroInicio(String.valueOf(dao.getMaxIdExportador()+1), 6));
		}else if(dao.isExiste("codigo", cliente.getCodigo(), cliente.getIdExportador())){
			msg += " Código do Exportador já Existe\n";
			return msg;
		}


		if(cliente.getNome().equals("")){
			msg += "Nome "; 
		}
		if(cliente.getCnpj().equals("")){
			msg += "Cnpj "; 
		}
		if(cliente.getExportadorContato() != null){
			for (ExportadorContato c : cliente.getExportadorContato()) {
				msg += validaPessoaContato(c);
			}
		}
		if(!msg.equals("")){
			msg = "Campos obrigatório: " + msg;
			return msg;
		}

		if(dao.isExiste("cnpj", cliente.getCnpj(), cliente.getIdExportador())){
			msg += " Cnpj do Exportador já Existe\n";
			return msg;
		}
		return msg;
	}
	private String validaPessoaContato(ExportadorContato c) {
		String msg = "";

		if(c.getNome().equals("")){
			msg += "nome do Contato ";
		}

		return msg;
	}



}
