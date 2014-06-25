package com.riis.dagger;

import android.content.Context;

import com.riis.DisasterAppActivity;
import com.riis.controllers.contactListDisplay.ContactListDisplayAdapter;
import com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener;
import com.riis.dagger.mock.MockContactList;
import com.riis.dagger.mock.MockListOfContactLists;
import com.riis.dagger.mock.MockResponseMessageList;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessageList;

import dagger.Module;
import dagger.Provides;

@Module (injects={DisasterAppActivity.class, ContactListDisplayAdapter.class})
public class DisasterAppTestObjectGraph
{
	Context context;
	
	public DisasterAppTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList()
	{
		return new MockContactList(context);
	}
	
	@Provides ListOfContactLists provideListOfContactLists() 
	{
		return new MockListOfContactLists(context);
	}
	
	@Provides ResponseMessageList provideResponseMessageList()
	{
		return new MockResponseMessageList(context);
	}
	
	@Provides ContactListDisplayItemClickListener provideMessageIndicatorItemClickListener() 
	{
		return new ContactListDisplayItemClickListener();
	}
}