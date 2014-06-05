package com.riis.models;

import java.util.Calendar;

public class ResponseMessage {
	
	private String phoneNumber;
	private String timeStamp;
	private String textMessageContents;
	
	public ResponseMessage() {
		this.phoneNumber = "";
		this.timeStamp = "";
		this.textMessageContents = "";
	}
	
	public void updateMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		this.timeStamp = (cal.get(Calendar.MONTH) + 1) +
				"-"+ cal.get(Calendar.DAY_OF_MONTH) +
				"-"+ cal.get(Calendar.YEAR) +
				" "+cal.getTime().toString().substring(11, 16);
	}
	
	public void setTextMessageContents(String textMessageContents) {
		this.textMessageContents = textMessageContents;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public String getTextMessageContents() {
		return textMessageContents;
	}
}
