package com.riis.models;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ResponseMessage extends BasePersistentModel
{
	private long id;
	private long contactListId;
	private String phoneNumber;
	private long timeStamp;
	private String textMessageContents;
	
	public ResponseMessage(Context context) 
	{
		super(context);
		this.id = -1;
		this.contactListId = -1;
		this.phoneNumber = "";
		this.timeStamp = 0L;
		this.textMessageContents = "";
	}
	
	public String getFormattedMessageSentTimeStamp() 
	{
		String date = "";
		
		if(getTimeStamp() == 0L)
		{
			return date;
		}
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getTimeStamp());
			date = (cal.get(Calendar.MONTH) + 1) +
					"-"+ cal.get(Calendar.DAY_OF_MONTH) +
					"-"+ cal.get(Calendar.YEAR) +
					" "+cal.getTime().toString().substring(11, 16);
			return date;
		}
	}
	
	public void updateMessageSentTimeStamp() 
	{
		Calendar cal = Calendar.getInstance();
		this.timeStamp = cal.getTimeInMillis();
	}
	
	public void setContactListId(long contactListId)
	{
		this.contactListId = contactListId;
	}
	
	public void setTextMessageContents(String textMessageContents) 
	{
		this.textMessageContents = textMessageContents;
	}
	
	public void setTimeStamp(long timeStamp) 
	{
		this.timeStamp = timeStamp;
	}
	
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	
	public long getContactListId()
	{
		return contactListId;
	}
	
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	
	public long getTimeStamp() 
	{
		return timeStamp;
	}
	
	public String getTextMessageContents() 
	{
		return textMessageContents;
	}
	
	@Override
	public boolean create() 
	{
		if (isFieldEmpty())
		{
			return false;
		}
		open();			
		ContentValues values = new ContentValues();
		values.put("contactListId", getContactListId());
		values.put("phoneNumber", getPhoneNumber());
		values.put("timeStamp", getTimeStamp());
		values.put("textMessageContents", getTextMessageContents());
		
		id = database.insert("responseMessage", null, values);
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
		int result = database.delete("responseMessage", "_id = "+ id, null);
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
		if (isClassEmpty())
		{
			return false;
		}
		String selection = buildWhereClause();
		
		open();
		Cursor cursor = database.query("responseMessage", null, selection.toString(), null, null, null, null);		
		boolean result = readRecordFromCursor(cursor);
		cursor.close();
		close();
		return result;
	}

	public boolean read(long id) 
	{
		if (id == -1)
		{
			return false;
		}		
		open();
		Cursor cursor = database.query("responseMessage", null, "_id =" +id, null, null, null, null);		
		boolean result = readRecordFromCursor(cursor);
		cursor.close();
		close();
		return result;
	}

	private boolean readRecordFromCursor(Cursor cursor) 
	{
		if (cursor.getCount() == 1)
		{
			cursor.moveToFirst();
			id = cursor.getLong(0);
			setContactListId(cursor.getLong(1));
			setPhoneNumber(cursor.getString(2));
			setTimeStamp(cursor.getLong(3));
			setTextMessageContents(cursor.getString(4));
			return true;			
		}
		return false;
	}

	@Override
	public boolean update() 
	{
		if(id == -1 || isFieldEmpty())
		{
			return false;
		}
		open();			
		ContentValues values = new ContentValues();
		values.put("contactListId", getContactListId());
		values.put("phoneNumber", getPhoneNumber());
		values.put("timeStamp", getTimeStamp());
		values.put("textMessageContents", getTextMessageContents());
		long updateId = database.update("responseMessage", values, "_id = "+ id, null);
		close();
			
		if (updateId != 1)
		{
			return false;			
		}
		return true;
	}
	
	private String buildWhereClause()
	{
		StringBuilder selection = new StringBuilder();
		String  and = "";
		if (contactListId != -1)
		{
			selection.append("contactListId=").append(contactListId);
			and = " AND ";
		}
		if (!phoneNumber.isEmpty())
		{
			selection.append(and);
			selection.append("phoneNumber='").append(phoneNumber).append("'");
			and = " AND ";
		}
		if (!textMessageContents.isEmpty())
		{
			selection.append(and);
			selection.append("textMessageContents='").append(textMessageContents).append("'");
		}
		return selection.toString();
	}
	
	private boolean isFieldEmpty() 
	{
		if(phoneNumber.isEmpty() || textMessageContents.isEmpty())
		{
			return true;
		}
		return false;
	}

	private boolean isClassEmpty() 
	{
		if(phoneNumber.isEmpty() && textMessageContents.isEmpty())
		{
			return true;
		}
		return false;
	}
}