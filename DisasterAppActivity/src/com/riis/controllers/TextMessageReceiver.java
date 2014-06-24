package com.riis.controllers;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.riis.models.Contact;
import com.riis.models.ContactList;
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
					candidates.add(list.getContactList(i));
				}
			}
		}
		
		for(int i = 0; i < candidates.size(); i++)
		{
		   if(candidates.get(i).getContact(i).getPhoneNumber().equals(phoneNumber)
					&& candidates.get(i).getMessageSentTimeStamp() != 0L)	
			{
				ResponseMessage response = new ResponseMessage(context);
				response.setPhoneNumber(sms[sms.length - 1].getOriginatingAddress().substring(2));
				
				boolean exists = response.read();
				
				if(exists)
				{
					response.setContactListId(candidates.get(i).getId());
					boolean success = response.read();
					Log.i("success", success+"");
					response.setTextMessageContents(sms[sms.length - 1].getMessageBody());
					response.updateMessageSentTimeStamp();
					response.update();
				}
				else
				{
					response.setTextMessageContents(sms[sms.length - 1].getMessageBody());
					response.setContactListId(candidates.get(i).getId());
					response.updateMessageSentTimeStamp();
					response.create();
				}
				
				return true;
			}
		}		
		return false;
	}
}