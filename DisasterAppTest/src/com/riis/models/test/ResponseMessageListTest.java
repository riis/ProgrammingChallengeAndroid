package com.riis.models.test;

import junit.framework.TestCase;

import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class ResponseMessageListTest extends TestCase
{
	private ResponseMessage responseMessage;
	private ResponseMessageList responseMessageList;

	public ResponseMessageListTest(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		
		responseMessage = new ResponseMessage(null);
		responseMessage.setMessageContents("Are you OK?");
		responseMessage.updateMessageSentTimeStamp();
		
		responseMessageList = new ResponseMessageList(null);
	}
	
	public void testSize()
	{
		assertEquals(0, responseMessageList.size());
	}
	
	public void testGetContact()
	{
		responseMessageList.addResponseMessage(responseMessage);
		assertEquals(responseMessage, responseMessageList.getResponseMessage(0));
	}
}