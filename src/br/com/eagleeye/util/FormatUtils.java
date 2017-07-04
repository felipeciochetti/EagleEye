package br.com.eagleeye.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatUtils {
	private static FormatUtils instance = new FormatUtils();
	
	private SimpleDateFormat sdf;
	private SimpleDateFormat sdtf;
	private SimpleDateFormat sdfXML;
	private SimpleDateFormat sdfNomeArquivo;
	private SimpleDateFormat sdfLeituraLote;
	private DecimalFormat df;
	
	private FormatUtils(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdtf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		sdfXML = new SimpleDateFormat("yyyy-MM-dd");
		sdfNomeArquivo = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
		sdfLeituraLote = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	}
	
	public static FormatUtils getInstance(){
		return instance;
	}
	
	public String formatarData(Calendar data){
		if ( data == null ){
			return "";
		}
		return formatarData(data.getTime(), sdf);
	}
	
	public String formatarData(Date data){
		return formatarData(data, sdf);
	}
	
	public String formatarDataNomeArquivo(Date data){
		return formatarData(data, sdfNomeArquivo);
	}
	
	public String formatarDataXML(Date data){
		return formatarData(data, sdfXML);
	}
	
	public String formatarDataHora(Date data){
		return formatarData(data, sdtf);
	}
	public String formatarNivelUsuario(String nivel){
		if(nivel.equals("A")){
			return "Administrador";
		}else if(nivel.equals("U")){
			return "Usuário";
		}else if(nivel.equals("C")){
			return "Cliente";
		}	
		return "";
	}
	public Date parseLeituraLote(String data){
		try{
			return sdfLeituraLote.parse(data);
		}catch(Exception e){
			return null;
		}
	}
	
	public String formatarData(Date data, SimpleDateFormat pSdf){
		if ( data != null ){
			try{
				return pSdf.format(data.getTime());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	public String zerosEsquerda(String texto, int qtd){
		if ( texto.length() >= qtd ){
			return texto.substring(0, qtd);
		}
		StringBuilder ret = new StringBuilder(texto);
		while(ret.length() < qtd){
			ret.insert(0,"0");
		}
		return ret.toString();
	}
	
	public String formatarNumero(BigDecimal valor, int qtdCasas){
		String retorno = "";
		try{
			df = new DecimalFormat();
			df.setMaximumFractionDigits(qtdCasas);
			df.setMinimumFractionDigits(qtdCasas);
			return df.format(valor.doubleValue());
		}catch (Exception e) {
		}
		
		return retorno;
	}
	
}
