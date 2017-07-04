package br.com.eagleeye.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

public class SendMail {

	Logger logger = Logger.getLogger(SendMail.class);
	Properties props = new Properties();
	static String NOME_FILE = "email.properties"; 
	
	public SendMail() {
		// TODO Auto-generated constructor stub
	}
	
	
	private Session criarSessionMail() throws Exception { 
		
		
//		File f = new File(ContextUtils.getInstance().getPathArquivosProperties() + NOME_FILE);
//		if(!f.isFile()){
//			logger.error("Arquivo de Propriedade Email não localizado " + f.getAbsolutePath() );
//			throw new Exception("Arquivo de Propriedade Email não localizado");
//		}
//		
//		props.load( new FileInputStream( f ));
//		final String  usuario = props.getProperty("mail.usuario");
//		final String  senha   = props.getProperty("mail.senha");
		
		
		
		
//		props.put("mail.smtp.host", "200.147.36.31");
//		props.put("mail.smtp.socketFactory.port", 587);
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
//		props.put("mail.smtp.auth", true); 
//		props.put("mail.smtp.port", 587); 
		
		
		props.put("mail.smtp.host", "mail.ativy.com");
		props.put("mail.smtp.auth", true); 
		props.put("mail.smtp.port", 587); 
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
//		props.put("mail.debug", true);
//		props.put("mail.debug.auth", true);
		
		
		
		
		final String  usuario = "marketingcoleta@bysoft.com.br";
		final String  senha   = "By@2016";
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usuario,senha); } });
		session.setDebug(true);
		return session; 
	}
	public static void main(String[] args) {
		
		SendMail envia = new SendMail();
		try {
			envia.enviarEmailSimples("xxxxxxx","XXXXXXXX", "felipeciochetti@gmail.com", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public  void enviarEmailSimples(String msg , String assunto , String destinatario,boolean isPadraoEgle) throws Exception{ 
	
		
		enviarMail( assunto,destinatario, msg, new ArrayList<String>(),isPadraoEgle);
		
		
		

	}
	public  void enviarMail(String subject,String mailto,String TextoHTML,List<String> filesImagesHtml , boolean isPadraoEgle)
	{
		Session mailSession = null;
		String mailfrom = "";
		String corpoEmail = "";
		List<String> filesImagesCabecalho = new ArrayList<String>();
		try {
			mailSession = criarSessionMail();
			mailfrom = props.getProperty("mail.remetente"); 
			if(mailfrom == null){
				mailfrom  = "marketingcoleta@bysoft.com.br";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String strResult = "";
		//System.out.println("\tMailing Process Started ............\t");
		if(filesImagesHtml == null){
			filesImagesHtml = new ArrayList<String>();
		}
		
		try
		{

			mailto = mailto.replace(';',',');
			//mailto.replaceAll(";",",");
			//ccmailid.replaceAll(";",",");
			try	
			{

				MimeMessage mimemessage = new MimeMessage(mailSession);
				// set FROM
				mimemessage.setFrom(new InternetAddress(mailfrom));
				// set DATE
				mimemessage.setSentDate(new java.util.Date());
				// set SUBJECT
				mimemessage.setSubject(subject);

				// set TO address
				try
				{
					mimemessage.setRecipients(javax.mail.Message.RecipientType.TO, mailto); 
				}
				catch(Exception exception1)
				{
					//System.out.println("\tError in setting recipients ......\t" + exception1.getMessage());
					strResult = "E-Mail para é inválido, "+ exception1.getMessage();
				}

				// attach message BODY
				MimeMultipart mimemultipart = new MimeMultipart();
				MimeBodyPart mimebodypart;

				
				// set padrao Eagle
				if(isPadraoEgle){
					 corpoEmail = ModeloEmailPadrao.getPadraoEmailEagle(TextoHTML);
					 filesImagesHtml.add(ModeloEmailPadrao.getLogotipoMvw());
					 filesImagesHtml.add(ModeloEmailPadrao.getLogotipoEagle());
					
				}else{
					corpoEmail = TextoHTML;
				}
				
				if ( !corpoEmail.equals("")){
					corpoEmail = corpoEmail.replace("\n", "<br>");
					corpoEmail += "<br><br>";
					mimebodypart = new MimeBodyPart();
					mimebodypart.setDataHandler(new DataHandler(new ByteArrayDataSource(corpoEmail , "text/html")));
					mimemultipart.addBodyPart(mimebodypart);
				}


				for(int i = 0; i < filesImagesHtml.size(); i++){
					// attach FILE
					mimebodypart = new MimeBodyPart();
					try
					{
						if(filesImagesHtml.get(i).equals("")){
							continue;
						}
						FileDataSource filedatasource = new FileDataSource(filesImagesHtml.get(i));
						mimebodypart.setDataHandler(new DataHandler(filedatasource));
						mimebodypart.setHeader("Content-ID","<image"+i+">");

					}
					catch(Exception exception3)
					{
						//System.out.println("\tError in sending file not been able to attach ......\t" + exception3.getMessage());
						strResult = "Erro ao atachar arquivos de imagem do html => "+ exception3.getMessage();
					}
					//mimebodypart.setFileName(filesImagesHtml[i]); 
					mimemultipart.addBodyPart(mimebodypart);
				}

				mimemessage.setContent(mimemultipart);

				//set CC MAIL and SEND the mail
				if(!mailto.equals(""))
				{
					// set CC MAIL
					try	
					{
						// send MAIL
						Transport.send(mimemessage);
						//System.out.println("\tSent Successfully..........");
						//strResult = "\tSent Successfully..........";
					}	catch(javax.mail.AuthenticationFailedException ex)			{
						strResult = "Erro ao enviar e-mail => Falha na Autenticação";
					}	catch(Exception exception4)			{
						//System.out.println("\tError in sending Address Try........." + exception4.getMessage());
						strResult = "Erro ao enviar e-mail => "+ exception4.getMessage();
					}
				} 
				else
				{
					//System.out.println("\tMail operation Failed..........\t");
					//strResult = "\tMail operation Failed..........\t";
					strResult = "Erro ao enviar e-mail, e-mail destino não informado";
				}

				// delete the file after sending
				//File file = new File(filename);
				//file.delete();
			}
			catch(AddressException addressexception)
			{
				//addressexception.printStackTrace();
				strResult = "O seguinte erro ocorreu ao enviar e-mail => "+ addressexception.getMessage();
			}
			catch(SendFailedException sendfailedexception)
			{
				//sendfailedexception.printStackTrace();
				strResult = "O seguinte erro ocorreu ao enviar e-mail => "+ sendfailedexception.getMessage();
			}
			catch(MessagingException messagingexception)
			{
				//System.out.println("Exception delivering message ("+ messagingexception.getMessage());
				strResult = "O seguinte erro ocorreu ao enviar e-mail => "+ messagingexception.getMessage();
			}
			catch(Exception exception2)
			{
				//exception2.printStackTrace();
				strResult = "O seguinte erro ocorreu ao enviar e-mail => "+ exception2.getMessage();
			}
		}
		catch(Exception exception)
		{
			//System.out.println("\tInitialization Block........." + exception.getMessage());
			strResult = "O seguinte erro ocorreu ao enviar e-mail => "+ exception.getMessage();
		}
		
		
		if(!strResult.equals("")){
			logger.error(strResult);
		}else{
			logger.info("Email enviado com sucesso !"); 
		}

	}

}
