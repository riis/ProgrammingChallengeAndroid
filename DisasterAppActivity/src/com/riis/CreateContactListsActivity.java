package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class CreateContactListsActivity extends Activity
{
	private ListView listView;
	private EditText contactListNameField;
	private Button removeButton;
	private Button cloneButton;

	@Inject ContactList contactList;
	@Inject ContactListSelectionItemClickListener item;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getCRUDContactListObjectGraph();
		objectGraph.inject(this);

		setContentView(R.layout.crud_contact_list_screen);
		contactListNameField = (EditText) findViewById(R.id.contactListNameText);
		removeButton = (Button)findViewById(R.id.removeContactListButton);
        removeButton.setVisibility(View.GONE);
        
        cloneButton = (Button)findViewById(R.id.cloneContactListButton);
        cloneButton.setVisibility(View.GONE);
        
        contactList.readAllContacts();
        
        listView = (ListView) findViewById(R.id.createContactListsView);        
        listView.setAdapter(new ContactListSelectionAdapter(this, contactList.getContacts(), "", getApplication()));
        listView.setOnItemClickListener(item);
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
				for(int i = 0; i < ((ContactListSelectionAdapter) listView.getAdapter()).getChecked().size(); i++)
				{
					if(((ContactListSelectionAdapter) listView.getAdapter()).getChecked().get(i))
					{
						ContactReference ref = new ContactReference(this);
						ref.setContactListId(list.getId());
						ref.setContactId(contactList.getContact(i).getId());
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
	
	public void cancelCreateContactList(View view)
	{
		finish();
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