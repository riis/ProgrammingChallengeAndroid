package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.ContactDataSource;
import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.models.ContactList;

public class ViewResponseMessagesActivity extends Activity{
	
	private ContactDataSource contactDataSource;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_messages_screen);
        
        contactDataSource = new ContactDataSource(this);
        
        contactDataSource.open();
        ContactList contactList = new ContactList();
        contactList.setContactList(contactDataSource.getContactList());
        contactDataSource.close();
       
        ListView listView = (ListView) findViewById(R.id.responseMessagesListView);

        listView.setAdapter(new ResponseMessagesAdapter(this, contactList.getContacts()));
    }
	
	public void returnToMainScreen(View view) {
//		finish();
		Intent i = new Intent(getBaseContext(), DisasterAppActivity.class); 
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
        startActivity(i);
	}
}