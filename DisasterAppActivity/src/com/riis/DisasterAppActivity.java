package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.ContactDataSource;
import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.models.ContactList;

public class DisasterAppActivity extends Activity{
	
	private ContactDataSource dataSource;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dataSource = new ContactDataSource(this);
        dataSource.open();
        ContactList contactList = new ContactList();
        
        try {
          	contactList.setContactList(dataSource.getContactList());
        } catch (Exception e) {
        	
        }
        
        dataSource.close();
        

        ListView listView = (ListView) findViewById(R.id.contactIndicatorListView);

        listView.setAdapter(new MessageIndicatorAdapter(this, contactList.getContacts()));
    }
	 
    public void createContactScreen(View view) {
    	Intent intent = new Intent(this, NewContactActivity.class);
    	startActivity(intent);
    }
    
   public void createEmergencyMessageScreen(View view) {
    	Intent intent = new Intent(this, SendEmergencyMessageActivity.class);
    	startActivity(intent);
    }

   public void viewMessageResponsesScreen(View view) {
	   Intent intent = new Intent(this, ViewResponseMessagesActivity.class);
	   startActivity(intent);
   }
}