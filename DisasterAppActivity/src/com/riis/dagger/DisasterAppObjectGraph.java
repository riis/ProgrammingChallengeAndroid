package com.riis.dagger;

import android.content.Context;

import com.riis.ContactListsActivity;
import com.riis.DisasterAppActivity;
import com.riis.ImportContactsActivity;
import com.riis.controllers.ContactListDisplayItemClickListener;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects={DisasterAppActivity.class, ContactListsActivity.class, ImportContactsActivity.class})
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
	
//	@Provides ListOfContactLists provideListOfContactLists() 
//	{
//		return new ListOfContactLists(context);
//	}
	
	@Provides ContactListDisplayItemClickListener provideMessageIndicatorItemClickListener() 
	{
		return new ContactListDisplayItemClickListener();
	}
}
