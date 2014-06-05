package com.riis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.ContactDataSource;
import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.models.ContactList;

public class ViewResponseMessagesActivity extends Activity{
	
	private ContactDataSource contactDataSource;
//	private ResponseMessageDataSource responseMessageDataSource;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_messages_screen);
        
        contactDataSource = new ContactDataSource(this);
//        responseMessageDataSource = new ResponseMessageDataSource(this);
        
        contactDataSource.open();
        ContactList contactList = new ContactList();
        contactList.setContactList(contactDataSource.getContactList());
        contactDataSource.close();
       
        ListView listView = (ListView) findViewById(R.id.responseMessagesListView);

        
        listView.setAdapter(new ResponseMessagesAdapter(this, contactList.getContacts()));
    }
	
	public void returnToMainScreen(View view) {
		finish();
	}
}
