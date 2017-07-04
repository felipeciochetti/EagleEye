package br.com.eagleeye.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import br.com.eagleeye.jpa.ClienteContato;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.repositorio.Processos;
import br.com.eagleeye.repositorio.TarefasAgendadas;
import br.com.eagleeye.util.Functions;
import br.com.eagleeye.util.SendMail;

public class NotificacoesEmailAberturaProcesso {

	Logger logger = Logger.getLogger(NotificacoesEmailAberturaProcesso.class);

	TarefasAgendadas repositorio;
	SendMail email = new SendMail();

	public NotificacoesEmailAberturaProcesso(EntityManager entityManager) {
		 	repositorio = new TarefasAgendadas(entityManager);
			logger.info("Inicio notificação abertura de processos");
	}


	public void  notificar(){

		List<Processo> listProcessos = getProcessosNotificacaoAbertura();

		for (Processo proc : listProcessos) {

			Set<ClienteContato> listContatos = proc.getCliente().getClienteContato();

			for (ClienteContato contato : listContatos) {

				if("S".equals(contato.getEmailStatus())){
					envioEmail(contato.getEmail(), proc);
				}
			}

			proc.setSnNotificarAbertura("N");
			repositorio.atualizarProcesso(proc);
		}

		logger.info("fim notificação abertura de processos");
	}

	public List<Processo> getProcessosNotificacaoAbertura(){
		return repositorio.getProcessosPorCampo("snNotificarAbertura", "S");
	}
	private void envioEmail(String destinatario , Processo processo){

		try {
			String msg = getMsgAberturaProcesso(processo);
			String assunto = "Eagle Eye - Abertura de Processo";  

			this.email.enviarEmailSimples(msg, assunto,destinatario,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getMsgAberturaProcesso(Processo processo){


		String msg = "<span style=\"font-weight:bold;font-size: 20pt;\">Abertura de Processo</span>\n"+	
				"<span style=\"font-weight:bold;font-size: 13pt;\">Processo: " +
				"</span style=\"font-weight:bold;font-size: 13pt;\">"+processo.getReferenciaMvw()+"\n"+
				"<span style=\"font-weight:bold;font-size: 13pt;\">Cliente: " +
				"</span style=\"font-weight:bold;font-size: 13pt;\">"+processo.getCliente().getNome()+"\n"+
				"<span style=\"font-weight:bold;font-size: 13pt;\">Data de Abertura: " +
				"</span style=\"font-weight:bold;font-size: 13pt;\">"+Functions.dataParaTexto(processo.getDtAbertura(), "dd/MM/yyyy")+"\n";
		
		
		return msg;
	}

}
