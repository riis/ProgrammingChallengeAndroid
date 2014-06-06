package com.riis.models.test;

import junit.framework.TestCase;

import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.TextMessageSender;

public class TextMessageSenderTest extends TestCase{
	
	private TextMessageSender textMessageSender;
	
	public TextMessageSenderTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		textMessageSender = new TextMessageSender();
	}
	
	public void testSendMessage() {
		boolean flag = false;
		
		String message = "Testing";
		
		Contact contact = new Contact();
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		ContactList contactList = new ContactList();
		contactList.addContact(contact);
		
		try {
			textMessageSender.sendMessage(contactList, message);
			flag = true;
		} catch(IllegalArgumentException e)
		{
			flag = false;
		}
		
		assertTrue(flag);
	}
}