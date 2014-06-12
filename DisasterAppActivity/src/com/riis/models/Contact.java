package com.riis.models;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Contact extends BasePersistentModel
{	
	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private long messageSentTimeStamp;
	
	public Contact(Context context) 
	{
		super(context);
		this.id = -1;
		this.firstName = "";
		this.lastName = "";
		this.emailAddress = "";
		this.phoneNumber = "";
		this.messageSentTimeStamp = 0L;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setMessageSentTimeStamp(long messageSentTimeStamp) {
		this.messageSentTimeStamp = messageSentTimeStamp;
	}
	
	public void updateMessageSentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		this.messageSentTimeStamp = cal.getTimeInMillis();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public long getMessageSentTimeStamp() {
		return messageSentTimeStamp;
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
		values.put("firstName", getFirstName());
		values.put("lastName", getLastName());
		values.put("emailAddress", getEmailAddress());
		values.put("phoneNumber", getPhoneNumber());
		values.put("messageSentTimeStamp", getMessageSentTimeStamp());
		
		id = database.insert("contact", null, values);
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
		int result = database.delete("contact", "_id = "+ id, null);
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
		Cursor cursor = database.query("contact", null, selection.toString(), null, null, null, null);		
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
		Cursor cursor = database.query("contact", null, "_id =" +id, null, null, null, null);		
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
			id = (cursor.getLong(0));
			setFirstName(cursor.getString(1));
			setLastName(cursor.getString(2));
			setEmailAddress(cursor.getString(3));
			setPhoneNumber(cursor.getString(4));
			setMessageSentTimeStamp(cursor.getLong(5));
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
		values.put("firstName", getFirstName());
		values.put("lastName", getLastName());
		values.put("emailAddress", getEmailAddress());
		values.put("phoneNumber", getPhoneNumber());
		values.put("messageSentTimeStamp", getMessageSentTimeStamp());
		long updateId = database.update("contact", values, "_id = "+ id, null);
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
		if (!firstName.isEmpty())
		{
			selection.append("firstName='").append(firstName).append("'");
			and = " AND ";
		}
		if (!lastName.isEmpty())
		{
			selection.append(and);
			selection.append("lastName='").append(lastName).append("'");
			and = " AND ";
		}
		if (!emailAddress.isEmpty())
		{
			selection.append(and);
			selection.append("emailAddress='").append(emailAddress).append("'");
			and = " AND ";
		}
		if (!phoneNumber.isEmpty())
		{
			selection.append(and);
			selection.append("phoneNumber='").append(phoneNumber).append("'");
		}
		return selection.toString();
	}
	
	private boolean isFieldEmpty() 
	{
		if(firstName.isEmpty() || lastName.isEmpty() 
				|| emailAddress.isEmpty() || phoneNumber.isEmpty())
		{
			return true;
		}
		return false;
	}

	private boolean isClassEmpty() 
	{
		if(firstName.isEmpty() && lastName.isEmpty() 
				&& emailAddress.isEmpty() && phoneNumber.isEmpty())
		{
			return true;
		}
		return false;
	}
}
