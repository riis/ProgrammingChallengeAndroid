package com.riis.models;

import java.util.Calendar;

public class Contact {
	
	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private String messageSentTimeStamp;
	
	public Contact(long id) {
		this.id = id;
		this.firstName = "";
		this.lastName = "";
		this.emailAddress = "";
		this.phoneNumber = "";
		this.messageSentTimeStamp = "";
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
	
	public void setMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		this.messageSentTimeStamp = (cal.get(Calendar.MONTH) + 1) +
				"-"+ cal.get(Calendar.DAY_OF_MONTH) +
				"-"+ cal.get(Calendar.YEAR) +
				" "+cal.getTime().toString().substring(11, 16);
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
	
	public String getMessageSentTimeStamp() {
		return messageSentTimeStamp;
	}
}
