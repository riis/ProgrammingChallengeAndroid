package com.riis.dagger;

import android.content.Context;

import com.riis.models.Contact;
import com.riis.models.ContactList;

public class MockContactList extends ContactList
{
	Context context;
	
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
		return 1;
	}
	
	public Contact getContact(int i)
	{
		Contact contact = new Contact(context);
		contact.setPhoneNumber("5869336419");
		return contact;
	}
}