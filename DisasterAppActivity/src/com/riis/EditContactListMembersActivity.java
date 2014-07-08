package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getCRUDContactListObjectGraph();
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
        
        contactListNameField = (EditText) findViewById(R.id.contactListNameText);
        
        updateButton = (Button) findViewById(R.id.saveCreateContactListSaveButton);
        updateButton.setText("Update");
    }

	public void cancelCreateContactList(View view)
	{
		finish();
	}
	
	public void removeContactList(View view)
	{
		callDeleteAlertDialog();
	}
	
	public void cloneContactList(View view)
	{
		Intent i = new Intent(this, CloneContactListActivity.class);
		i.putExtra("CONTACT_LIST_NAME", contactList.getName());
		startActivity(i);
		finish();
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
		
		callUpdatedAlertDialog();
	}
	
	private void callDeleteAlertDialog()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Contact List Deleted");
		alertDialogBuilder.setMessage("Are you sure you want to delete this contact list?");
		alertDialogBuilder.setCancelable(true);
		alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			   {
					public void onClick(DialogInterface dialog,int id) 
					{
						contactList.delete();
						finish();
					}
			   });
		alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
			   {
					public void onClick(DialogInterface dialog,int id) 
					{
						dialog.cancel();
					}
			   });
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private void callUpdatedAlertDialog()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Contact List Updated");
		dialog.setMessage("Your contact list has been updated!");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogSingleButtonClickListener(this));
		dialog.show();
	}
}