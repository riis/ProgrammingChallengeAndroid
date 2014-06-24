package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.controllers.EmergencyMessageTextWatcher;
import com.riis.controllers.TextMessageSender;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class SendEmergencyMessageActivity extends Activity {
	
	private TextView characterCountLabel;
	private EditText emergencyMessageField;
	@Inject ContactList contactList;
	@Inject TextMessageSender textMessageSender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getSendEmergencyMessageObjectGraph();
		objectGraph.inject(this);
		setContentView(R.layout.message_screen);
		
		characterCountLabel = (TextView) findViewById(R.id.characterCountLabel);
		characterCountLabel.setText(""+ 120);
		emergencyMessageField = (EditText) findViewById(R.id.emergencyMessageField);
		
		emergencyMessageField.addTextChangedListener(new EmergencyMessageTextWatcher(characterCountLabel));
	}
	
	public void cancelSendEmergencyMessage(View view)
	{
		finish();
	}
	
	public void sendEmergencyMessage(View view)
	{
		if(isValidEmergencyMessage(emergencyMessageField.getText().toString()))
		{
			contactList.setName("Everyone");
			contactList.read();
			contactList.updateMessageSentTimeStamp();
			contactList.update();
			
//			for(int i = 0; i < contactList.size(); i++)
//			{
//				Contact contact = contactList.getContact(i);
//				contact.updateMessageSentTimeStamp();
//				contact.update();
//			}
			
			textMessageSender.sendMessage(contactList, emergencyMessageField.getText().toString());
			
			ResponseMessageList responseMessageList = new ResponseMessageList(this);
			responseMessageList.read(1);
			ArrayList<ResponseMessage> responses = responseMessageList.getResponseMessage();
			
			for(int i = 0; i < responses.size(); i++) 
			{
				ResponseMessage response = responses.get(i);
				response.read();
				response.setTextMessageContents("Are you OK?");
				response.setTimeStamp(0);
				response.update();
			}

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