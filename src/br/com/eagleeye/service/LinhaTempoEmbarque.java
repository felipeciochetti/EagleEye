package br.com.eagleeye.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;
import br.com.eagleeye.util.Functions;

public class LinhaTempoEmbarque implements Serializable {

	StringBuffer sb = new StringBuffer("");
	String param = "";
	static final String ICONE = "certified";

	public LinhaTempoEmbarque() {
	}

	private String gerarPrimeiraFase(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<tr>");
		sb.append("<td id=\"tdLineFirst\" class=\"stgTdLinePrev\" style=\"width: 4.0%;\"></td>");

		return sb.toString();
	}

	private String gerarUltimaFase(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td id=\"tdLineLast\" style=\"width: 4.0%;\"></td></tr><tr>");

		return sb.toString();
	}

	public String gerarPrimeriaFase(StatusFaseLinhaTempoDoc status){
		param = " firstStage ";
		if(status.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
			return gerarPrimeiraFase() + gerarFaseCompletada();
		}else if(status.equals(StatusFaseLinhaTempoDoc.PROCESSANDO)){
			return gerarPrimeiraFase() + gerarFaseProcessando();
		}else if(status.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
			return gerarPrimeiraFase() + gerarFaseAguardando();
		}else if(status.equals(StatusFaseLinhaTempoDoc.CANCELADA)){
			return gerarPrimeiraFase() + gerarFaseCancelada();
		}
		return "";
	}
	public String gerarUltimaFase(StatusFaseLinhaTempoDoc status){
		param = " lastStage ";
		if(status.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
			return gerarFaseCompletada() + gerarUltimaFase();
		}else if(status.equals(StatusFaseLinhaTempoDoc.PROCESSANDO)){
			return gerarFaseProcessando() + gerarUltimaFase();
		}else if(status.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
			return gerarFaseAguardando() + gerarUltimaFase();
		}
		return "";
	}

	private String gerarFaseCompletada(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td class=\"stgTdIcon\">");

		sb.append("<div class=\"bgLine"+param+"bglineColorCompleted\"></div>");

		sb.append("<div id=\"stgIcon1\" class=\"stgIconEmb "+ ICONE +" completed\">");

		sb.append("</div>");

		sb.append("</td>");

		param = "";

		return sb.toString();
	}

	public String gerarFase(StatusFaseLinhaTempoDoc status){
		if(status.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
			return gerarFaseCompletada() ;
		}else if(status.equals(StatusFaseLinhaTempoDoc.PROCESSANDO)){
			return gerarFaseProcessando() ;
		}else if(status.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
			return gerarFaseAguardando() ;
		}else if(status.equals(StatusFaseLinhaTempoDoc.CANCELADA)){
			return gerarFaseCancelada();
		}
		return "";

	}
	private String gerarFaseProcessando(){

		StringBuffer sb = new StringBuffer("");

		sb.append("<td class=\"stgTdIcon\">");

		sb.append("<div class=\"bgLine"+param+"bglineColorProcessing\"></div>");

		sb.append("<div id=\"stgIcon1\" class=\"stgIconEmb "+ ICONE +" processing\">");

		sb.append("</div>");

		sb.append("</td>");

		param = "";

		return sb.toString();

	}


	private String gerarFaseAguardando(){

		StringBuffer sb = new StringBuffer("");

		sb.append("<td class=\"stgTdIcon\">");

		sb.append("<div class=\"bgLine"+param+"bglineColorWaiting\"></div>");

		sb.append("<div id=\"stgIcon1\" class=\"stgIconEmb "+ ICONE +" waiting\">");

		sb.append("</div>");

		sb.append("</td>");

		param = "";

		return sb.toString();

	}
	private String gerarFaseCancelada(){

		StringBuffer sb = new StringBuffer("");

		sb.append("<td class=\"stgTdIcon\">");

		sb.append("<div class=\"bgLine"+param+"bglineColorCanceled\"></div>");

		sb.append("<div id=\"stgIcon1\" class=\"stgIconEmb "+ ICONE +" canceled\">");

		sb.append("</div>");

		sb.append("</td>");

		param = "";

		return sb.toString();

	}

	public String gerarFaseEntreFase(StatusFaseLinhaTempoDoc faseAnterior ,StatusFaseLinhaTempoDoc faseProxima ){
		StringBuffer sb = new StringBuffer("");
		if(faseAnterior.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
			
			return gerarEntreAguardandoAguardando();
			
		}else if(faseAnterior.equals(StatusFaseLinhaTempoDoc.PROCESSANDO)){
			
				return gerarEntreProcessandoAguardando();
				
		}else if(faseAnterior.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
			
			 if(faseProxima.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
				return gerarEntreCompletadaCompletada();
			}else if(faseProxima.equals(StatusFaseLinhaTempoDoc.PROCESSANDO)){ 
				return gerarEntreCompletadaProcessando();
			}else if(faseProxima.equals(StatusFaseLinhaTempoDoc.CANCELADA)){
				return gerarEntreCompletadaCancelada();
			}else{
				return gerarEntreCompletadaAguardando();
			}

		}else if(faseAnterior.equals(StatusFaseLinhaTempoDoc.CANCELADA)){
			
			return gerarEntreAguardandoAguardando();
		}
		return "";
	}

	private String gerarEntreCompletadaProcessando(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td colspan=\"2\" class=\"stgTdLineBetween\" >");

		sb.append("<div class=\"bgLine bgLineColorCompleted2Processing\">");

		sb.append("</div>");

		sb.append("</td>");


		return sb.toString();
	}
	private String gerarEntreCompletadaAguardando(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td colspan=\"2\" class=\"stgTdLineBetween\" >");

		sb.append("<div class=\"bgLine bgLineColorCompleted2Waiting\">");

		sb.append("</div>");

		sb.append("</td>");


		return sb.toString();
	}

	private String gerarEntreCompletadaCancelada(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td colspan=\"2\" class=\"stgTdLineBetween\" >");

		sb.append("<div class=\"bgLine bgLineColorCompleted2Canceled\">");

		sb.append("</div>");

		sb.append("</td>");


		return sb.toString();
	}
	
	private String gerarEntreProcessandoAguardando(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td colspan=\"2\" class=\"stgTdLineBetween\" >");

		sb.append("<div class=\"bgLine bgLineColorProcessing2Waiting\">");

		sb.append("</div>");

		sb.append("</td>");


		return sb.toString();
	}


	private String gerarEntreAguardandoAguardando(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td colspan=\"2\" class=\"stgTdLineBetween\" >");

		sb.append("<div class=\"bgLine bglineColorWaiting\">");

		sb.append("</div>");

		sb.append("</td>");


		return sb.toString();
	}


	private String gerarEntreCompletadaCompletada(){
		StringBuffer sb = new StringBuffer("");

		sb.append("<td colspan=\"2\" class=\"stgTdLineBetween\" >");

		sb.append("<div class=\"bgLine bglineColorCompleted\">");

		sb.append("</div>");

		sb.append("</td>");


		return sb.toString();
	}


	public String gerarInicioCabecalho(int qtd){
		StringBuffer sb = new StringBuffer("");

		sb.append("<table id=\"timeLineTableEmb\" class=\"tlTable tl\" border=\"0\">"+
				"<tbody>");
				
		sb.append("<tr>");



		return sb.toString(); 
	}
	public String gerarFimCabecalho(){
		StringBuffer sb = new StringBuffer("");

		sb.append("</tr>");

		return sb.toString(); 

	}
	public String gerarCabecalho(String nome){
		return "<td id=\"stgTitle1\" colspan=\"3\" class=\"stgTitle\">"+nome.toUpperCase()+"</td>"; 
	}
	public String getFimLinhaTempo(){
		return "</tr></tbody></table>";
	}
	public String gerarRodapeFase(int fase , Date data , StatusFaseLinhaTempoDoc status){
		StringBuffer sb = new StringBuffer("");
		String labelData = "";
		String labelHora = "";
		String strData = Functions.dataParaTexto(data, "dd/MM/yyyy");
		String strHora = Functions.dataParaTexto(data, "hh:mm");
		String labelStatus  = getValorStatusRodape(status);
		
		if(!strData.equals("")){
			labelData = "Data: ";
		}
		if(!strHora.equals("")){
			labelHora = "Hora: ";
		}
		
		if(status.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
			strData = "" ; strHora = "" ; labelData = ""; labelHora = "";
		}

		sb.append("<td id=\"tdFooterStage"+fase+"\" colspan=\"3\" class=\"stgFooter completed\"><div id=\"stage1_date_div\" class=\"date\" style=\"visibility: visible;\">");

		sb.append("<div id=\"stage1_date_div\" class=\"date\" style=\"visibility: visible;\"><strong>"+labelData+"<span id=\"stage"+fase+"_date\">"+strData+"</span></strong></div>");

		sb.append("<div class=\"date\" style=\"visibility: visible;\">"+labelHora+"<span id=\"stage"+fase+"_hour\">"+strHora+"</span></div>");

		sb.append("<span id=\"stage"+fase+"_ico\" class=\"footerIco "+labelStatus+"\"></span></td>");





		return sb.toString();
	}
	private String getValorStatusRodape(StatusFaseLinhaTempoDoc status){
		if(status.equals(StatusFaseLinhaTempoDoc.AGUARDANDO)){
			return "waiting";
		}else if(status.equals(StatusFaseLinhaTempoDoc.PROCESSANDO)){
				return "processing";
		}else if(status.equals(StatusFaseLinhaTempoDoc.COMPLETADA)){
			return "completed";
		}else if(status.equals(StatusFaseLinhaTempoDoc.CANCELADA)){
			return "canceled";
		}
		return "";
		
		
	}
	public static void main(String[] args) {

		StatusFaseLinhaTempoDoc anterio = StatusFaseLinhaTempoDoc.AGUARDANDO;

		System.out.println(anterio.equals(StatusFaseLinhaTempoDoc.AGUARDANDO));

	}

}
