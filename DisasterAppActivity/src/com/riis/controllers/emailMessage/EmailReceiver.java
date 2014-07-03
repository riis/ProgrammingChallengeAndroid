package com.riis.controllers.emailMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class EmailReceiver extends AsyncTask<Void, Void, Void>
{
	private long id;
	private String host = "imap.gmail.com";
	private String user = "";
	private String password = "";
	private Context context;
	
	public EmailReceiver(Context context, long id)
	{
		SharedPreferences prefs = context.getSharedPreferences("emailData", 1);
		user = prefs.getString("email", "");
		password = prefs.getString("password", "");
		this.context = context;
		this.id = id;
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		Log.i("in background", "background");
		receiveEmail();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result)
	{
		ResponseMessageList messages = new ResponseMessageList(context);
		messages.read(id);
		
		if(!messages.allContactsResponded(id))
		{
			Handler handler = new Handler();
	        handler.postDelayed(new Runnable()
	        {
				@Override
				public void run()
				{
					new EmailReceiver(context, id).execute();
				}
	        },10000);
		}
	}
	
	private void receiveEmail() 
	{
		Properties props = new Properties();
	    props.setProperty("mail.store.protocol", "imaps");
	    props.setProperty("mail.imaps.host", "imaps.gmail.com");
	    props.setProperty("mail.imaps.port", "993");
	    props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.setProperty("mail.imaps.socketFactory.fallback", "false");
	    
		try
		{
		    Session imapSession = Session.getInstance(props);
	
			Store store = imapSession.getStore("imaps");
			store.connect(host, user, password);
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			retrieveMessage(inbox.getMessages());
			store.close();
		}
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void retrieveMessage(Message[] test) throws MessagingException, IOException
	{
		long newEmailTimeStamp = 0;
		
		ContactList myContactList = new ContactList(context);
		myContactList.read(id);
		
		for(Message m : test)
		{
			Log.i("in for loop", "for");
			
			if(m.getSubject().equals("Re: There has been an Emergency!"))
			{
				Log.i("in if subject", "subject");
				Contact contact = new Contact(context);
				contact.setEmailAddress(getEmailAddressFromMail(m));
				contact.read();

				ArrayList<Contact> t = myContactList.getContacts();
				
				Log.i("contact id", contact.getId()+"");
				Log.i("contains", myContactList.contains(contact)+"");
				Log.i("timeStamp", (myContactList.getMessageSentTimeStamp() < m.getSentDate().getTime()) +"");
				
				if(t.contains(contact) && myContactList.getMessageSentTimeStamp() < m.getSentDate().getTime())
				{
					if(newEmailTimeStamp < m.getSentDate().getTime())
					{
						newEmailTimeStamp = m.getSentDate().getTime();
					}
					
					storeEmailResponse(m, contact);
				}
			}
		}
		
		myContactList.setMessageSentTimeStamp(newEmailTimeStamp);
	}

	private String getEmailAddressFromMail(Message m) throws MessagingException {
		String email = m.getFrom()[0].toString();
		
		if(email.indexOf('<') != -1)
		{
			email = email.substring(email.indexOf('<') + 1, email.lastIndexOf('>'));
		}
		return email;
	}

	private void storeEmailResponse(Message result, Contact contact) throws MessagingException, IOException
	{
		Log.i("contains works", "works");
		
		String body = getBody(result);
		body = body.split("\\r?\\n")[0];
		if(body.length() > 255)
		{
			body = body.substring(0, 255);
		}
		
		ContactReference ref = new ContactReference(context);
		ref.setContactId(contact.getId());
		ref.setContactListId(id);
		ref.read();
		
		ResponseMessage response = new ResponseMessage(context);
		response.setReferenceId(ref.getId());
		response.read();
		
		if(response.getTimeStamp() == 0)
		{
			response.setMessageContents(body);
			response.updateMessageSentTimeStamp();
			response.update();
		}
	}
	
	private String getBody(Part p) throws MessagingException, IOException
    {
		String test = "";
        if (p.isMimeType("text/plain"))
        {
            if (!p.getContent().toString().equals(null))
                test = (String)p.getContent();
        }
        else if (p.isMimeType("multipart/*"))
        {
            Multipart mp = (Multipart)p.getContent();

            for (int x = 0; x < mp.getCount(); x++)
            {
                test += getBody(mp.getBodyPart(x));
            }
        }
        
        return test;
    }
}