package com.riis.models;

public class Contact {
	
	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	
	public Contact() {
		this.id = 0L;
		this.firstName = "";
		this.lastName = "";
		this.emailAddress = "";
		this.phoneNumber = "";
	}
	
	public void setId(Long id) {  //did Thomas change anything here?
		this.id = id;
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
}
