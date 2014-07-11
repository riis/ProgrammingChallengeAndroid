package com.riis.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

public class Contact extends BasePersistentModel 
{	
	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private int pingCount;
	
	public Contact(Context context) 
	{
		super(context);
		this.id = -1;
		this.firstName = "";
		this.lastName = "";
		this.emailAddress = "";
		this.phoneNumber = "";
		this.pingCount = 0;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public void setPingCount(int pingCount)
	{
		this.pingCount = pingCount;
	}
	
	public long getId()
	{
		return id;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getEmailAddress()
	{
		return emailAddress;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public int getPingCount()
	{
		return pingCount;
	}
	
	public boolean exists()
	{
		if(read())
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean create() 
	{
		if (isFieldEmpty() || exists())
		{
			return false;
		}
		
		open();			
		ContentValues values = new ContentValues();
		values.put("firstName", getFirstName());
		values.put("lastName", getLastName());
		values.put("emailAddress", getEmailAddress());
		values.put("phoneNumber", getPhoneNumber());
		values.put("pingCount", getPingCount());
		
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
			setPingCount(cursor.getInt(5));
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
		values.put("pingCount", getPingCount());
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
//				|| emailAddress.isEmpty()
				|| phoneNumber.isEmpty())
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
	
	public void parseName(String name)
	{
		String[] strings = TextUtils.split(name, " ");
		setFirstName(strings[0]);
		
		if(strings.length > 1)
		{
			setLastName(strings[1]);
		}
	}
}