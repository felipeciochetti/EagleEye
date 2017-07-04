package br.com.eagleeye.service;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.jpa.ClienteContato;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Clientes;
import br.com.eagleeye.util.Functions;

public class CadastrarCliente implements Serializable{


	Clientes dao = new Clientes();

	public CadastrarCliente() {
	}

	public String adicionar(Cliente cliente){

		
		String ret = validaDadosCliente(cliente);
		
		if(!ret.equals("")){
			return ret;
		}

		try {
			if(cliente.getIdCliente() <= 0 ){
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

	public String validaDadosCliente(Cliente cliente){
		String msg = "";

		if(cliente.getCodigo().equals("")){
			cliente.setCodigo(Functions.ColocaZeroInicio(String.valueOf(dao.getMaxIdCliente()+1), 6));
		}else if(dao.isExiste("codigo", cliente.getCodigo(), cliente.getIdCliente())){
			msg += " Código do Cliente já Existe\n";
			return msg;
		}


		if(cliente.getNome().equals("")){
			msg += "Nome "; 
		}
		if(cliente.getCnpj().equals("")){
			msg += "Cnpj "; 
		}
		if(cliente.getClienteContato() != null){
			for (ClienteContato c : cliente.getClienteContato()) {
				msg += validaPessoaContato(c);
			}
		}
		if(!msg.equals("")){
			msg = "Campos obrigatório: " + msg;
			return msg;
		}

		if(dao.isExiste("cnpj", cliente.getCnpj(), cliente.getIdCliente())){
			msg += " Cnpj do Cliente já Existe\n";
			return msg;
		}
		return msg;
	}
	private String validaPessoaContato(ClienteContato c) {
		String msg = "";

		if(c.getNome().equals("")){
			msg += "nome do Contato ";
		}

		return msg;
	}



}
