package com.riis.models;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Contact {
	
	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private boolean messageSent;
	private String messageSentTimeStamp;
	
	public Contact(long id) {
		this.id = id;
		this.firstName = "";
		this.lastName = "";
		this.emailAddress = "";
		this.phoneNumber = "";
		this.messageSent = false;
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
	
	public void booleanMessageWasSent() {
		this.messageSent = true;
	}
	
	public void booleanMessageWasReceived() {
		this.messageSent = false;
	}
	
	public void setMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		this.messageSentTimeStamp = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) +
				"-"+ cal.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.US) +
				"-"+ cal.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.US) +
				" "+cal.getTime().toString();
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
	
	public boolean getBooleanMessageSent() {
		return messageSent;
	}
	
	public String getMessageSentTimeStamp() {
		return messageSentTimeStamp;
	}
}
