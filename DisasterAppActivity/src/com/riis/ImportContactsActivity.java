package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.controllers.ImportedContactsAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactImporter;

import dagger.ObjectGraph;

public class ImportContactsActivity  extends Activity
{
	private Button cancelButton;
	private Button importButton;
	private ListView listView;
	private TextView noImportsLabel;
	@Inject ContactListSelectionItemClickListener item;
	@Inject ContactImporter importer;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getImportContactsObjectGraph();
		objectGraph.inject(this);
		
        setContentView(R.layout.import_contacts_screen);
        
        noImportsLabel = (TextView) findViewById(R.id.noContactsToImportLabel);
        cancelButton = (Button) findViewById(R.id.cancelImportContactsButton);
        importButton = (Button) findViewById(R.id.saveImportedContactsButton);
        
	    listView = (ListView) findViewById(R.id.importedContactsListView);        
        listView.setAdapter(new ImportedContactsAdapter(this, importer.fetchContacts()));
        listView.setOnItemClickListener(item);
        
        if(listView.getCount() == 0)
        {
        	noImportsLabel.setVisibility(View.VISIBLE);
        	importButton.setVisibility(View.GONE);
        	cancelButton.setText("Back");
        }
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
			if(listView.getChildAt(i) != null)
			{
				Log.i("in if statement", i+"");
				CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);

				if(checkBox.isChecked())
				{
					imports.add(contacts.get(i));
				}
			}
		}
		
		importer.importContacts(imports);
		
		finish();
	}
}