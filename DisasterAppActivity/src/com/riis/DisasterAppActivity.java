package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.controllers.MessageIndicatorItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class DisasterAppActivity extends Activity
{
	private ListView listView;
	@Inject ContactList contactList;
	@Inject MessageIndicatorItemClickListener item;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getDisasterAppObjectGraph();
		objectGraph.inject(this);
        setContentView(R.layout.main);
        
        contactList.read();
        
        listView = (ListView) findViewById(R.id.contactIndicatorListView);        
        listView.setAdapter(new MessageIndicatorAdapter(this, contactList.getContacts()));
        listView.setOnItemClickListener(item);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.disaster_app_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent();
	    switch (item.getItemId()) {
	        case R.id.createContactItem:
	        	i = new Intent(this, NewContactActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.createContactListItem:
	        	i = new Intent(this, ContactListsActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.createEmergencyMessageItem:
	        	i = new Intent(this, SendEmergencyMessageActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.viewResponseMessagesItem:
	        	i = new Intent(this, ViewResponseMessagesActivity.class);
	        	startActivity(i);
	        	return true;
	    }
	    
	    return false;
	}
	
	@Override
	protected void onResume() 
	{
	   super.onResume();
	   onCreate(null);
	}
}