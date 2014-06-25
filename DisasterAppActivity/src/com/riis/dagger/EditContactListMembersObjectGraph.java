package com.riis.dagger;

import android.content.Context;

import com.riis.EditContactListMembersActivity;
import com.riis.controllers.contactListSelection.ContactListSelectionAdapter;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects={EditContactListMembersActivity.class, ContactListSelectionAdapter.class})
public class EditContactListMembersObjectGraph
{
	private Context context;
	
	public EditContactListMembersObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList() 
	{
		return new ContactList(context);
	}
}