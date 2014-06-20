package com.riis.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ContactList extends BasePersistentModel
{	
	private ArrayList<Contact> contacts;
	private Context context;
	private long id;
	private String name;
	
	public ContactList(Context context) 
	{
		super(context);
		this.context = context;
		this.id = -1;
		this.name = "";
		contacts = new ArrayList<Contact>();
	}
	
	public Long getId()
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
		
		open();	
		id = database.insert("contactList", null, values);
		close();

		if (id == -1)
		{
			return false;			
		}
		
		boolean returnVal = true;
		if(size() > 0)
		{
			for(int i = 0; i < size(); i++)
			{
				returnVal = insertMemberIntoContactList(getContact(i));
			}
		}
		
		return returnVal;
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
		return readWithOrderByClause("messageSentTimeStamp ASC");
	}
	
	public boolean readAllContacts()
	{
		return readWithOrderByClause("lastName ASC");
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
		boolean result = readContactListFromCursor(cursor);
		cursor.close();
		close();
		
		if(!result)
		{
			return false;
		}
		
		open();
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
		Cursor cursor = database.query("contactList", null, "_id =" +id, null, null, null, null);		
		boolean result = readContactListFromCursor(cursor);
		cursor.close();
		close();
		return result;
	}
	
	@Override
	public boolean update() 
	{
		if(id == -1 || getName().equals(""))
		{
			return false;
		}
		
		open();			
		ContentValues values = new ContentValues();
		values.put("name", getName());
		long updateId = database.update("contactList", values, "_id = "+ getId(), null);
		close();
			
		if (updateId != 1)
		{
			return false;			
		}
		
		open();
		Cursor refCursor = database.query("contactListMembers", null, "contactListId = "+ getId(), null, null, null, null);
		
		ArrayList<Contact> storedContacts = readContactListMembersFromCursor(refCursor);
		
		refCursor.close();
		close();
		
		boolean returnVal = false;
		if(size() > 0 && storedContacts.size() > 0)
		{
			for(int i = 0; i < storedContacts.size(); i++)
			{
				if(!contacts.contains(storedContacts.get(i)))
				{
					open();
					int delete = database.delete("contactListMembers", "contactListId = "+ getId() 
							+" AND contactId = "+ storedContacts.get(i).getId(), null);
					close();
					if(delete != 1)
					{
						return false;
					}
				}
			}
			
			for(int i = 0; i < size(); i++)
			{
				if(!storedContacts.contains(getContact(i)))
				{
					returnVal = insertMemberIntoContactList(getContact(i));
				}
			}
		}
		else if(size() == 0 && storedContacts.size() > 0)
		{
			open();
			database.delete("contactListMembers", "contactListId = "+ getId(), null);
			close();
		}
		else if(size() > 0 && storedContacts.size() == 0)
		{
			for(int i = 0; i < size(); i++)
			{
				returnVal = insertMemberIntoContactList(getContact(i));
			}
		}
		
		return returnVal;
	}
	
	private boolean insertMemberIntoContactList(Contact contact)
	{
		ContentValues refValues = new ContentValues();
		refValues.put("contactListId", getId());
		refValues.put("contactId", contact.getId());
		
		open();
		long refId = database.insert("contactListMembers", null, refValues);
		close();
		
		if(refId == -1)
		{
			return false;
		}
		
		return true;
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
	
	private boolean readContactListFromCursor(Cursor cursor)
	{
		if (cursor.getCount() == 1)
		{
			cursor.moveToFirst();
			id = (cursor.getLong(0));
			return true;
		}

		return false;
	}
	
	private boolean readWithOrderByClause(String orderBy)
	{
		String[] columns = {"_id"};
		
		open();
		Cursor cursor = database.query("contact", columns, null, null, null, null, orderBy);
		
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
}