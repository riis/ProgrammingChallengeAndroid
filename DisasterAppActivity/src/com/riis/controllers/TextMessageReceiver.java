package com.riis.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.riis.models.Contact;
import com.riis.models.ContactList;
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
		ContactList contactList = new ContactList(context);
		contactList.read();
		
		for(int i = 0; i < contactList.size(); i++)
		{
		   if(contactList.getContact(i).getPhoneNumber().equals(sms[sms.length - 1].getOriginatingAddress().toString().substring(2))
					&& contactList.getContact(i).getMessageSentTimeStamp() != 0L)	
			{
				ResponseMessage response = new ResponseMessage(context);
				response.setPhoneNumber(sms[sms.length - 1].getOriginatingAddress().substring(2));
				response.setTextMessageContents(sms[sms.length - 1].getMessageBody());
				response.updateMessageSentTimeStamp();
				response.create();
				
				Contact contact = contactList.getContact(i);
				contact.setMessageSentTimeStamp(0L);  
				contact.update();
				return true;
			}
		}		
		return false;
	}
}
