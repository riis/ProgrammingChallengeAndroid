package com.riis.models;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class ContactList extends BasePersistentModel
{	
	private ArrayList<Contact> contacts;
	private Context context;
	
	public ContactList(Context context) 
	{
		super(context);
		this.context = context;
		contacts = new ArrayList<Contact>();
	}
	
	public int size() 
	{
		return contacts.size();
	}
	
	public Contact getContact(int index) 
	{
		return contacts.get(index);
	}
	
	public void addContact(Contact contact) 
	{
		contacts.add(contact);
	}
	
	public void setContactList(ArrayList<Contact> contacts) 
	{
		this.contacts = contacts;
	}
	
	public ArrayList<Contact> getContacts() 
	{
		return contacts;
	}

	@Override
	public boolean create() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() 
	{

		String[] columns = {"_id"};
		open();	
		Cursor cursor = database.query("contact", columns, null, null, null, null, "lastName ASC");

		boolean returnVal = cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Contact currentContact = new Contact(context);
			boolean success = currentContact.read(cursor.getInt(0)); 
			if (success)
			{
				contacts.remove(currentContact);
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
	public boolean read() 
	{
		String[] columns = {"_id"};
		
		open();
		Cursor cursor = database.query("contact", columns, null, null, null, null, "lastName ASC");

		boolean returnVal = cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Contact currentContact = new Contact(context);
			boolean success = currentContact.read(cursor.getInt(0)); 
			if (success)
			{
				contacts.add(currentContact);
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
	
	public boolean readByTimeStamp() 
	{
		String[] columns = {"_id"};
		
		open();
		Cursor cursor = database.query("contact", columns, null, null, null, null, "messageSentTimeStamp ASC");

		boolean returnVal = cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Contact currentContact = new Contact(context);
			
			boolean success = currentContact.read(cursor.getInt(0)); 
			if (success)
			{
				contacts.add(currentContact);
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
		// TODO Auto-generated method stub
		return false;
	}
}