package com.riis.dagger.mock;

import java.util.ArrayList;

import android.content.Context;

import com.riis.models.Contact;
import com.riis.models.ContactImporter;

public class MockContactImporter extends ContactImporter 
{
	private Context context;

	public MockContactImporter(Context context)
	{
		super(context);
		this.context = context;
	}
	
	@Override
	public ArrayList<Contact> fetchContacts()
	{
		MockContactList list = new MockContactList(context);
		list.readAllContacts();
		
		return list.getContacts();
	}
}