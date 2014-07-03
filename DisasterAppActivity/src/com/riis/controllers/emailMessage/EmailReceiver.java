package com.riis.controllers.emailMessage;

import java.io.IOException;
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

import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class EmailReceiver extends AsyncTask<Void, Void, Void>
{
	private long id;
	private String host = "imap.gmail.com";
	private String user = "";
	private String password = "";
	private Context context;
	private ListOfContactLists lists;
	
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
		receiveEmail();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result)
	{
		if(lists != null)
		{
			for(ContactList l : lists.getContactLists())
			{
				ResponseMessageList messages = new ResponseMessageList(context);
				messages.read(l.getId());
				
				if(!messages.allContactsResponded(l.getId()))
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
		}
		else
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
	    Session imapSession = Session.getInstance(props);

		Store store;
		Message result;
		try
		{
			store = imapSession.getStore("imaps");
			store.connect(host, user, password);
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			Message[] test = inbox.getMessages();
			result = test[test.length - 1];
			result.getSentDate();
			
			if(result.getSubject().equals("Re: There has been an Emergency!"))
			{
				Contact contact = new Contact(context);
				String email = result.getFrom()[0].toString();
				
				if(email.indexOf('<') != -1)
				{
					email = email.substring(email.indexOf('<') + 1, email.lastIndexOf('>'));
				}
				contact.setEmailAddress(email);
				contact.read();
				
				storeEmailResponse(result, contact);
			}
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

	private void storeEmailResponse(Message result, Contact contact) throws MessagingException, IOException
	{
		
		lists = new ListOfContactLists(context);
		lists.readByContact(contact);
		boolean exists = false;
		for(ContactList l : lists.getContactLists())
		{
			if(l.getId() == id)
			{
				exists = true;
			}
		}
		
		if(exists)
		{
			for(ContactList l : lists.getContactLists())
			{
				String body = getBody(result);
				body = body.split("\\r?\\n")[0];
				if(body.length() > 255)
				{
					body = body.substring(0, 255);
				}
				
				ContactReference ref = new ContactReference(context);
				ref.setContactId(contact.getId());
				ref.setContactListId(l.getId());
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