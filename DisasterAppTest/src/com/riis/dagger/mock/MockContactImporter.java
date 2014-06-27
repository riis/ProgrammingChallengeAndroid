package com.riis.dagger.mock;

import android.content.Context;

import com.riis.models.ContactImporter;

public class MockContactImporter extends ContactImporter 
{
	Context context;

	public MockContactImporter(Context context)
	{
		super(context);
		this.context = context;
	}
}
