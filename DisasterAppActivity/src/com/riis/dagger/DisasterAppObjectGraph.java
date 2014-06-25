package com.riis.dagger;

import android.content.Context;

import com.riis.DisasterAppActivity;
import com.riis.controllers.contactListDisplay.ContactListDisplayAdapter;
import com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessageList;

import dagger.Module;
import dagger.Provides;

@Module (injects={DisasterAppActivity.class, ContactListDisplayAdapter.class})
public class DisasterAppObjectGraph 
{
	Context context;
	
	public DisasterAppObjectGraph(Context context)
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
	
	@Provides ResponseMessageList provideResponseMessageList()
	{
		return new ResponseMessageList(context);
	}
	
	@Provides ContactListDisplayItemClickListener provideMessageIndicatorItemClickListener() 
	{
		return new ContactListDisplayItemClickListener();
	}
}