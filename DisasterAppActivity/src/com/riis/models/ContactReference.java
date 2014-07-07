package com.riis.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ContactReference extends BasePersistentModel
{
	private long id;
	private long contactListId;
	private long contactId;
	private String notes;
	
	public ContactReference(Context context)
	{
		super(context);
		this.id = -1;
		this.contactListId = -1;
		this.contactId = -1;
		setNotes("");
	}
	
	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	
	public long getContactListId()
	{
		return contactListId;
	}

	public void setContactListId(long contactListId)
	{
		this.contactListId = contactListId;
	}

	public long getContactId()
	{
		return contactId;
	}

	public void setContactId(long contactId)
	{
		this.contactId = contactId;
	}

	public long getId()
	{
		return id;
	}
	
	@Override
	public boolean create()
	{
		if (!isNew())
		{
			return false;
		}
		open();			
		ContentValues values = new ContentValues();
		values.put("contactListId", getContactListId());
		values.put("contactId", getContactId());
		values.put("notes", getNotes());
		
		id = database.insert("contactListMembers", null, values);
		close();

		if (id == -1)
		{
			return false;			
		}
		return true;
	}

	@Override
	public boolean delete()
	{
		open();			
		int result = database.delete("contactListMembers", "_id = "+ id, null);
		close();

		if (result == 0)
		{
			return false;			
		}
		return true;
	}

	@Override
	public boolean read()
	{
		if (!isNew())
		{
			return false;
		}
		
		String selection = buildWhereClause();
		
		open();
		Cursor cursor = database.query("contactListMembers", null, selection.toString(), null, null, null, null);		
		boolean result = readRecordFromCursor(cursor);
		cursor.close();
		close();
		return result;
	}
	
	public boolean read(long id)
	{
		open();
		Cursor cursor = database.query("contactListMembers", null, "_id = "+ id, null, null, null, null);		
		boolean result = readRecordFromCursor(cursor);
		cursor.close();
		close();
		return result;
	}

	@Override
	public boolean update()
	{
		if(!isNew())
		{
			return false;
		}
		open();			
		ContentValues values = new ContentValues();
		values.put("contactListId", getContactListId());
		values.put("contactId", getContactId());
		values.put("notes", getNotes());
		long updateId = database.update("contactListMembers", values, "_id = "+ id, null);
		close();
			
		if (updateId != 1)
		{
			return false;			
		}
		return true;
	}
	
	private boolean readRecordFromCursor(Cursor cursor) 
	{
		if (cursor.getCount() == 1)
		{
			cursor.moveToFirst();
			id = (cursor.getLong(0));
			setContactListId(cursor.getLong(1));
			setContactId(cursor.getLong(2));
			setNotes(cursor.getString(3));
			return true;
		}
		return false;
	}
	
	private String buildWhereClause()
	{
		StringBuilder selection = new StringBuilder();
		String  and = "";
		if (id != -1)
		{
			selection.append("_id=").append(id);
			and = " AND ";
		}
		if (contactListId != -1)
		{
			selection.append(and);
			selection.append("contactListId=").append(contactListId);
			and = " AND ";
		}
		if (contactId != -1)
		{
			selection.append(and);
			selection.append("contactId=").append(contactId);
			and = " AND ";
		}
		return selection.toString();
	}
	
	private boolean isNew()
	{
		if(id == -1 && contactListId == -1 && contactId == -1 && notes.equals(""))
		{
			return false;
		}
		return true;
	}
}