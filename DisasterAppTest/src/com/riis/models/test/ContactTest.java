package com.riis.models.test;

import java.util.Calendar;
import java.util.Locale;

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
	
	public void testMessageWasSent() {
		//newContact.messageWasSent();
		assertTrue(newContact.getBooleanMessageSent());
	}
	
	public void testMessageWasReceived() {
		//newContact.messageWasReceived();
		assertFalse(newContact.getBooleanMessageSent());
	}
	
	public void testMessageSentTimeStamp() {
		newContact.setMessageSentTimeStamp();
		Calendar cal = Calendar.getInstance();
		String date = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) +
				"-"+ cal.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.US) +
				"-"+ cal.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.US) +
				" "+cal.getTime().toString();
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
	
	public void testInitialMessageSent() {
		assertFalse(newContact.getBooleanMessageSent());
	}
	
	public void testInitialMessageSentTimeStamp() {
		assertNotNull(newContact.getMessageSentTimeStamp());
	}
}