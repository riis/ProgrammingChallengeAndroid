package com.riis.dagger;

import android.content.Context;

import com.riis.DisasterAppActivity;
import com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;

import dagger.Module;
import dagger.Provides;

@Module (injects=DisasterAppActivity.class)
public class DisasterAppTestObjectGraph
{
	Context context;
	
	public DisasterAppTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList() 
	{
		return new ContactList(context);
	}
	
	@Provides ListOfContactLists provideListOfContactLists() 
	{
		return new ListOfContactLists(context);
	}
	
	@Provides ContactListDisplayItemClickListener provideMessageIndicatorItemClickListener() 
	{
		return new ContactListDisplayItemClickListener();
	}
}
