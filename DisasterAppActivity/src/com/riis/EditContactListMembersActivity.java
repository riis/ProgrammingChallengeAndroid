package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.controllers.contactListSelection.ContactListSelectionItemClickListener;
import com.riis.controllers.contactListSelection.ContactListSelectionAdapter;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;

import dagger.ObjectGraph;

public class EditContactListMembersActivity extends Activity
{
	private ListView listView;
	private Button updateButton;
	private Button cancelButton;
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
        updateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				contactList.setContactList(new ArrayList<Contact>());
				
				for(int i = 0; i < list.size(); i++)
				{
					CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);
					if(checkBox.isChecked())
					{
						contactList.addContact(list.getContact(i));
						ResponseMessage response = new ResponseMessage(getApplicationContext());
				        response.setTextMessageContents(" Are you OK?");
				        response.setPhoneNumber(list.getContact(i).getPhoneNumber());
				        response.setContactListId(contactList.getId());
				        if(response.read())
				        {
				        	response.update();
				        }
				        else
				        {
				        	response.create();
				        }
					}
				}
				
				contactList.update();
				
				finish();
			}
		});
        
        cancelButton = (Button) findViewById(R.id.cancelCreateContactListButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }
}