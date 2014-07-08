package com.riis.dagger;

import android.content.Context;

import com.riis.ImportContactsActivity;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.models.ContactImporter;

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
	
	@Provides ContactImporter provideContactImporter()
	{
		return new ContactImporter(context);
	}
	
	@Provides ContactListSelectionItemClickListener provideContactListSelectionItemListener()
	{
		return new ContactListSelectionItemClickListener();
	}
}