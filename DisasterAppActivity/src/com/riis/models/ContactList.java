package com.riis.models;

import java.util.ArrayList;

public class ContactList {
	
	private ArrayList<Contact> contacts;
	
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
	
	public void setContactList(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
}
