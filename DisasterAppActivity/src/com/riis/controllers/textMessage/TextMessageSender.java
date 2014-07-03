package com.riis.controllers.textMessage;

import android.telephony.SmsManager;

import com.riis.models.Contact;
import com.riis.models.ContactList;

public class TextMessageSender
{
	public TextMessageSender()
	{
	}

	public boolean sendMessage(ContactList contactList, String message)
	{
		message = prepareMessageToSend(message);
		
		SmsManager sms = SmsManager.getDefault();
		
		for(int i = 0; i < contactList.size(); i++)
		{
			Contact contact = contactList.getContact(i);
			contact.setPingCount(1);
			contact.update();
			
			sms.sendTextMessage(contactList.getContact(i).getPhoneNumber(), null, message, null, null);
		}
		return true;
	}
	
	public boolean sendIndividualMessage(Contact contact, String message)
	{
		message = prepareMessageToSend(message);
		
		SmsManager sms = SmsManager.getDefault();
		
		sms.sendTextMessage(contact.getPhoneNumber(), null, message, null, null);
		
		return true;
	}
	
	private String prepareMessageToSend(String message)
	{
		message += " Are you OK?";
		return message;
	}
}