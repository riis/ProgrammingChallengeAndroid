package com.riis.controllers;

import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.riis.models.ContactList;
import com.riis.models.Email;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class EmailSender extends AsyncTask<Void, Void, Integer>
{
	private Context context;
	private ContactList contactList;
	private String message;
	
	public EmailSender(Context context, ContactList contactList, String message)
	{
		this.context = context;
		this.contactList = contactList;
		this.message = prepareMessageToSend(message);
	}
	
	@Override
	protected Integer doInBackground(Void... arg0) {
		SharedPreferences prefs = context.getSharedPreferences("emailData", 1);
		
		ResponseMessageList responseMessageList = new ResponseMessageList(context);
		responseMessageList.read(contactList.getId());
		ArrayList<ResponseMessage> responses = responseMessageList.getResponseMessage();
		
		ArrayList<String> recepiants = new ArrayList<String>();
		
		for(int i = 0; i < responses.size(); i++) 
		{
			if(responses.get(i).getTimeStamp() == 0)
			{
				recepiants.add(contactList.getContact(i).getEmailAddress());
			}
		}
		
		Email email = new Email(prefs.getString("email", ""), prefs.getString("password", ""));
		email.setToField(recepiants);
		email.setBody(message);
		
		try
		{
			email.send();
		}
		catch (AddressException m)
		{
			m.printStackTrace();
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String prepareMessageToSend(String message)
	{
		message += " Are you OK?";
		return message;
	}
}