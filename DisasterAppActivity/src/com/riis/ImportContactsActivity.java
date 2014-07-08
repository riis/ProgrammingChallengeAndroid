package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.riis.controllers.ImportedContactsAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactImporter;

import dagger.ObjectGraph;

public class ImportContactsActivity  extends Activity
{
	private ListView listView;
	@Inject ContactListSelectionItemClickListener item;
	@Inject ContactImporter importer;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getImportContactsObjectGraph();
		objectGraph.inject(this);
		
        setContentView(R.layout.import_contacts_screen);
        
	    listView = (ListView) findViewById(R.id.importedContactsListView);        
        listView.setAdapter(new ImportedContactsAdapter(this, importer.fetchContacts()));
        listView.setOnItemClickListener(item);
	}
	
	public void returnToMainScreen(View view)
	{
		finish();
	}
	
	public void importContacts(View view)
	{
		ArrayList<Contact> contacts = importer.fetchContacts();
		ArrayList<Contact> imports = new ArrayList<Contact>();
		
		for(int i = 0; i < listView.getCount(); i++)
		{
			CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);

			if(checkBox.isChecked())
			{
				imports.add(contacts.get(i));
			}
		}
		
		importer.importContacts(imports);
		
		finish();
	}
}