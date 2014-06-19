package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.controllers.MessageIndicatorItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class ContactListsActivity extends Activity
{
	
	private ListView listView;
	private EditText contactListNameField;
	
	@Inject ContactList contactList;
	@Inject MessageIndicatorItemClickListener item;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getDisasterAppObjectGraph();
		objectGraph.inject(this);
		
        setContentView(R.layout.contact_list_screen);
        contactListNameField = (EditText) findViewById(R.id.contactListText);
       
        contactList.read();
        
        listView = (ListView) findViewById(R.id.contactListsView);        
        listView.setAdapter(new MessageIndicatorAdapter(this, contactList.getContacts()));
        listView.setOnItemClickListener(item);
    }
}
