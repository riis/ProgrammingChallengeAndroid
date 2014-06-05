package com.riis.models.test;

import java.util.Calendar;

import junit.framework.TestCase;

import com.riis.models.Contact;

public class ContactTest extends TestCase {
	
	private Contact newContact;

	public ContactTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		newContact = new Contact(1);
	}
	
	public void testNewFirstName() {
		newContact.setFirstName("Bob");
		assertEquals("Bob", newContact.getFirstName());
	}
	
	public void testNewLastName() {
		newContact.setLastName("Jones");
		assertEquals("Jones", newContact.getLastName());
	}
	
	public void testNewEmailAddress() {
		newContact.setEmailAddress("bjones@example.com");
		assertEquals("bjones@example.com", newContact.getEmailAddress());
	}
	
	public void testNewPhoneNumber() {
		newContact.setPhoneNumber("5555555555");
		assertEquals("5555555555", newContact.getPhoneNumber());
	}
	
	public void testNewMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		String date = (cal.get(Calendar.MONTH) + 1) +
				"-"+ cal.get(Calendar.DAY_OF_MONTH) +
				"-"+ cal.get(Calendar.YEAR) +
				" "+cal.getTime().toString().substring(11, 16);
		newContact.setMessageSentTimeStamp(date);
		assertEquals(newContact.getMessageSentTimeStamp(), date);
	}
	
	public void testUpdateMessageSentTimeStamp() {
		newContact.updateMessageSentTimeStamp();
		Calendar cal = Calendar.getInstance();
		String date = (cal.get(Calendar.MONTH) + 1) +
				"-"+ cal.get(Calendar.DAY_OF_MONTH) +
				"-"+ cal.get(Calendar.YEAR) +
				" "+cal.getTime().toString().substring(11, 16);
		assertEquals(newContact.getMessageSentTimeStamp(), date);
	}
	
	public void testInitialId() {
		assertEquals(1L, (long) newContact.getId());
	}
	
	public void testInitialFirstName() {
		assertNotNull(newContact.getFirstName());
	}
	
	public void testInitialLastName() {
		assertNotNull(newContact.getLastName());
	}
	
	public void testInitialEmailAddress() {
		assertNotNull(newContact.getEmailAddress());
	}
	
	public void testInitialPhoneNumber() {
		assertNotNull(newContact.getPhoneNumber());
	}
	
	public void testInitialMessageSentTimeStamp() {
		assertNotNull(newContact.getMessageSentTimeStamp());
	}
}