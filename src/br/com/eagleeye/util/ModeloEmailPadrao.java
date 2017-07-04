package br.com.eagleeye.util;

import org.apache.log4j.Logger;

public class ModeloEmailPadrao {
		
	
	static Logger logger = Logger.getLogger(ModeloEmailPadrao.class);
	
	public ModeloEmailPadrao() {
		// TODO Auto-generated constructor stub
	}
	
	static String getLogotipoMvw(){
		String url = ContextUtils.getInstance().getPathLogoTipos()+"logo-mvwIndex.png";
		
		if(!Functions.existeArquivo(url)){
			logger.error("logotipo Mvw não localizado");
		}
		return url;
	}
	static String getLogotipoEagle(){
		String url = ContextUtils.getInstance().getPathLogoTipos()+"logo.png";
		
		if(!Functions.existeArquivo(url)){
			logger.error("logotipo Eagle não localizado");
		}
		return url;
	}
	static public String getPadraoEmailEagle(String msgCorpo){
		/*
		 * ;background-color:#f5f0e5
		 */
		
		StringBuffer html = new StringBuffer("<table style=\"width:100%\" align=\"center\">");
		html.append("<tr>");
		html.append("<td>");
		html.append("<img src=\"cid:image0\" align=\"left\"></html>");
		html.append("</td>");
		html.append("<td>");
		html.append("<img src=\"cid:image1\"align=\"right\"></html>");
		html.append("</td>");
		html.append("</tr>");

		html.append("<tr style=\"background-color:#fff\">");
		html.append("<td>");
		html.append("</td>");
		html.append("<td>");
		html.append("</td>");
		html.append("</tr>");
		
		html.append("<tr>");
		html.append("<td collspan=\"2\" style=\"height:300px;vertical-align:top;\">");
		html.append(msgCorpo);
		html.append("</td>");
		html.append("</tr>");
		html.append("</table>");
		
		return html.toString();
	}
	

}
