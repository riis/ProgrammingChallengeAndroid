package com.riis.models;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class ListOfContactLists extends BasePersistentModel
{
	private ArrayList<ContactList> contactLists;
	private Context context;
	
	public ListOfContactLists(Context context) 
	{
		super(context);
		this.context = context;
		contactLists = new ArrayList<ContactList>();
	}

	public int size() 
	{
		return contactLists.size();
	}
	
	public ContactList getContactList(int index)
	{
		return contactLists.get(index);
	}
	
	public void addContactList(ContactList contactList) 
	{
		contactLists.add(contactList);
	}
	
	public void setContactList(ArrayList<ContactList> contactLists) 
	{
		this.contactLists = contactLists;
	}
	
	public ArrayList<ContactList> getContactLists() 
	{
		return contactLists;
	}
	
	@Override
	public boolean create() 
	{
		return false;
	}

	@Override
	public boolean delete() 
	{
		return false;
	}

	@Override
	public boolean read()
	{	
		open();
		Cursor cursor = database.query("contactList", null, null, null, null, null,"name ASC");		
		contactLists = readListOfContactListsMembersFromCursor(cursor);
		cursor.close();
		close();
		return true;
	}

	@Override
	public boolean update()
	{
		return false;
	}
	
	private ArrayList<ContactList> readListOfContactListsMembersFromCursor(Cursor cursor)
	{
		ArrayList<ContactList> storedContactLists = new ArrayList<ContactList>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			ContactList currentContactList = new ContactList(context);
			currentContactList.setName(cursor.getString(1));

			currentContactList.setMessageSentTimeStamp(cursor.getLong(2));
			currentContactList.readByTimeStamp(); 
			
			storedContactLists.add(currentContactList);
			cursor.moveToNext();
		}
		
		return storedContactLists;
	}
}
