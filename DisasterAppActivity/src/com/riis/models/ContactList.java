package com.riis.models;

import java.util.ArrayList;
import java.util.List;

public class ContactList {
	
	private List<Contact> contacts;
	
	public ContactList() {
		contacts = new ArrayList<Contact>();
	}
	
	public int size() {
		return contacts.size();
	}
	
	public Contact getContact(int index) {
		return contacts.get(index);
	}
	
	public void addContact(Contact contact) {
		contacts.add(contact);
	}
	
	public void setContactList(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public List<Contact> getContacts() {
		return contacts;
	}
}
