package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.DisasterAppDataSource;
import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.models.ContactList;

public class DisasterAppActivity extends Activity
{	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        ContactList contactList = new ContactList(this);
        contactList.read();
                
        ListView listView = (ListView) findViewById(R.id.contactIndicatorListView);        
        listView.setAdapter(new MessageIndicatorAdapter(this, contactList.getContacts()));
    }
	
	@Override
	protected void onResume() 
	{
	   super.onResume();
	   this.onCreate(null);
	}
	 
    public void createContactScreen(View view) 
    {
    	Intent intent = new Intent(this, NewContactActivity.class);
    	startActivity(intent);
    }
    
   public void createEmergencyMessageScreen(View view)
   {
    	Intent intent = new Intent(this, SendEmergencyMessageActivity.class);
    	startActivity(intent);
    }

   public void viewMessageResponsesScreen(View view) 
   {
	   Intent intent = new Intent(this, ViewResponseMessagesActivity.class);
	   startActivity(intent);
   }
}