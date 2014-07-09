package com.riis.dagger.mock;

import android.content.Context;

import com.riis.models.ContactReference;

public class MockContactReference extends ContactReference
{
	private long id;
	private Context context;
	
	public MockContactReference(Context context) {
		super(context);
		this.context = context;
		this.id = -1;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	@Override
	public long getId()
	{
		return id;
	}
	
	@Override
	public boolean create()
	{
		id = 2;
		
		setContactListId(2);
		setContactId(1);
		
		return true;
	}
	
	@Override
	public boolean read()
	{
		return false;
	}
}