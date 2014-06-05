package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.riis.controllers.ContactDataSource;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.TextMessageReceiver;

public class DisasterAppActivity extends Activity{
	
	private ContactDataSource dataSource;
	private TextMessageReceiver textMessageReceiver;
	private String receivedMessageCheck;
	private boolean tempTestingBoolean;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dataSource = new ContactDataSource(this);
        //textMessageReceiver = new TextMessageReceiver(this, intent);
        dataSource.open();
        Contact contact;
        
        
        try {
          	contact = dataSource.getContact();
        } catch (Exception e) {
        	contact = new Contact(1);
        }

      TextView contactView = (TextView) findViewById(R.id.sampleLabel);
      

      ContactList contactList = new ContactList();
       contactList.setContactList(dataSource.getContactList());
        for(int i =0;i<dataSource.getContactList().size();i++)
        {
        	contact = contactList.getContact(i);
        	
        	if(checkIfContactReceivedMessage(tempTestingBoolean))
        		  {receivedMessageCheck="*";}
        	else {receivedMessageCheck="";} 
        	
        	addContactToTextView(contactView, contact);   	
        	//using toggling just to test	
        	tempTestingBoolean =!tempTestingBoolean;
        }
       dataSource.close();

    }
	
	 public void addContactToTextView(TextView textView, Contact contact)
	   {
		 
		   textView.append( contact.getFirstName() 
				   + " " + contact.getLastName() 
				   + "\t "+ contact.getEmailAddress() 
				   + "\t "+ contact.getPhoneNumber()
				   + "\t "+ receivedMessageCheck
				   + "\n");
		
	   }
	 
	 public boolean checkIfContactReceivedMessage(boolean state)
	 { 
		 
		 return state;
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