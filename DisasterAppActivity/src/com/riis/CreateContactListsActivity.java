package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
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

public class CreateContactListsActivity extends Activity
{
	private ListView listView;
	private EditText contactListNameField;

	@Inject ContactList contactList;
	@Inject ContactListSelectionItemClickListener item;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getCreateContactListsObjectGraph();
		objectGraph.inject(this);

		setContentView(R.layout.crud_contact_list_screen);
		contactListNameField = (EditText) findViewById(R.id.contactListNameText);
        contactListNameField = (EditText) findViewById(R.id.contactListNameText);
       
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
				for(int i = 0; i < listView.getCount(); i++)
				{
					CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);
					if(checkBox.isChecked())
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