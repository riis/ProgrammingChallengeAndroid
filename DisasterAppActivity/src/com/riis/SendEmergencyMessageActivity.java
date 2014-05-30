package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.models.*;
import com.riis.controllers.*;


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
	}
	
	public void cancelSendEmergencyMessage(View view) {
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
	}
	

	public void sendEmergencyMessage( String message, String phoneNumber) {
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
		//sms.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
		
		
		
		
	//	Intent intent = new Intent(this, DisasterAppActivity.class);
	//	startActivity(intent);
}
	private String prepareMessageToSend(String message) {
		message += "Are you OK?";
		return message;
	}
	
	private boolean isValiedEmergencyMessage(String message) {
		if(message.length() > 120) {
			emergencyMessageField.setError("Your message must be less then 120 characters!");
			return false;
		}
		
		return true;
	}
}
