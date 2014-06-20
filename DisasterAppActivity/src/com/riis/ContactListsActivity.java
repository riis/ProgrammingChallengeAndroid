package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.riis.controllers.ContactSelectionAdapter;
import com.riis.controllers.ContactSelectionItemClickListener;
import com.riis.controllers.MessageIndicatorItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactImporter;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class ContactListsActivity extends Activity
{
	private ListView listView;
	private EditText contactListNameField;
	private ContactImporter importer;
	
	@Inject ContactList contactList;
	@Inject MessageIndicatorItemClickListener item;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getDisasterAppObjectGraph();
		objectGraph.inject(this);
 	   	setContentView(R.layout.create_contact_list_screen);
		contactListNameField = (EditText) findViewById(R.id.contactListNameText);
        
//        ContentResolver contentResolver = getContentResolver();
//        importer.fetchContacts(contentResolver);
        

        contactListNameField = (EditText) findViewById(R.id.contactListNameText);
       
        contactList.readAllContacts();
        
        listView = (ListView) findViewById(R.id.createContactListsView);        
        listView.setAdapter(new ContactSelectionAdapter(this, contactList.getContacts()));
        listView.setOnItemClickListener(new ContactSelectionItemClickListener());
    }
	
	public void saveContactList(View view)
	{
		if(contactListNameField.getText().toString().equals(""))
		{
			contactListNameField.setError("Please enter a name for the contact list!");
		}
		else
		{
			ContactList list = new ContactList(this);
			list.setName(contactListNameField.getText().toString());
			
			boolean success = list.create();
			if(!success)
			{
				contactListNameField.setError("Please choose a different name!");
			}
			else
			{
				for(int i = 0; i < listView.getCount(); i++)
				{
					CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);
					if(checkBox.isSelected())
					{
						list.addContact(contactList.getContact(i));
					}
				}
				
				list.update();
				
				finish();
			}
		}
	}
	
	public void cancelCreateContactList(View view)
	{
		finish();
	}
}
	

