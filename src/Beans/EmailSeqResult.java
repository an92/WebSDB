package Beans;

import java.util.Properties;
import java.io.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSeqResult 
{
	private String toEmail = "";
	private String folderName = "";
	private String taskName = "";
	private static String homeFolder = "/var/lib/tomcat7/webapps/PeptideExplorer/users/";
	
	public void setParams(String to, String folderName, String taskName)
	{
		this.toEmail = to;
		this.folderName = folderName;
		this.taskName = taskName;
	}
	
	public void sendEmail() throws Exception
	{
		final String username = "PeptideExplorer@gmail.com";
		final String password = "loginstud";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try 
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("PeptideExplorer prediction results for task: " + taskName + "; Job ID: " + folderName);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			File file = new File(homeFolder + folderName + "/result.tsv");
			if(file.exists())
			{
				String part = "";
				if(Math.ceil((file.length()/(1024.0*1024))) < 15 )
					part = "The results have been attached in this email. You can also go to http://lightning.med.monash.edu/PeptideExplorer/users/" + folderName + "/results.jsp for detailed results.";
				else
					part = "The result file is too large to be attached in this email. Please go to http://lightning.med.monash.edu/PeptideExplorer/users/" + folderName + "/results.jsp for detailed and downloadable result file.";
				
		        messageBodyPart.setText("Dear User, \n\n  The prediction has been finished. " +  part + "\n\nThanks for using PeptideExplorer.\n\nRegards,\n\nPeptideExplorer Team.");
		        Multipart multipart = new MimeMultipart();
		        multipart.addBodyPart(messageBodyPart);
		        messageBodyPart = new MimeBodyPart();
		        DataSource source = new FileDataSource(homeFolder + folderName + "/result.tsv");
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName("result.tsv");
		        multipart.addBodyPart(messageBodyPart);
		        message.setContent(multipart);
	
				Transport.send(message);
	
				System.out.println("Done");
			}
		} catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}
