package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.riis.controllers.DialogSingleButtonClickListener;
import com.riis.controllers.contactListSelection.ContactListSelectionAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;

import dagger.ObjectGraph;

public class CloneContactListActivity extends Activity
{
	private ListView listView;
	private EditText contactListNameField;
	
	@Inject ContactList contactList;
	@Inject ContactList everyoneList;
	@Inject ContactReference ref;
	@Inject ContactListSelectionItemClickListener item;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_contact_list_screen);
        
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getCRUDContactListObjectGraph();
		objectGraph.inject(this);
        
        Bundle extras = getIntent().getExtras();
        String contactListName = extras.getString("CONTACT_LIST_NAME");
        
        contactListNameField = (EditText) findViewById(R.id.contactListNameText);
		Button removeButton = (Button) findViewById(R.id.removeContactListButton);
        removeButton.setVisibility(View.GONE);
        Button cloneButton = (Button) findViewById(R.id.cloneContactListButton);
        cloneButton.setVisibility(View.GONE);
        
        everyoneList.readAllContacts();
        
        listView = (ListView) findViewById(R.id.createContactListsView);        
        listView.setAdapter(new ContactListSelectionAdapter(this, everyoneList.getContacts(), contactListName, getApplication()));
        listView.setOnItemClickListener(item);
	}
	
	public void cancelCreateContactList(View view)
	{
		finish();
	}
	
	public void saveContactList(View view)
	{
		if(contactListNameField.getText().toString().equals(""))
		{
			contactListNameField.setError("Please enter a name for the contact list!");
		}
		else
		{
			contactList.setName(contactListNameField.getText().toString());
			
			boolean success = contactList.create();
			if(!success)
			{
				contactListNameField.setError("Please choose a different name!");
			}
			else
			{
				for(int i = 0; i < listView.getCount(); i++)
				{
					CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);
					if(checkBox.isChecked())
					{
						ref.setContactListId(contactList.getId());
						ref.setContactId(everyoneList.getContact(i).getId());
						ref.create();
						
						ResponseMessage response = new ResponseMessage(this);
						response.setReferenceId(ref.getId());
				        response.setMessageContents(" Are you OK?");
				        response.create();
					}
				}
				
				callAlertDialog();
			}
		}
	}
	
	private void callAlertDialog()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Contact List Saved");
		dialog.setMessage("Your contact list has been saved!");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogSingleButtonClickListener(this));
		dialog.show();
	}
}