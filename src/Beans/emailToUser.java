package Beans;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.*;

public class emailToUser 
{

	private static String toEmail = "";
	private static String results = "";
	private static String homeFolder = "/var/lib/tomcat7/webapps/ROOT/users/";
	private static String folderName = "";
	
	public void setParams(String to, String info, String folderName)
	{
		toEmail = to;
		results = info;
		this.folderName = folderName;
	}
	
	public void sendEmail() throws Exception
	{
		final String username = "MetalExplorerMonash@gmail.com";
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
			message.setSubject("MetalExplorer prediction results");
			message.setText(results);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		
		File  out = new File(homeFolder + folderName + "/output.log");
		FileWriter fw = new FileWriter(out, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(toEmail + "\n" + results);
		bw.flush();
		bw.close();
		fw.close();
	}
}
