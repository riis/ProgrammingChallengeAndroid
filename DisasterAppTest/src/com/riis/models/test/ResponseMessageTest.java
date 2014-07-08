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
				"-"+ cal.get(Calendar.YEAR);
		assertTrue(responseMessage.getFormattedMessageSentTimeStamp().contains(date));
	}
	
	public void testNewTextMessageContents()
	{
		responseMessage.setMessageContents("This is a test response.");
		assertEquals("This is a test response.", responseMessage.getMessageContents());
	}
	
	public void testInitialTextMessageContents()
	{
		assertNotNull(responseMessage.getMessageContents());
	}
}