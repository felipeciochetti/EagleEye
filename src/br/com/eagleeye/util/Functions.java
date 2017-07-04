package br.com.eagleeye.util;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import br.com.eagleeye.enums.StatusFaseLinhaTempoDoc;


public abstract class Functions {

	static Logger logger = Logger.getLogger(Functions.class);

	public static String ColocaZeroInicio(String Texto, int Tamanho) {
		return ColocaZero(Texto, Tamanho, "Inicio");
	}

	public static String ColocaZeroFim(String Texto, int Tamanho) {
		return ColocaZero(Texto, Tamanho, "Fim");
	}
	
	static public  StatusFaseLinhaTempoDoc getValorLinhaTempo(String valor){
		if(valor.equals(StatusFaseLinhaTempoDoc.AGUARDANDO.getValor())){
			return StatusFaseLinhaTempoDoc.AGUARDANDO;
		}else if(valor.equals(StatusFaseLinhaTempoDoc.PROCESSANDO.getValor())){
			return  StatusFaseLinhaTempoDoc.PROCESSANDO;
		}else if(valor.equals(StatusFaseLinhaTempoDoc.COMPLETADA.getValor())){
			return StatusFaseLinhaTempoDoc.COMPLETADA;
		}else if(valor.equals(StatusFaseLinhaTempoDoc.CANCELADA.getValor())){
			return StatusFaseLinhaTempoDoc.CANCELADA;
		}
		return null;
	}

	protected static String ColocaZero(String Texto, int Tamanho, String Posicao) {
		String TextoOriginal = Texto;
		if (Texto.length() > Tamanho) {
			return Texto.substring(0, Tamanho);
		}
		for (int x = 0; x < Tamanho - TextoOriginal.length(); x++) {
			if (Posicao.equals("Inicio")) {
				Texto = "0" + Texto;
			} else {
				Texto = Texto + "0";
			}

		}
		return Texto;
	}

	static public String dataParaTexto(Date Data) {
		return dataParaTexto(Data, "dd/MM/yyyy");
	}

	static public String dataParaTexto(Date Data, String Mascara ) {
		return dataParaTexto(Data, Mascara, false);
	}
	static public String dataParaTexto(Date Data, String Mascara , boolean retornaVazio) {
		if (Data == null || (Data.equals(new Timestamp(0)) && retornaVazio)) {
			return "";
		} else {
			return new SimpleDateFormat(Mascara).format(Data);
		}
	}

	static public void criarDiretorio(String diretorio){
		File f = new File(diretorio);
		if(!f.isDirectory()){
			if(f.mkdirs()){
				logger.info("Pasta  criada :" + diretorio);
			}else{
				logger.warn("Pasta  não foi criada :" + diretorio);
			}
		}
	}
	static public boolean existePasta(String pasta){
		File f = new File(pasta);
		return f.isDirectory();

	}

	static public boolean existeArquivo(String arquivo){
		File f = new File(arquivo);
		return f.isFile();

	}

	static public void limparPasta(String pasta){


		File f = new File(pasta);

		if(f.isDirectory()){
			File[] list = f.listFiles();
			for (File x : list) {
				x.delete();
			}
		}

	}
	

}
