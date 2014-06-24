package com.riis.dagger;

import android.content.Context;

import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;

public class MockListOfContactLists extends ListOfContactLists
{
	Context context;
	
	public MockListOfContactLists(Context context)
	{
		super(context);
		this.context = context;
	}
	
	public void setListOfContactLists(ListOfContactLists listOfContactLists)
	{
		
	}
	
	public int size()
	{
		return 1;
	}
	
	public ContactList getContactList(int i)
	{
		ContactList contactList = new ContactList(context);
		contactList.setName("Family");
		return contactList;
	}
}
