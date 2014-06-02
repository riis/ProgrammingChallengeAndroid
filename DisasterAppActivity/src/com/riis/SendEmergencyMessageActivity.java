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
import com.riis.models.SmsMessageSender;

public class SendEmergencyMessageActivity extends Activity {
	
	private ContactDataSource dataSource;
	private TextView characterCountLabel;
	private EditText emergencyMessageField;
	
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
		finish();
	}
	
	public void sendEmergencyMessage(View view) {
		if(isValidEmergencyMessage(emergencyMessageField.getText().toString())) {
			ContactList contactList = new ContactList();
			contactList.setContactList(dataSource.getContactList());
			SmsMessageSender smsSenderModel = new SmsMessageSender();
			//String messageContent = smsSenderModel.prepareMessageToSend(emergencyMessageField.getText().toString());
		

			dataSource.close();
			finish();
		}
	}

	private String prepareMessageToSend(String message) {
		message += " Are you OK?";
		return message;
	}
	
	private boolean isValidEmergencyMessage(String message) {
		if(message.length() > 120) {
			emergencyMessageField.setError("Your message must be less then 120 characters!");
			return false;
		}
		
		return true;
	}

}
