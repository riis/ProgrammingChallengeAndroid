package com.riis.models.test;

import junit.framework.TestCase;

import com.riis.models.Contact;
import com.riis.models.ContactList;

public class ContactListTest extends TestCase{
	
	private Contact contact;
	private ContactList contactList;

	public ContactListTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		contact = new Contact(1);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		contactList = new ContactList();
	}
	
	public void testSize() {
		assertEquals(0, contactList.size());
	}
	
	public void testGetContact() {
		contactList.addContact(contact);
		assertEquals(contact, contactList.getContact(0));
	}
}
