package com.riis.dagger;

import android.content.Context;

import com.riis.models.Contact;

public class MockContact extends Contact
{
	public MockContact(Context context)
	{
		super(context);
		super.id = -1;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
}
