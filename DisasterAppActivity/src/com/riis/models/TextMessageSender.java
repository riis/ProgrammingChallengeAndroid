package com.riis.models;

import android.telephony.SmsManager;

public class TextMessageSender implements MessageSender{
	
	public TextMessageSender() {
	}

	@Override
	public void sendMessage(ContactList contactList, String message) {
		message = prepareMessageToSend(message);
		
		SmsManager sms = SmsManager.getDefault();
		
		for(int i = 0; i < contactList.size(); i++) {
			sms.sendTextMessage(contactList.getContact(i).getPhoneNumber(), null, message, null, null);
		}
	}
	
	private String prepareMessageToSend(String message) {
		message += " Are you OK?";
		return message;
	}
}
