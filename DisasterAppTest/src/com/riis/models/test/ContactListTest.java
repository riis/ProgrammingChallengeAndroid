package com.riis.models.test;

import java.util.Calendar;

import junit.framework.TestCase;

import com.riis.models.Contact;
import com.riis.models.ContactList;

public class ContactListTest extends TestCase
{
	private Contact contact;
	private ContactList contactList;

	public ContactListTest(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		
		contact = new Contact(null);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		contactList = new ContactList(null);
	}
	
	public void testSize()
	{
		assertEquals(0, contactList.size());
	}
	
	public void testGetContact()
	{
		contactList.addContact(contact);
		assertEquals(contact, contactList.getContact(0));
	}
	
	public void testSetContactListName()
	{
		contactList.setName("Test List");
		assertEquals("Test List", contactList.getName());
	}
	
	public void testNewMessageSentTimeStamp()
	{
		Calendar cal = Calendar.getInstance();
		long time = cal.getTimeInMillis();
		contactList.setMessageSentTimeStamp(time);
		assertEquals(contactList.getMessageSentTimeStamp(), time);
	}
	
	public void testUpdateMessageSentTimeStamp()
	{
		contactList.updateMessageSentTimeStamp();
		assertTrue(contactList.getMessageSentTimeStamp() > 0);
	}
	
	public void testInitialMessageSentTimeStamp()
	{
		assertNotNull(contactList.getMessageSentTimeStamp());
	}
}