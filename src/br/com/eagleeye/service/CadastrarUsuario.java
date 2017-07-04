package br.com.eagleeye.service;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.mensagens.Mensagem;
import br.com.eagleeye.repositorio.Usuarios;
import br.com.eagleeye.util.Functions;

public class CadastrarUsuario implements Serializable{


	Usuarios dao = new Usuarios();

	public CadastrarUsuario() {
	}

	public boolean adicionar(Usuario usuario){

		if(!validaDadosUsuario(usuario)){
			return false;
		}

		if(!usuario.getNivel().equals("C")){
			usuario.setCliente(null);
		}

		if(!usuario.getNovaSenha().equals("")){
			usuario.setSenha(usuario.getNovaSenha());
		}

		try {
			if(usuario.getIdUsuario() <= 0 ){
				dao.adicionar(usuario);
				new Mensagem().mensagensGeralAviso("Usúario salvo com sucesso!");
			}else{
				dao.atualizar(usuario);
			}
			//FacesContext.getCurrentInstance().getExternalContext().redirect("ListaUsuario");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean validaDadosUsuario(Usuario usuario){
		String msg = "";

		if(usuario.getCodigo().equals("")){
			usuario.setCodigo(Functions.ColocaZeroInicio(String.valueOf(dao.getMaxIdUsuario()+1), 6));
		}else if(dao.isExiste("codigo", usuario.getCodigo(), usuario.getIdUsuario())){
			new Mensagem().mensagensGeralErro("Código do Usuario já Existe");
		}

		if (usuario.getNome().trim().length() == 0) {
			msg += " nome,";
		}
		if (usuario.getUsuario().trim().length() == 0) {
			msg += " usuário,";
		}else{
			if(dao.isExiste("usuario", usuario.getUsuario(), usuario.getIdUsuario())){
				new Mensagem().mensagensGeralErro("Usuário informado ja existe");
				return false;
			}
		}	
		if(!"".equals(usuario.getConfSenha())){
			if (usuario.getConfSenha().trim().length() == 0) {
				msg += " confirmação senha,";
			}
		}

		if (usuario.getNivel().equals("")) {
			msg += "tipo de usuário";
		}else if (usuario.getNivel().equals("C") && usuario.getCliente() == null){
			msg += "Vincule um Cliente,";
		}


		if(!msg.equals("")){
			new Mensagem().mensagensGeralErro("Campos obrigatório:",msg.substring(0,msg.length()-1));
			return false;
		}


		if (!usuario.getConfSenha().equals(usuario.getNovaSenha())) {
			new Mensagem().mensagensGeralErro("Nova Senha não confere com confirmação de senha.");
			return false;
		}


		return true;
	}



}
