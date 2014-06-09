package com.riis.models;

import java.util.Calendar;

public class Contact {
	
	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private long messageSentTimeStamp;
	
	public Contact() {
		this.firstName = "";
		this.lastName = "";
		this.emailAddress = "";
		this.phoneNumber = "";
		this.messageSentTimeStamp = 0L;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setMessageSentTimeStamp(long messageSentTimeStamp) {
		this.messageSentTimeStamp = messageSentTimeStamp;
	}
	
	public void updateMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		this.messageSentTimeStamp = cal.getTimeInMillis();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public long getMessageSentTimeStamp() {
		return messageSentTimeStamp;
	}
}
