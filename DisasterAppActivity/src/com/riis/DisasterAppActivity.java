package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.riis.controllers.ContactDataSource;
import com.riis.controllers.ResponseMessageDataSource;
import com.riis.controllers.TextMessageReceiver;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class DisasterAppActivity extends Activity{
	
	private ContactDataSource dataSource;
	private ResponseMessageDataSource messageDataSource;
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
        ContactList contactList = new ContactList();
        
        try {
          	contactList.setContactList(dataSource.getContactList());
        } catch (Exception e) {
        	
        }
        
        dataSource.close();

        TextView contactView = (TextView) findViewById(R.id.sampleLabel);
        
        messageDataSource = new ResponseMessageDataSource(this);
        messageDataSource.open();
      
        for(int i =0;i<contactList.size();i++)
        {
        	if(!contactList.getContact(i).getMessageSentTimeStamp().equals("")) {
        		if(contactList.getContact(i).getPhoneNumber().equals(
        				messageDataSource.getResponseMessageList().get(i).getPhoneNumber())) {
        			String sentMessageTimeStamp = contactList.getContact(i).getMessageSentTimeStamp();
        			String textTimeStamp = messageDataSource.getResponseMessageList().get(i).getTimeStamp();
        			
        			int sentMonth = Integer.parseInt(sentMessageTimeStamp.substring(0, 1));
        			int textMonth = Integer.parseInt(textTimeStamp.substring(0, 1));
        			
        			int sentDay = Integer.parseInt(sentMessageTimeStamp.substring(3, 4));
        			int textDay = Integer.parseInt(textTimeStamp.substring(3, 4));
        			
        			int sentYear = Integer.parseInt(sentMessageTimeStamp.substring(6, 9));
        			int textYear = Integer.parseInt(textTimeStamp.substring(6, 9));
        			
        			int sentHour = Integer.parseInt(sentMessageTimeStamp.substring(12, 13));
        			int textHour = Integer.parseInt(textTimeStamp.substring(12, 13));
        			
        			int sentMinute = Integer.parseInt(sentMessageTimeStamp.substring(14, 15));
        			int textMinute = Integer.parseInt(textTimeStamp.substring(14, 15));
        			
        			Log.i("Time stamp", sentMonth +" "+ sentDay +" "+ sentYear +" "+sentHour +" "+ sentMinute);
        			addContactToTextView(contactView, contactList.getContact(i));
        		}
        	} else {receivedMessageCheck="";
        	addContactToTextView(contactView, contactList.getContact(i));}
//        	contact = contactList.getContact(i);
        	
//        	if(contact.getMessageSentTimeStamp())
//        		  {receivedMessageCheck="*";}
//        	else {receivedMessageCheck="";} 
//        	
        	   	
        	//using toggling just to test	
        	tempTestingBoolean =!tempTestingBoolean;
        }
       

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