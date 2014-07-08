package com.riis.dagger;

import android.content.Context;

import com.riis.CloneContactListActivity;
import com.riis.CreateContactListsActivity;
import com.riis.EditContactListMembersActivity;
import com.riis.controllers.contactListSelection.ContactListSelectionAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.dagger.mock.MockContactList;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects={CreateContactListsActivity.class, CloneContactListActivity.class,
		EditContactListMembersActivity.class, ContactListSelectionAdapter.class})
public class CRUDContactListTestObjectGraph 
{
	Context context;
	
	public CRUDContactListTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList()
	{
		return new MockContactList(context);
	}
	
	@Provides ContactListSelectionItemClickListener provideContactListSelectionItemClickListener()
	{
		return new ContactListSelectionItemClickListener();
	}
}