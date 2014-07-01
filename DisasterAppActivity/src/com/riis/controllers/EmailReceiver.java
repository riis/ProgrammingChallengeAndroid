package com.riis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.riis.models.Contact;
import com.riis.models.ResponseMessage;

public class EmailReceiver
{
	String host = "imap.gmail.com";
	String user = "";
	String password = "";
	String subjectSubstringToSearch = "Test E-Mail through Java";
	private Context context;
	
	public EmailReceiver(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences("emailData", 1);
		user = prefs.getString("email", "");
		password = prefs.getString("password", "");
		this.context = context;
	}
	
	public void receiveEmail()
	{
		Properties props = new Properties();
	    //IMAPS protocol
	    props.setProperty("mail.store.protocol", "imaps");
	    //Set host address
	    props.setProperty("mail.imaps.host", "imaps.gmail.com");
	    //Set specified port
	    props.setProperty("mail.imaps.port", "993");
	    //Using SSL
	    props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.setProperty("mail.imaps.socketFactory.fallback", "false");
	    //Setting IMAP session
	    Session imapSession = Session.getInstance(props);

		Store store;
		Message[] result = new Message[1];
		try
		{
			store = imapSession.getStore("imaps");
			store.connect(host, user, password);
			//Get all mails in Inbox Folder
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			//Return result to array of message
			result = inbox.getMessages();
		} catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i < result.length; i++)
		{
			try
			{
				if(result[i].getSubject().contains("There has been an Emergency!"))
				{
					Contact contact = new Contact(context);
					contact.setEmailAddress(result[i].getFrom()[0].toString());
					contact.read();
					ResponseMessage response = new ResponseMessage(context);
				}
			} catch (MessagingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void readContent(Message m) throws IOException, MessagingException
	{
	    Object o = m.getContent();
	    
	    if (o instanceof String)
	    {
	    	Log.i("", "**This is a String Message**");
	    	Log.i("", (String)o);
		}
	    else if (o instanceof Multipart)
	    {
	    	Log.i("", "**This is a Multipart Message.  ");
	    	Multipart mp = (Multipart)o;
	    	int count3 = mp.getCount();
	    	Log.i("", "It has " + count3 +" BodyParts in it**");
	    	for (int j = 0; j < count3; j++)
	    	{
	    		BodyPart b = mp.getBodyPart(j);
	    		String mimeType2 = b.getContentType();
	    		Log.i("", "BodyPart " +(j + 1) +" is of MimeType " + m.getContentType());

	    		Object o2 = b.getContent();
	    		
	    		if (o2 instanceof String)
	    		{
	    			Log.i("", "**This is a String BodyPart**");
	    			Log.i("", (String)o2);
	    		}
	    		else if (o2 instanceof Multipart)
	    		{
	    			Log.i("", "**This BodyPart is a nested Multipart.");
	    			Multipart mp2 = (Multipart)o2;
	    			int count2 = mp2.getCount();
	    			Log.i("", "It has " + count2 + "further BodyParts in it**");
	    		}
	    		else if (o2 instanceof InputStream)
	    		{
	    			Log.i("", "**This is an InputStream BodyPart**");
	    		}
    		}
	    }
	    else if (o instanceof InputStream)
	    {
	    	Log.i("", "**This is an InputStream message**");
	    	InputStream is = (InputStream)o;
	    	// Assumes character content (not binary images)
	    	int c;
	    	while ((c = is.read()) != -1)
	    	{
	    		System.out.write(c);
	    	}
	    }
	}
}