package com.riis.dagger.mock;

import android.content.Context;

import com.riis.models.ResponseMessage;

public class MockResponseMessage extends ResponseMessage
{
	Context context;
	
	public MockResponseMessage(Context context) {
		super(context);
		this.context = context;
		super.id = -1;
	}
	
	public void setId(long id)
	{
		super.id = id;
	}

}
