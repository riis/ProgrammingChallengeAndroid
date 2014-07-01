package com.riis.controllers;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessage;

public class TextMessageReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		
		Object[] messages = (Object[]) bundle.get("pdus");
		SmsMessage[] sms = new SmsMessage[messages.length];
		
		for(int i = 0; i < messages.length; i++)
		{
			sms[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
		}
		
		isEmergencyContact(context, sms);
	}

	private boolean isEmergencyContact(Context context, SmsMessage[] sms)
	{
		ListOfContactLists list = new ListOfContactLists(context);
		list.read();
		
		String phoneNumber = sms[sms.length - 1].getOriginatingAddress().toString().substring(2);
		Contact contact = new Contact(context);
		contact.setPhoneNumber(phoneNumber);
		contact.read();
		
		ArrayList<ContactList> candidates = new ArrayList<ContactList>();
		
		for(int i = 0; i < list.size(); i++)
		{
			ContactList current = list.getContactList(i);
			current.read();
			
			for(int j = 0; j < current.size(); j++)
			{
				if(current.getContact(j).getId() == contact.getId())
				{
					candidates.add(current);
				}
			}
		}
		
		for(int i = 0; i < candidates.size(); i++)
		{
			if(candidates.get(i).getMessageSentTimeStamp() != 0L)
			{
				ContactReference ref = new ContactReference(context);
				ref.setContactListId(candidates.get(i).getId());
				ref.setContactId(contact.getId());
				ref.read();
				
				ResponseMessage response = new ResponseMessage(context);
				response.setReferenceId(ref.getId());
				
				boolean exists = response.read();
				if(exists)
				{
					response.setTextMessageContents(sms[sms.length - 1].getMessageBody());
					response.updateMessageSentTimeStamp();
					response.update();
				}
				else
				{
					response.setTextMessageContents(sms[sms.length - 1].getMessageBody());
					response.updateMessageSentTimeStamp();
					response.create();
				}
				
				return true;
			}
		}		
		return false;
	}
}