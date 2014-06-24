package com.riis.models.test;

import java.util.Calendar;

import junit.framework.TestCase;

import com.riis.models.ResponseMessage;

public class ResponseMessageTest extends TestCase{
	
	private ResponseMessage responseMessage;

	public ResponseMessageTest(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		
		responseMessage = new ResponseMessage(null);
	}
	
	public void testGetFormattedMessageSentTimeStamp()
	{
		responseMessage.updateMessageSentTimeStamp();
		Calendar cal = Calendar.getInstance();
		String date = (cal.get(Calendar.MONTH) + 1) +
				"-"+ cal.get(Calendar.DAY_OF_MONTH) +
				"-"+ cal.get(Calendar.YEAR) +
				" "+cal.getTime().toString().substring(11, 16);
		assertEquals(date, responseMessage.getFormattedMessageSentTimeStamp());
	}
	
	public void testNewPhoneNumber()
	{
		responseMessage.setPhoneNumber("5555555555");
		assertEquals("5555555555", responseMessage.getPhoneNumber());
	}
	
	public void testNewTextMessageContents()
	{
		responseMessage.setTextMessageContents("This is a test response.");
		assertEquals("This is a test response.", responseMessage.getTextMessageContents());
	}
	
	public void testNewContactListId()
	{
		responseMessage.setContactListId(1);
		assertEquals(1, responseMessage.getContactListId());
	}
	
	public void testInitialPhoneNumber()
	{
		assertNotNull(responseMessage.getPhoneNumber());
	}
	
	public void testInitialTextMessageContents()
	{
		assertNotNull(responseMessage.getTextMessageContents());
	}
	
	public void testInitialContactListId()
	{
		assertEquals(-1, responseMessage.getContactListId());
	}
}