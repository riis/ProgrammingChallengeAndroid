package com.riis.dagger;

import android.content.Context;

import com.riis.ImportContactsActivity;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects=ImportContactsActivity.class)
public class ImportContactsObjectGraph
{
	Context context;
	
	public ImportContactsObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList()
	{
		return new ContactList(context);
	}
	
	@Provides ContactListSelectionItemClickListener provideContactListSelectionItemListener()
	{
		return new ContactListSelectionItemClickListener();
	}
}