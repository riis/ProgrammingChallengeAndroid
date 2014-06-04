package com.riis.models;

public class ResponseMessage {
	
	private String phoneNumber;
	private String textMessageContents;
	
	public ResponseMessage() {
		this.phoneNumber = "";
		this.textMessageContents = "";
	}
	
	public void setTextMessageContents(String textMessageContents) {
		this.textMessageContents = textMessageContents;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getTextMessageContents() {
		return textMessageContents;
	}
}
