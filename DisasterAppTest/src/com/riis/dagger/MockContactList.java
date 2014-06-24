package com.riis.dagger;

import java.util.ArrayList;

import android.content.Context;

import com.riis.models.Contact;
import com.riis.models.ContactList;

public class MockContactList extends ContactList
{
	boolean readCall= false;
	boolean readAllCall= false;
	Context context;
	ContactList shortContactList;
	ContactList fullContactList;
	
	public MockContactList(Context context)
	{
		super(context);
		this.context = context;
	}
	
	public void setContactList(ContactList contactlist)
	{
	}
	
	public int size()
	{
		if(readCall = true)
		{
			return shortContactList.size();
		}
		
		if(readAllCall)
		{
			return fullContactList.size();
		}
		
		return 1;
	}
	
	public Contact getContact(int i)
	{
		if(readCall)
		{
			return shortContactList.getContact(i);
		}
		
		if(readAllCall)
		{
			return fullContactList.getContact(i);
		}
		
		Contact contact = new Contact(context);
		contact.setPhoneNumber("5869336419");
		return contact;
	}
	
	public boolean read()
	{
		readCall = true;
		
		MockContact contact = new MockContact(context);
		contact.setId(1);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		
		shortContactList = new ContactList(context);
		shortContactList.setName("Test");
		shortContactList.addContact(contact);
		
		return readCall;
	}
	
	public boolean readAllContacts()
	{
		readAllCall = true;
		
		MockContact contact = new MockContact(context);
		contact.setId(1);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		
		MockContact secondContact = new MockContact(context);
		secondContact.setId(2);
		secondContact.setFirstName("Mike");
		secondContact.setLastName("Richardson");
		secondContact.setEmailAddress("MJ@example.com");
		secondContact.setPhoneNumber("1235550066");
		
		fullContactList = new ContactList(context);
		fullContactList.setName("Everyone");
		fullContactList.addContact(contact);
		fullContactList.addContact(secondContact);
		
		return readAllCall;
	}
	
	public ArrayList<Contact> getContacts()
	{
		if(readAllCall)
		{
			return fullContactList.getContacts();
		}
		
		return null;
	}
}