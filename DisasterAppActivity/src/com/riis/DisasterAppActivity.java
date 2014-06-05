package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.riis.controllers.ContactDataSource;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class DisasterAppActivity extends Activity{
	
	private ContactDataSource dataSource;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dataSource = new ContactDataSource(this);
        dataSource.open();
        Contact contact;
        try {
          	contact = dataSource.getContact();
        } catch (Exception e) {
        	contact = new Contact(1);
        }

       // dataSource.close();
      TextView contactView = (TextView) findViewById(R.id.sampleLabel);
      
      
//      contactView.setText(contact.getFirstName() + " " + contact.getLastName()
//      		+ "\n"+ contact.getEmailAddress()
//      		+ "\n"+ contact.getPhoneNumber());
      ContactList contactList = new ContactList();
       contactList.setContactList(dataSource.getContactList());
        for(int i =0;i<dataSource.getContactList().size();i++)
        {
        	contact = contactList.getContact(i);
        	addContactToTextView(contactView, contact);
        	
        }
       dataSource.close();

    }
	
	 public void addContactToTextView(TextView textView, Contact contact)
	   {
		   textView.append( contact.getFirstName() 
				   + " " + contact.getLastName() 
				   + "\t"+ contact.getEmailAddress() 
				   + "\t"+ contact.getPhoneNumber()
				   + "\n");
		
	   }
    
    public void createContactScreen(View view) {
    	Intent intent = new Intent(this, NewContactActivity.class);
    	startActivity(intent);
    }
    
   public void createEmergencyMessageScreen(View view) {
    	Intent intent = new Intent(this, SendEmergencyMessageActivity.class);
    	startActivity(intent);
    }
   
  
}