package com.riis.dagger;

import android.content.Context;

import com.riis.CloneContactListActivity;
import com.riis.CreateContactListsActivity;
import com.riis.EditContactListMembersActivity;
import com.riis.controllers.contactListSelection.ContactListSelectionAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects={CreateContactListsActivity.class, CloneContactListActivity.class,
		EditContactListMembersActivity.class, ContactListSelectionAdapter.class})
public class CRUDContactListObjectGraph
{
	private Context context;
	
	public CRUDContactListObjectGraph(Context context)
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