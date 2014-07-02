package com.riis.dagger.mock;

import android.content.Context;

import com.riis.models.ContactReference;

public class MockContactReference extends ContactReference
{
	private long id;
	private Context context;
	private ContactReference ref;
	
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
	public void setContactListId(long id)
	{
		ref.setContactId(id);
	}
	
	@Override
	public void setContactId(long id)
	{
		ref.setContactId(id);
	}
	
	@Override
	public boolean create()
	{
		return false;
	}
	
	@Override
	public boolean read()
	{
		return false;
	}
}