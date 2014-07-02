package com.riis.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email extends javax.mail.Authenticator
{
	private ArrayList<String> toField; 
	private String fromField; 

	private String password;
	private String port; 
	private String sport; 
	private String host; 
	private String bodyField; 
	 
	private boolean authenticate; 
	private boolean debuggable; 
	 
	private Multipart multipart; 
	 
	public Email()
	{
		host = "smtp.gmail.com";
		port = "465";
		sport = "465";
		 
		password = "";
		fromField = ""; 
		bodyField = "";
		 
		debuggable = false;
		authenticate = true;
		 
		multipart = new MimeMultipart(); 
		 
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
		CommandMap.setDefaultCommandMap(mc); 
	} 
	 
	public Email(String fromField, String password)
	{ 
		this();
		this.fromField = fromField; 
		this.password = password;
	}
	
	public boolean send() throws AddressException, MessagingException
	{
		Properties props = setProperties();
		
		if(!password.equals("") && toField.size() > 0 && !fromField.equals("")
				&& !bodyField.equals(""))
		{
			Session session = Session.getInstance(props, this);
			
			MimeMessage msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(fromField));
			
			InternetAddress[] addressTo = new InternetAddress[toField.size()];
			
			for (int i = 0; i < toField.size(); i++)
			{
				addressTo[i] = new InternetAddress(toField.get(i));
			}
			
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 
 
			msg.setSubject("There has been an Emergency!"); 
			msg.setSentDate(new Date());
			
			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setText(bodyField);
			multipart.addBodyPart(messageBodyPart); 
			
			msg.setContent(multipart); 
			
			Transport.send(msg);
			return true;
		}
		else
		{ 
			return false; 
		}
	}
	
	@Override 
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(fromField, password);
	}
	
	private Properties setProperties()
	{
		Properties props = new Properties(); 
		props.put("mail.smtp.host", host); 
		
		if(debuggable)
		{
			props.put("mail.debug", "true");
		}
		
		if(authenticate)
		{
			props.put("mail.smtp.auth", "true"); 
		}
		
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", sport);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false"); 
		
		return props;
	} 
 
	public void setBody(String bodyField)
	{
		this.bodyField = bodyField;
	}
	
	public void setToField(ArrayList<String> toField)
	{
		this.toField = toField;
	}
}