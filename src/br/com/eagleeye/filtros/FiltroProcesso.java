package br.com.eagleeye.filtros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.eagleeye.complete.ClienteComplete;
import br.com.eagleeye.jpa.Cliente;
import br.com.eagleeye.repositorio.Clientes;

public class FiltroProcesso {
	
	
	
	boolean exibirProcessosCancelados;
	
	Cliente cliente;
	Timestamp dtIniAber;
	Timestamp dtfinAber;
	String check;
	
	Clientes repositorioCliente = new Clientes();
	
	public FiltroProcesso() {
	}

	
	
	public boolean isExibirProcessosCancelados() {
		return exibirProcessosCancelados;
	}
	public void setExibirProcessosCancelados(boolean exibirProcessosCancelados) {
		this.exibirProcessosCancelados = exibirProcessosCancelados;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Timestamp getDtIniAber() {
		return dtIniAber;
	}
	public void setDtIniAber(Timestamp dtIniAber) {
		this.dtIniAber = dtIniAber;
	}
	public Timestamp getDtfinAber() {
		return dtfinAber;
	}
	public void setDtfinAber(Timestamp dtfinAber) {
		this.dtfinAber = dtfinAber;
	}
	public List<Cliente> completeCliente(String query) {
		return new ClienteComplete(repositorioCliente).execute(query);
	}
	
	public String getCheck() {
		return check;
	}



	public void setCheck(String check) {
		this.check = check;
	}



	public Map getFiltro(){
		Map mapa = new HashMap();
		 StringBuffer where = new StringBuffer("");
		List<Serializable> param = new ArrayList<Serializable>();
		
		
		if(check != null && check.equals("true")){
			where.append(" where dtCancelamento is not null ");
		}else{
			where.append(" where dtCancelamento is null ");
		}

		if(cliente != null ){
			where.append(" and cliente.nome = ? ");
			param.add(cliente.getNome());
		}
		
		if(dtIniAber != null && dtfinAber != null){
			where.append(" and dtAbertura between ? and ? ");
			param.add(dtIniAber);
			param.add(dtfinAber);
		}
		
		mapa.put("where", where.toString());
		mapa.put("param", param);
		
		
		
		return mapa;
	}
	
	
	
	

}
