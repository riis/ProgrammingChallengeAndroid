package com.riis.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;

public class TextMessageReceiver extends BroadcastReceiver{
	
	private ContactDataSource contactDataSource;
	private ResponseMessageDataSource messageDataSource;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		  Log.i("SmsReceiver", "went into onReceive ");

		
		Object[] messages = (Object[]) bundle.get("pdus");
		SmsMessage[] sms = new SmsMessage[messages.length];
		
		for(int i = 0; i < messages.length; i++) {
			sms[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
		}
		
		isEmergencyContact(context, sms);
	}

	private boolean isEmergencyContact(Context context, SmsMessage[] sms) {
		contactDataSource = new ContactDataSource(context);
		contactDataSource.open();
		ContactList contactList = new ContactList();
		contactList.setContactList(contactDataSource.getContactList());
		contactDataSource.close();

		for(int i = 0; i < contactList.size(); i++) {
			if(contactList.getContact(i).getPhoneNumber().equals(sms[sms.length - 1].getOriginatingAddress().toString().substring(2))
					&& !contactList.getContact(i).getMessageSentTimeStamp().equals("")) {
				messageDataSource = new ResponseMessageDataSource(context);
				messageDataSource.open();
				
				ResponseMessage response = new ResponseMessage();
				response.setPhoneNumber(sms[sms.length - 1].getOriginatingAddress().substring(2));
				response.setTextMessageContents(sms[sms.length - 1].getMessageBody());
				response.updateMessageSentTimeStamp();
				
				messageDataSource.createResponseMessage(response);
				messageDataSource.close();
				
				contactDataSource.open();
				
				Contact contact = contactList.getContact(i);
				contact.setMessageSentTimeStamp("");
				contactDataSource.updateContact(contact);
				
				contactDataSource.close();
				
				return true;
			}
		}
		
		return false;
	}
}