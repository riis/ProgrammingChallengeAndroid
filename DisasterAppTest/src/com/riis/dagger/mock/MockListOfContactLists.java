package com.riis.dagger.mock;

import java.util.ArrayList;

import android.content.Context;

import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;

public class MockListOfContactLists extends ListOfContactLists
{
	boolean readCall= false;
	boolean readAllCall= false;
	Context context;
	ListOfContactLists lists;
	
	public MockListOfContactLists(Context context)
	{
		super(context);
		this.context = context;
	}
	
	public boolean read()
	{
		readCall = true;
		
		MockContactList list = new MockContactList(context);
		list.readAllContacts();
		list.setName("Test");
		list.setContactList(list.getContacts());
		
		lists = new ListOfContactLists(context);
		lists.addContactList(list);
		
		return readCall;
	}
	
	public ArrayList<ContactList> getContactLists()
	{
		if(readCall)
		{
			return lists.getContactLists();
		}
		return null;
	}
}