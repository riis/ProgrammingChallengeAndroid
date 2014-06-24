package com.riis.dagger;

import android.content.Context;

import com.riis.ContactListsActivity;
import com.riis.DisasterAppActivity;
import com.riis.models.ListOfContactLists;

import dagger.Module;
import dagger.Provides;

@Module (injects={DisasterAppActivity.class, ContactListsActivity.class})
public class DisasterAppTestObjectGraph
{
	Context context;
	
	public DisasterAppTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
//	@Provides ContactList provideContactList() 
//	{
//		return new MockContactList(context);
//	}
	
	@Provides ListOfContactLists provideListOfContactLists() 
	{
		return new MockListOfContactLists(context);
	}
	
	
	
}
