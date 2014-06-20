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

import com.riis.controllers.ContactSelectionAdapter;
import com.riis.controllers.ContactSelectionItemClickListener;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class EditContactListMembersActivity extends Activity
{
	private ListView listView;
	private Button updateButton;
	private TextView listName;
	
	@Inject ContactList contactList;
	@Inject ContactList updatedList;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.create_contact_list_screen);
        
        listName = (TextView) findViewById(R.id.contactListNameLabel);
        
        EditText contactListNameField = (EditText) findViewById(R.id.contactListNameText);
        contactListNameField.setVisibility(View.GONE);
        
        contactList.readAllContacts();
        
        listView = (ListView) findViewById(R.id.createContactListsView);        
        listView.setAdapter(new ContactSelectionAdapter(this, contactList.getContacts()));
        listView.setOnItemClickListener(new ContactSelectionItemClickListener());
        
        updateButton = (Button) findViewById(R.id.saveCreateContactListSaveButton);
        updateButton.setText("Update");
        updateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updatedList.setName(listName.getText().toString());
				updatedList.read();
				
				updatedList.setContactList(new ArrayList<Contact>());
				
				for(int i = 0; i < listView.getCount(); i++)
				{
					CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectContactCheckBox);
					if(checkBox.isSelected())
					{
						updatedList.addContact(contactList.getContact(i));
					}
				}
				
				updatedList.update();
				
				finish();
			}
		});
    }
	
	public void updateContactList(View view)
	{
		
	}
	
	public void cancelUpdateContactList(View view)
	{
		finish();
	}
}