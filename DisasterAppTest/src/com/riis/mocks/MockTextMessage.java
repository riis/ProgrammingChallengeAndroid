package com.riis.mocks;

public class MockTextMessage {
	
	private String phoneNumber;
	private String messageContents;
	
	public MockTextMessage() {
		phoneNumber = "";
		messageContents = "Are you OK?";
	}
	
	public void setTextMessage(String phoneNumber, String message) {
		this.phoneNumber = phoneNumber;
		messageContents = message;
	}
	
	public String getMessagePhoneNumber() {
		return phoneNumber;
	}
	
	public String getMessageContents() {
		return messageContents;
	}

}
