package com.riis.dagger;

import android.content.Context;

import com.riis.CreateContactListsActivity;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects=CreateContactListsActivity.class)
public class CreateContactListsObjectGraph
{
	Context context;
	
	public CreateContactListsObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList()
	{
		return new ContactList(context);
	}
	
	@Provides ContactListSelectionItemClickListener provideContactListSelectionItemClickListener()
	{
		return new ContactListSelectionItemClickListener();
	}
}