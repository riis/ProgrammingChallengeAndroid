package com.riis.dagger;

import com.riis.models.Contact;
import com.riis.models.ContactList;

public class MockContactList extends ContactList
{
	public void setContactList(ContactList contactlist)
	{
		
	}
	
	public int size()
	{
		return 1;
	}
	public Contact getContact(int i)
	{
		Contact contact = new Contact();
		contact.setPhoneNumber("5869336419");
		return contact;
		
	}
}
