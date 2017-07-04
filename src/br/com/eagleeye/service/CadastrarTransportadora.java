package br.com.eagleeye.service;

import java.io.Serializable;

import br.com.eagleeye.jpa.TransportadoraContato;
import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.jpa.TransportadoraContato;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Transportadoras;
import br.com.eagleeye.util.Functions;

public class CadastrarTransportadora implements Serializable{


	Transportadoras dao = new Transportadoras();

	public CadastrarTransportadora() {
	}

	public String adicionar(Transportadora transportadora){

		
	String ret = validaDadosTransportadora(transportadora);
		
		if(!ret.equals("")){
			return ret;
		}

		try {
			if(transportadora.getIdTransportadora() <= 0 ){
				dao.adicionar(transportadora);
			}else{
				dao.atualizar(transportadora);
			}
			//FacesContext.getCurrentInstance().getExternalContext().redirect("ListaTransportadora");
			return "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String validaDadosTransportadora(Transportadora transportadora){
		String msg = "";

		if(transportadora.getCodigo().equals("")){
			transportadora.setCodigo(Functions.ColocaZeroInicio(String.valueOf(dao.getMaxIdTransportadora()+1), 6));
		}else if(dao.isExiste("codigo", transportadora.getCodigo(), transportadora.getIdTransportadora())){
			msg += " Código do Transportadora já Existe\n";
			return msg;
		}


		if(transportadora.getNome().equals("")){
			msg += "Nome "; 
		}
		if(transportadora.getCnpj().equals("")){
			msg += "Cnpj "; 
		}
		if(transportadora.getTransportadoraContato() != null){
			for (TransportadoraContato c : transportadora.getTransportadoraContato()) {
				msg += validaPessoaContato(c);
			}
		}
		if(!msg.equals("")){
			msg = "Campos obrigatório: " + msg;
			return msg;
		}

		if(dao.isExiste("cnpj", transportadora.getCnpj(), transportadora.getIdTransportadora())){
			msg += " Cnpj do Transportadora já Existe\n";
			return msg;
		}
		return msg;
	}
	private String validaPessoaContato(TransportadoraContato c) {
		String msg = "";

		if(c.getNome().equals("")){
			msg += "nome do Contato ";
		}

		return msg;
	}



}
