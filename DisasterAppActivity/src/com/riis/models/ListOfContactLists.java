package com.riis.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ListOfContactLists extends BasePersistentModel
{
	private ArrayList<ContactList> contactLists;
	private Context context;
	private String name;
	
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
		Cursor cursor = database.query("contactList", null, null, null, null, null,
				"name ASC");		
		//boolean result = readListOfContactListsFromCursor(cursor);
		contactLists = readListOfContactListsMembersFromCursor(cursor);
		
		cursor.close();
		close();
		
//		if(!result)
//		{
//			return false;
//		}
		
		
		return true;
	}

	@Override
	public boolean update()
	{
		return false;
	}
	
	private boolean insertMemberIntoListOfContactLists(ContactList contactList)
	{
		ContentValues refValues = new ContentValues();
		refValues.put("contactListId", contactList.getId());
		
		open();
		long refId = database.insert("listofContactListsMembers", null, refValues);
		close();
		
		if(refId == -1)
		{
			return false;
		}
		
		return true;
	}
	
	private ArrayList<ContactList> readListOfContactListsMembersFromCursor(Cursor cursor)
	{
		ArrayList<ContactList> storedContactLists = new ArrayList<ContactList>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			ContactList currentContactList = new ContactList(context);
			currentContactList.setName(cursor.getString(1));
			currentContactList.read(); 
			
			storedContactLists.add(currentContactList);
			
			cursor.moveToNext();
		}
		
		return storedContactLists;
	}
	
	private boolean readListOfContactListsFromCursor(Cursor cursor)
	{
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			return true;
		}

		return false;
	}


}
