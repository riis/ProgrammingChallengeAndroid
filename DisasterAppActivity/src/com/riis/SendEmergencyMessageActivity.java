package com.riis;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

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

}
