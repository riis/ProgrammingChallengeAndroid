package com.riis.models;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ContactList extends BasePersistentModel
{	
	private ArrayList<Contact> contacts;
	private Context context;
	private long id;
	private String name;
	private long messageSentTimeStamp;
	
	public ContactList(Context context) 
	{
		super(context);
		this.context = context;
		this.id = -1;
		this.name = "";
		this.messageSentTimeStamp = 0L;
		contacts = new ArrayList<Contact>();
	}
	
	public long getMessageSentTimeStamp()
	{
		return messageSentTimeStamp;
	}
	
	public void setMessageSentTimeStamp(long messageSentTimeStamp)
	{
		this.messageSentTimeStamp = messageSentTimeStamp;
	}
	
	public void updateMessageSentTimeStamp()
	{
		Calendar cal = Calendar.getInstance();
		this.messageSentTimeStamp = cal.getTimeInMillis();
	}
	
	public long getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
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
		if (getName().equals(""))
		{
			return false;
		}
				
		ContentValues values = new ContentValues();
		values.put("name", getName());
		
		boolean exists = read();
		if(exists)
		{
			return false;
		}
		
		values.put("messageSentTimeStamp", getMessageSentTimeStamp());
		
		open();	
		database.beginTransaction();
		try
		{
			id = database.insert("contactList", null, values);
			
			if(id == -1)
			{
				throw new MemberDatabaseException();
			}
			
			if(size() > 0)
			{
				for(int i = 0; i < size(); i++)
				{
					insertMemberIntoContactList(getContact(i));
				}
			}
			database.setTransactionSuccessful();
		}
		catch (MemberDatabaseException e)
		{
			return false;
		}
		finally
		{
			database.endTransaction();
			close();
		}
		
		return true;
	}

	@Override
	public boolean delete() 
	{
		open();
		database.delete("contactListMembers", "contactListId = "+ getId(), null);
			
		int result = database.delete("contactList", "_id = "+ getId(), null);
		close();

		if (result == 0)
		{
			return false;			
		}
		return true;
	}
	
	public boolean readByTimeStamp()
	{
		if (getName().equals(""))
		{
			return false;
		}		
		
		open();
		Cursor cursor = database.query("contactList", null, "name = '"+ getName() +"'", null, null, null,
				"messageSentTimeStamp ASC");
		try
		{
			readContactListFromCursor(cursor);
		}
		catch(MemberDatabaseException e)
		{
			close();
			return false;
		}
		finally
		{
			cursor.close();
		}
		
		Cursor refCursor = database.query("contactListMembers", null, "contactListId = "+ getId(), null, null, null,
				null);
		if(refCursor.moveToFirst())
		{
			contacts = readContactListMembersFromCursor(refCursor);
		}
		
		refCursor.close();
		
		return true;
	}
	
	public boolean readAllContacts()
	{
		open();
		Cursor cursor = database.query("contact", null, null, null, null, null, "lastName ASC");
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Contact currentContact = new Contact(context);
			boolean success = currentContact.read(cursor.getLong(0)); 

			if (success)
			{
				contacts.add(currentContact);
			}
	
			cursor.moveToNext();
		}
		cursor.close();
		close();
		
		return true;
	}
	
	@Override
	public boolean read()
	{
		if (getName().equals(""))
		{
			return false;
		}
		
		open();
		Cursor cursor = database.query("contactList", null, "name = '"+ getName() +"'", null, null, null,
				null);		
		try
		{
			readContactListFromCursor(cursor);
		}
		catch (MemberDatabaseException e1)
		{
			close();
			return false;
		}
		finally
		{
			cursor.close();
		}
			
		Cursor refCursor = database.query("contactListMembers", null, "contactListId = "+ getId(), null, null, null,
				null);
			
		if(refCursor.moveToFirst())
		{
			contacts = readContactListMembersFromCursor(refCursor);
		}
		
		refCursor.close();
		close();
		
		return true;
	}
	
	public boolean read(long id) 
	{
		if (id == -1)
		{
			return false;
		}
		
		open();
		database.beginTransaction();
		try
		{
			Cursor cursor = database.query("contactList", null, "_id =" +id, null, null, null, null);
			readContactListFromCursor(cursor);
			cursor.close();
			
			database.setTransactionSuccessful();
		}
		catch (MemberDatabaseException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			database.endTransaction();
			close();
		}
		
		return true;
	}
	
	@Override
	public boolean update() 
	{
		if(id == -1)
		{
			return false;
		}

		ContentValues values = new ContentValues();
		values.put("name", getName());
		values.put("messageSentTimeStamp", getMessageSentTimeStamp());
		
		open();
		Cursor refCursor = database.query("contactListMembers", null, "contactListId = "+ getId(), null, null, null, null);
		refCursor.close();
		database.beginTransaction();
		try
		{
			long updateId = database.update("contactList", values, "_id = "+ getId(), null);
			
			if (updateId != 1)
			{
				throw new MemberDatabaseException();			
			}
			
			database.setTransactionSuccessful();
		}
		catch(MemberDatabaseException e)
		{
			return false;
		}
		finally
		{
			database.endTransaction();
			close();
		}
			
		return true;
	}
	
	private void insertMemberIntoContactList(Contact contact) throws MemberDatabaseException
	{
		if(!database.inTransaction())
		{
			throw new MemberDatabaseException();
		}
		
		ContentValues refValues = new ContentValues();
		refValues.put("contactListId", getId());
		refValues.put("contactId", contact.getId());
		
		long refId = database.insert("contactListMembers", null, refValues);

		
		if(refId == -1)
		{
			throw new MemberDatabaseException();
		}
	}
	
	private ArrayList<Contact> readContactListMembersFromCursor(Cursor cursor)
	{
		ArrayList<Contact> storedContacts = new ArrayList<Contact>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Contact currentContact = new Contact(context);
			boolean success = currentContact.read(cursor.getInt(2)); 
			if (success)
			{
				storedContacts.add(currentContact);
			}
			cursor.moveToNext();
		}
		return storedContacts;
	}
	
	private void readContactListFromCursor(Cursor cursor) throws MemberDatabaseException
	{
		if (cursor.getCount() == 1)
		{
			cursor.moveToFirst();
			id = cursor.getLong(0);
			name = cursor.getString(1);
			messageSentTimeStamp = cursor.getLong(2);
		}
		else
		{
			throw new MemberDatabaseException();
		}
	}
}