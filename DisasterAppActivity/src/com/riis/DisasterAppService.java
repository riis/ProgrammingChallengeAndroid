package com.riis;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.riis.controllers.EmailSender;
import com.riis.controllers.TextMessageSender;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class DisasterAppService extends Service
{
	private ContactList contactList;
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int id)
	{
		super.onStartCommand(intent, flags, id);
		
		String name = intent.getStringExtra("List name");
		String message = intent.getStringExtra("Message contents");
		
		contactList = new ContactList(this);
		contactList.setName(name);
		contactList.read();
		
		TextMessageSender textMessageSender = new TextMessageSender();
		
		ResponseMessageList responseMessageList = new ResponseMessageList(this);
		responseMessageList.read(contactList.getId());
		ArrayList<ResponseMessage> responses = responseMessageList.getResponseMessage();
		
		for(int i = 0; i < responses.size(); i++) 
		{
			if(responses.get(i).getTimeStamp() == 0)
			{
				Contact contact = new Contact(this);
				contact.setPhoneNumber(responses.get(i).getPhoneNumber());
				contact.read();
				contact.setPingCount(contact.getPingCount() + 1);
				contact.update();
				
				textMessageSender.sendIndividualMessage(contact, message);
			}
		}
		
		EmailSender task = new EmailSender(this, contactList, message);
		task.execute();
		
		return START_NOT_STICKY;
	}
}