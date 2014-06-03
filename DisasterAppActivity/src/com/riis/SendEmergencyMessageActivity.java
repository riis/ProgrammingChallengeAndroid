package com.riis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.controllers.ContactDataSource;
import com.riis.models.ContactList;

import com.riis.models.EmergencyMessageTextWatcher;
import com.riis.models.TextMessageSender;

public class SendEmergencyMessageActivity extends Activity {
	
	private TextView characterCountLabel;
	private EditText emergencyMessageField;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_screen);
		
		characterCountLabel = (TextView) findViewById(R.id.characterCountLabel);
		characterCountLabel.setText(""+ 120);
		emergencyMessageField = (EditText) findViewById(R.id.emergencyMessageField);
		
		emergencyMessageField.addTextChangedListener(new EmergencyMessageTextWatcher(characterCountLabel));
	}
	
	public void cancelSendEmergencyMessage(View view) {
		finish();
	}
	
	public void sendEmergencyMessage(View view) {
		if(isValidEmergencyMessage(emergencyMessageField.getText().toString())) {
			ContactList contactList = new ContactList();
			ContactDataSource dataSource = new ContactDataSource(this);
			dataSource.open();
			contactList.setContactList(dataSource.getContactList());


			dataSource.close();
			
			TextMessageSender textMessageSender = new TextMessageSender();
			textMessageSender.sendMessage(contactList, emergencyMessageField.getText().toString());
			
			finish();
		}
	}
	
	private boolean isValidEmergencyMessage(String message) {
		if(message.length() > 120) {
			emergencyMessageField.setError("Your message must be less then 120 characters!");
			return false;
		}
		
		return true;
	}
}