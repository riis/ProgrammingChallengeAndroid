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
import android.widget.TextView;

import com.riis.controllers.DialogSingleButtonClickListener;
import com.riis.controllers.contactListSelection.ContactListSelectionAdapter;
import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;

import dagger.ObjectGraph;

public class EditContactListMembersActivity extends Activity
{
	private ListView listView;
	private Button updateButton;
	private TextView listName;
	
	@Inject ContactList contactList;
	@Inject ContactList list;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_contact_list_screen);
        
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getEditContactListMembersObjectGraph();
		objectGraph.inject(this);
        
        Bundle extras = getIntent().getExtras();
        String contactListName = extras.getString("CONTACT_LIST_NAME");
        
        listName = (TextView) findViewById(R.id.contactListNameLabel);
        listName.setText("Contact List: "+ contactListName);
        listName.setTextSize(18);
        listName.setPadding(0, 0, 0, 5);
        
        EditText contactListNameField = (EditText) findViewById(R.id.contactListNameText);
        contactListNameField.setVisibility(View.GONE);
        
        contactList.setName(contactListName);
        contactList.read();
        
        list.readAllContacts();
        
        listView = (ListView) findViewById(R.id.createContactListsView);        
        listView.setAdapter(new ContactListSelectionAdapter(this, list.getContacts(), contactListName, getApplication()));
        listView.setOnItemClickListener(new ContactListSelectionItemClickListener());
        
        updateButton = (Button) findViewById(R.id.saveCreateContactListSaveButton);
        updateButton.setText("Update");
    }
	
	public void saveContactList(View view)
	{
		for(int i = 0; i < list.size(); i++)
		{
			CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);
			
			ContactReference ref = new ContactReference(getApplicationContext());
			ref.setContactListId(contactList.getId());
			ref.setContactId(list.getContact(i).getId());
			boolean exists = ref.read();
			
			if(checkBox.isChecked())
			{
				if(exists)
				{
					ref.update();
				}
				else
				{
					ref.create();
				}
				
				ResponseMessage response = new ResponseMessage(getApplicationContext());
				response.setReferenceId(ref.getId());
		        response.setMessageContents(" Are you OK?");

		        if(response.read())
		        {
		        	response.update();
		        }
		        else
		        {
		        	response.create();
		        }
			}
			else
			{
				if(exists)
				{
					ResponseMessage response = new ResponseMessage(getApplicationContext());
					response.setReferenceId(ref.getId());
					response.read();
					response.delete();
					ref.delete();
				}
			}
		}
		
		callAlertDialog();
	}
	
	public void cancelCreateContactList(View view)
	{
		finish();
	}
	
	private void callAlertDialog()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Contact List Updated");
		dialog.setMessage("Your contact list has been updated!");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogSingleButtonClickListener(this));
		dialog.show();
	}
}