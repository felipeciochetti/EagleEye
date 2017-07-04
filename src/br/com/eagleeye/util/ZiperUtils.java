package br.com.eagleeye.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZiperUtils {

	final int TAMANHO_BUFFER = 2048;

	public void compactar (String arqSaida, List<String> arquivos) {
		int cont;

		byte[] dados = new byte[TAMANHO_BUFFER];

		BufferedInputStream origem = null;
		FileInputStream streamDeEntrada = null;
		FileOutputStream destino = null;

		ZipOutputStream saida = null;

		ZipEntry entry = null;

		try {
			destino = new FileOutputStream(arqSaida);
			saida = new ZipOutputStream(new BufferedOutputStream(destino));

			for (int i = 0; i < arquivos.size(); i++) {
	            File arquivo = new File(arquivos.get(i));

	            if (arquivo.isFile() && !(arquivo.getName()).equals(arqSaida)) {
//	               logger.info("Compactando: " + arquivos.get(i));

	               streamDeEntrada = new FileInputStream(arquivo);
	               origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
	               entry = new ZipEntry(arquivos.get(i).substring(arquivos.get(i).lastIndexOf(File.separator)+1) );
	               saida.putNextEntry(entry);

	               while((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
	                  saida.write(dados, 0, cont);
	               }

	               origem.close();
	            }
	         }

			saida.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
