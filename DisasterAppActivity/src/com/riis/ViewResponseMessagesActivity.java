package com.riis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.models.ContactList;

public class ViewResponseMessagesActivity extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_messages_screen);
        
        ContactList contactList = new ContactList(this);
        contactList.readByTimeStamp();
       
        ListView listView = (ListView) findViewById(R.id.responseMessagesListView);

        listView.setAdapter(new ResponseMessagesAdapter(this, contactList.getContacts()));
    }
	
	public void returnToMainScreen(View view)
	{
		finish();
	}
}