package com.riis.models;

import java.util.Calendar;

public class ResponseMessage {
	
	private String phoneNumber;
	private long timeStamp;
	private String textMessageContents;
	
	public ResponseMessage() {
		this.phoneNumber = "";
		this.timeStamp = 0L;
		this.textMessageContents = "";
	}
	
	public String getFormattedMessageSentTimeStamp() {
		String date = "";
		
		if(timeStamp == 0L)
			return date;
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timeStamp);
			date = (cal.get(Calendar.MONTH) + 1) +
					"-"+ cal.get(Calendar.DAY_OF_MONTH) +
					"-"+ cal.get(Calendar.YEAR) +
					" "+cal.getTime().toString().substring(11, 16);
			return date;
		}
	}
	
	public void updateMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		this.timeStamp = cal.getTimeInMillis();
	}
	
	public void setTextMessageContents(String textMessageContents) {
		this.textMessageContents = textMessageContents;
	}
	
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public String getTextMessageContents() {
		return textMessageContents;
	}
}
