package com.riis.models.test;

import junit.framework.TestCase;

import com.riis.models.ResponseMessage;

public class ResponseMessageTest extends TestCase{
	
	private ResponseMessage responseMessage;

	public ResponseMessageTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		responseMessage = new ResponseMessage();
	}
	
	public void testNewPhoneNumber() {
		responseMessage.setPhoneNumber("5555555555");
		assertEquals("5555555555", responseMessage.getPhoneNumber());
	}
	
	public void testNewTextMessageContents() {
		responseMessage.setTextMessageContents("This is a test response.");
		assertEquals("This is a test response.", responseMessage.getTextMessageContents());
	}
	
	public void testInitialPhoneNumber() {
		assertNotNull(responseMessage.getPhoneNumber());
	}
	
	public void testInitialTextMessageContents() {
		assertNotNull(responseMessage.getTextMessageContents());
	}
}