package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.contactListDisplay.ContactListDisplayAdapter;
import com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ListOfContactLists;

import dagger.ObjectGraph;

public class DisasterAppActivity extends Activity
{
	private ListView listView;
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
        listView.setAdapter(new ContactListDisplayAdapter(this, listOfContactLists.getContactLists(), getApplication()));
        listView.setOnItemClickListener(item);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.disaster_app_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Intent i = new Intent();
	    switch (item.getItemId()) 
	    {
	        case R.id.createContactItem:
	        	i = new Intent(this, ContactDetailsActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.createContactListItem:
	        	i = new Intent(this, CreateContactListsActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.importContactsItem:
	        	i = new Intent(this, ImportContactsActivity.class);
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
	
	public void createContact(View view)
	{
		Intent i = new Intent(this, ContactDetailsActivity.class);
		startActivity(i);
	}
	
	public void createContactList(View view)
	{
		Intent i = new Intent(this, CreateContactListsActivity.class);
		startActivity(i);
	}
	public void importContacts(View view)
	{
		Intent i = new Intent(this, ImportContactsActivity.class);
		startActivity(i);
	}
	
	public void viewResponseMessages(View view)
	{
		Intent i = new Intent(this, ViewResponseMessagesActivity.class);
		startActivity(i);
	}
}