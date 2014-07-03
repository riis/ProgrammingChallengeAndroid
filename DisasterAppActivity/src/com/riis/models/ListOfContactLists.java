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
	
	public boolean readByContact(Contact contact)
	{
		if(contact.getId() == -1)
		{
			return false;
		}
		
		open();
		String t = "SELECT * FROM contactList l INNER JOIN contactListMembers m ON m.contactListId = l._id "
				+ "INNER JOIN contact c ON c._id=m.contactId WHERE m.contactId="
				+ contact.getId() +" AND l.messageSentTimeStamp != 0 ORDER BY l.messageSentTimeStamp DESC";
		
		Cursor cursor = database.rawQuery(t, null);
		boolean returnVal = cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			ContactList next = new ContactList(context);
			boolean success = next.read(cursor.getLong(0));
			if (success)
			{
				contactLists.add(next);
			}
			else
			{
				returnVal = false;
			}
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return returnVal;
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