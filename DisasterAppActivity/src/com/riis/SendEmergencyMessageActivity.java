package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.controllers.ContactDataSource;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.EmergencyMessageTextWatcher;
import com.riis.models.TextMessageSender;

import dagger.ObjectGraph;

public class SendEmergencyMessageActivity extends Activity {
	
	private TextView characterCountLabel;
	private EditText emergencyMessageField;
	@Inject ContactList contactList;
	@Inject TextMessageSender textMessageSender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getObjectGraph();
		objectGraph.inject(this);
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
			ContactDataSource dataSource = new ContactDataSource(this);
			dataSource.open();
			contactList.setContactList(dataSource.getContactList());
			
			for(int i = 0; i < contactList.size(); i++) {
				Contact contact = contactList.getContact(i);
				contact.updateMessageSentTimeStamp();
				dataSource.updateContact(contact);
			}

			dataSource.close();
			
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