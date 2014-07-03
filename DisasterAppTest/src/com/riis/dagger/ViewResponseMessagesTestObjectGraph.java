package com.riis.dagger;

import android.content.Context;

import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.responseMessages.ResponseMessageItemClickListener;
import com.riis.controllers.responseMessages.ResponseMessagesAdapter;
import com.riis.dagger.mock.MockContactList;
import com.riis.dagger.mock.MockListOfContactLists;
import com.riis.dagger.mock.MockResponseMessageList;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessageList;

import dagger.Module;
import dagger.Provides;

@Module(injects={ViewResponseMessagesActivity.class, ResponseMessagesAdapter.class})
public class ViewResponseMessagesTestObjectGraph
{
	Context context;
	
	public ViewResponseMessagesTestObjectGraph(Context context)
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
	
	@Provides ResponseMessageItemClickListener provideResponseMessageItemClickListener()
	{
		return new ResponseMessageItemClickListener();
	}
}
