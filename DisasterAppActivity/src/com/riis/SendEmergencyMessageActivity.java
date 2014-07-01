package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.controllers.EmailSender;
import com.riis.controllers.EmergencyMessageTextWatcher;
import com.riis.controllers.MessageRecepiantsListAdapter;
import com.riis.controllers.TextMessageSender;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class SendEmergencyMessageActivity extends Activity {
	
	private TextView characterCountLabel;
	private EditText emergencyMessageField;
	private ListView recepiantsList;
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
        
		characterCountLabel = (TextView) findViewById(R.id.characterCountLabel);
		characterCountLabel.setText(""+ 120);
		emergencyMessageField = (EditText) findViewById(R.id.emergencyMessageField);
		
		emergencyMessageField.addTextChangedListener(new EmergencyMessageTextWatcher(characterCountLabel));
		
		recepiantsList = (ListView) findViewById(R.id.messageRecepiantsList);
		recepiantsList.setAdapter(new MessageRecepiantsListAdapter(this, contactList.getContacts()));
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
			contactList.updateMessageSentTimeStamp();
			contactList.update();
			
			textMessageSender.sendMessage(contactList, emergencyMessageField.getText().toString());
			
			EmailSender task = new EmailSender(this, contactList, emergencyMessageField.getText().toString());
			task.execute();
			
			ResponseMessageList responseMessageList = new ResponseMessageList(this);
			responseMessageList.read(contactList.getId());
			ArrayList<ResponseMessage> responses = responseMessageList.getResponseMessage();
			
			for(int i = 0; i < responses.size(); i++) 
			{
				ResponseMessage response = responses.get(i);
				response.read();
				response.setTextMessageContents("Are you OK?");
				response.setTimeStamp(0);
				response.update();
			}
			
			Intent i = new Intent(SendEmergencyMessageActivity.this, DisasterAppService.class);
			i.putExtra("List name", contactList.getName());
			i.putExtra("Message contents", emergencyMessageField.getText().toString());
			
			PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, i, 0);
			PendingIntent secondIntent = PendingIntent.getService(getApplicationContext(), 1, i, 0);
			
			getApplicationContext();
			AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15000, pendingIntent);
			manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, secondIntent);

			finish();
		}
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