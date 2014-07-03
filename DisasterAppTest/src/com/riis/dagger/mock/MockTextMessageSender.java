package com.riis.dagger.mock;

import com.riis.controllers.textMessage.TextMessageSender;
import com.riis.models.ContactList;

public class MockTextMessageSender extends TextMessageSender
{
	@Override
	public boolean sendMessage(ContactList contactList, String message)
	{
		if(contactList.size()!=1)
		{
			return false;
		}
		
		if(!"5869336419".equals(contactList.getContact(0).getPhoneNumber()))
		{
			return false;
		}
		
		return true;	
	}
}