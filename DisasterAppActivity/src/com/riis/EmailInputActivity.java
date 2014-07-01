package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EmailInputActivity extends Activity
{
	private static final String EMAIL_ADDRESS_PATTERN = "[A-Za-z0-9-]+(\\.[a-z0-9-]+)*@[A-Za-z0-9]+(\\.[a-z]+)*(\\.[a-z]{2,4})";
	private static final String EMAIL_ADDRESS_ERROR = "Please enter a valid email address!";
	
	private EditText emailField;
	private EditText passwordField;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_data_screen);
		
		emailField = (EditText) findViewById(R.id.emailAddressInput);
		passwordField = (EditText) findViewById(R.id.passwordInput);
	}
	
	public void submitEmailAndPassword(View view)
	{
		if (!isEmailValid(emailField.getText().toString())) 
			emailField.setError(EMAIL_ADDRESS_ERROR);
		else if (passwordField.getText().toString().equals("")) 
			passwordField.setError("Please enter your password!");
		else 
		{
			SharedPreferences prefs = getSharedPreferences("emailData", 1);
			Editor edit = prefs.edit();
			edit.putString("email", emailField.getText().toString());
			edit.putString("password", passwordField.getText().toString());
			edit.commit();
			
			Intent i = new Intent(this, DisasterAppActivity.class);
			startActivity(i);
		}
	}
	
	public void skipToMain(View view)
	{
		Intent i = new Intent(this, DisasterAppActivity.class);
		startActivity(i);
	}
	
	private boolean isEmailValid(String email)
	{
		return email.matches(EMAIL_ADDRESS_PATTERN);
	}
}