package com.riis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.riis.controllers.DialogSingleButtonClickListener;

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
		setContentView(R.layout.email_input_screen);
		
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
			
			callSubmitAlertDialog();
		}
	}
	
	public void skipToMain(View view)
	{
		callSkipAlertDialog();
	}
	
	private void callSkipAlertDialog()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Skip Email Setup");
		dialog.setMessage("Are you sure you do not want to enter an email address? Some features will be disabled!");
		dialog.setCancelable(false);
		dialog.setPositiveButton("Yes", new DialogSingleButtonClickListener(this, DisasterAppActivity.class));
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int id) 
				{
					dialog.cancel();
				}
			});
		dialog.show();
	}
	
	private void callSubmitAlertDialog()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Email Credentials Saved");
		dialog.setMessage("Your email credentials are saved!");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogSingleButtonClickListener(this, DisasterAppActivity.class));
		dialog.show();
	}
	
	private boolean isEmailValid(String email)
	{
		return email.matches(EMAIL_ADDRESS_PATTERN);
	}
}