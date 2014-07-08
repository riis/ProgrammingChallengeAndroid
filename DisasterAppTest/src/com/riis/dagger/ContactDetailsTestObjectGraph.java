package com.riis.dagger;

import android.content.Context;

import com.riis.ContactDetailsActivity;
import com.riis.dagger.mock.MockContact;
import com.riis.models.Contact;

import dagger.Module;
import dagger.Provides;

@Module(injects=ContactDetailsActivity.class)
public class ContactDetailsTestObjectGraph
{
	Context context;
	
	public ContactDetailsTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides Contact provideContact()
	{
		return new MockContact(context);
	}
}