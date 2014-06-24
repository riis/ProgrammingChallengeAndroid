package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.riis.controllers.contactListDisplay.ContactListDisplayAdapter;
import com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;

import dagger.ObjectGraph;

public class DisasterAppActivity extends Activity
{
	private ListView listView;
	@Inject ContactList contactList;
	@Inject ListOfContactLists listOfContactLists;
	@Inject ContactListDisplayItemClickListener item;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getDisasterAppObjectGraph();
		objectGraph.inject(this);
        setContentView(R.layout.main);
        
        listOfContactLists.read();
        
        listView = (ListView) findViewById(R.id.contactListDisplay);        
        listView.setAdapter(new ContactListDisplayAdapter(this, listOfContactLists.getContactLists()));
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
	        	i = new Intent(this, CreateContactListsActivity.class);
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