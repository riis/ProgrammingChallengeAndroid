package com.riis.dagger;

import android.content.Context;

import com.riis.ContactDetailsActivity;

import dagger.Module;

@Module(injects=ContactDetailsActivity.class)
public class ContactDetailsObjectGraph
{
	Context context;
	
	public ContactDetailsObjectGraph(Context context)
	{
		this.context = context;
	}
}
