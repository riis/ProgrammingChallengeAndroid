package com.riis.dagger;

import android.content.Context;

import com.riis.ContactDetailsActivity;
import com.riis.models.Contact;

import dagger.Module;
import dagger.Provides;

@Module(injects=ContactDetailsActivity.class)
public class ContactDetailsObjectGraph
{
	Context context;
	
	public ContactDetailsObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides Contact provideContact()
	{
		return new Contact(context);
	}
}