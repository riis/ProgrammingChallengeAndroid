package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.controllers.ContactDataSource;
import com.riis.models.ContactList;

public class SendEmergencyMessageActivity extends Activity {
	
	private ContactDataSource dataSource;
	
	private TextView characterCountLabel;
	private EditText emergencyMessageField;
//<<<<<<< HEAD
//=======
	private String theMessage;
	private String[] theListOfPhoneNumbers;
//>>>>>>> e9beff67f594e562795e3376d4649fbd937f9902
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_screen);
		
		characterCountLabel = (TextView) findViewById(R.id.characterCountLabel);
		characterCountLabel.setText(""+ 120);
		emergencyMessageField = (EditText) findViewById(R.id.emergencyMessageField);
		
		final TextWatcher characterCount = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				int length = 120 - arg0.length();
				characterCountLabel.setText(String.valueOf(""+ length));
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		};
		
		emergencyMessageField.addTextChangedListener(characterCount);
		
		dataSource = new ContactDataSource(this);
		dataSource.open();
	}
	
	public void cancelSendEmergencyMessage(View view) {
		dataSource.close();
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
	}
	
//<<<<<<< HEAD
	public void sendEmergencyMessage(View view) {
		if(isValidEmergencyMessage(emergencyMessageField.getText().toString())) {
			ContactList contactList = new ContactList();
			contactList.setContactList(dataSource.getContactList());
			
			String messageContent = prepareMessageToSend(emergencyMessageField.getText().toString());
			
			SmsManager sms = SmsManager.getDefault();
			
			for(int i = 0; i < contactList.size(); i++) {
				sms.sendTextMessage(contactList.getContact(i).getPhoneNumber(), null, messageContent, null, null);
			}
			
			
			dataSource.close();
			Intent intent = new Intent(this, DisasterAppActivity.class);
			startActivity(intent);
		}
	}
	
//=======

//	public void sendEmergencyMessage( String[] phoneNumbers) {
//		theMessage=emergencyMessageField.getText().toString();
//		theListOfPhoneNumbers = new String[phoneNumbers.length];
//		SmsManager sms = SmsManager.getDefault();
// //----send the message to everyone in the list-----//
//		for(int i = 0; i<phoneNumbers.length;i++)
//		{
//			sms.sendTextMessage(phoneNumbers[i], null, theMessage, null, null);
//			theListOfPhoneNumbers[i]=phoneNumbers[i];
//		}
//		
//	//	Intent intent = new Intent(this, DisasterAppActivity.class);
//	//	startActivity(intent);
//}
//>>>>>>> e9beff67f594e562795e3376d4649fbd937f9902
	private String prepareMessageToSend(String message) {
		message += "Are you OK?";
		return message;
	}
	
	private boolean isValidEmergencyMessage(String message) {
		if(message.length() > 120) {
			emergencyMessageField.setError("Your message must be less then 120 characters!");
			return false;
		}
		
		return true;
	}
//<<<<<<< HEAD
//=======
	
	public String getMessageBack()
	{
		return theMessage;
	}
	
	public String getFirstPhoneNumberBack()
	{
		return theListOfPhoneNumbers[0];
	}
	
	public String getLastPhoneNumberBack()
	{
		return theListOfPhoneNumbers[theListOfPhoneNumbers.length-1];
	}
//>>>>>>> e9beff67f594e562795e3376d4649fbd937f9902
}
