package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.controllers.EmergencyMessageTextWatcher;
import com.riis.controllers.MessageRecepiantsListAdapter;
import com.riis.controllers.textMessage.TextMessageSender;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class SendEmergencyMessageActivity extends Activity
{
	private Button noEmailButton;
	private TextView characterCountLabel;
	private TextView noEmailInfoLabel;
	private EditText emergencyMessageField;
	private ListView recipientsList;
	
	@Inject ContactList contactList;
	@Inject TextMessageSender textMessageSender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getSendEmergencyMessageObjectGraph();
		objectGraph.inject(this);
		setContentView(R.layout.send_emergency_message_screen);
		
		Bundle extras = getIntent().getExtras();
        String contactListName = extras.getString("CONTACT_LIST_NAME");
        
        contactList.setName(contactListName);
        contactList.read();
        
        noEmailButton = (Button) findViewById(R.id.noEmailButton);
		characterCountLabel = (TextView) findViewById(R.id.characterCountLabel);
		noEmailInfoLabel = (TextView) findViewById(R.id.noEmailInformationLabel);
		emergencyMessageField = (EditText) findViewById(R.id.emergencyMessageField);
		
		characterCountLabel.setText(""+ 120);
		
		emergencyMessageField.addTextChangedListener(new EmergencyMessageTextWatcher(characterCountLabel));
		
		recipientsList = (ListView) findViewById(R.id.messageRecipientsList);
		recipientsList.setAdapter(new MessageRecepiantsListAdapter(this, contactList.getContacts()));
		
		SharedPreferences prefs = getSharedPreferences("emailData", 1);
		if(prefs.getString("email", "").equals(""))
		{
			noEmailButton.setVisibility(View.VISIBLE);
			noEmailInfoLabel.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		onCreate(null);
	}
	
	public void cancelSendEmergencyMessage(View view)
	{
		finish();
	}
	
	public void sendEmergencyMessage(View view)
	{
		if(isValidEmergencyMessage(emergencyMessageField.getText().toString()))
		{
			contactList.read();
			
			for(Contact c : contactList.getContacts())
			{
				c.read();
				c.setPingCount(0);
				c.update();
			}
			
			contactList.updateMessageSentTimeStamp();
			contactList.update();
			
			ResponseMessageList responseMessageList = new ResponseMessageList(this);
			responseMessageList.read(contactList.getId());
			ArrayList<ResponseMessage> responses = responseMessageList.getResponseMessage();
			
			for(ResponseMessage r : responses) 
			{
				r.read();
				r.setMessageContents("Are you OK?");
				r.setTimeStamp(0);
				r.update();
				
				ContactReference ref = new ContactReference(this);
				ref.read(r.getReferenceId());
				ref.setNotes(0);
				ref.update();
			}
			
			Intent i = new Intent(SendEmergencyMessageActivity.this, DisasterAppService.class);
			i.putExtra("List name", contactList.getName());
			i.putExtra("Message contents", emergencyMessageField.getText().toString());
			
			PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, i, 0);
			PendingIntent secondIntent = PendingIntent.getService(getApplicationContext(), 1, i, 0);
			PendingIntent thirdIntent = PendingIntent.getService(getApplicationContext(), 2, i, 0);
			
			AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1, pendingIntent);
			manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 300000, secondIntent);
			manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 600000, thirdIntent);
			
			finish();
		}
	}
	
	public void setUpEmail(View view)
	{
		Intent i = new Intent(this, EmailInputActivity.class);
		i.putExtra("setUpEmail", true);
		startActivity(i);
	}
	
	private boolean isValidEmergencyMessage(String message)
	{
		if(message.length() > 120)
		{
			emergencyMessageField.setError("Your message must be less then 120 characters!");
			return false;
		}
		
		return true;
	}
}