package com.riis.dagger;

import android.content.Context;

import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.responseMessages.ResponseMessageItemClickListener;
import com.riis.controllers.responseMessages.ResponseMessagesAdapter;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessageList;

import dagger.Module;
import dagger.Provides;

@Module(injects={ViewResponseMessagesActivity.class, ResponseMessagesAdapter.class})
public class ViewResponseMessagesObjectGraph
{
	Context context;
	
	public ViewResponseMessagesObjectGraph(Context context)
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
	
	@Provides ResponseMessageItemClickListener provideResponseMessageItemClickListener()
	{
		return new ResponseMessageItemClickListener();
	}
}