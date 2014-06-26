package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.ImportedContactsAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactImporter;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class ImportContactsActivity  extends Activity
{
	private ListView listView;
	private ArrayList<Contact> contacts;
	@Inject ContactList everyoneList;
	@Inject ContactListSelectionItemClickListener item;
	@Inject ContactImporter importer;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getImportContactsObjectGraph();
		objectGraph.inject(this);
		
		contacts = new ArrayList<Contact>();
        setContentView(R.layout.import_contacts_screen);
        
        ContentResolver contentResolver = getContentResolver();
        contacts = importer.fetchContacts(contentResolver, contacts);
        
        everyoneList.setName("Everyone");
        everyoneList.read();
        
	    listView = (ListView) findViewById(R.id.importedContactsListView);        
        listView.setAdapter(new ImportedContactsAdapter(this, contacts));
        listView.setOnItemClickListener(item);
	}
	
	public void returnToMainScreen(View view)
	{
		finish();
	}
	
	public void importContacts(View view)
	{
		everyoneList.read();
		importer.importContacts(contacts, everyoneList, listView);
		
		finish();
	}
}