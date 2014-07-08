package com.riis.dagger.mock;

import android.content.Context;

import com.riis.models.Contact;

public class MockContact extends Contact
{
	Contact contact;
	private long id;
	
	public MockContact(Context context)
	{
		super(context);
		this.id = -1;
	}
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	@Override
	public boolean create()
	{
		setId(1);
		setFirstName("Bob");
		setLastName("Jones");
		setEmailAddress("bjones@example.com");
		setPhoneNumber("1234567890");
		setPingCount(0);
		
		return true;
	}
	
	@Override
	public boolean read(long id)
	{
		setId(id);
		setFirstName("Bob");
		setLastName("Jones");
		setEmailAddress("bjones@example.com");
		setPhoneNumber("1234567890");
		setPingCount(0);
		
		return true;
	}
}
