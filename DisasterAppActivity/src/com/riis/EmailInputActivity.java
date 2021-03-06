package com.riis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.riis.controllers.DialogSingleButtonClickListener;
import com.riis.models.ContactList;

public class EmailInputActivity extends Activity
{
	private static final String EMAIL_ADDRESS_PATTERN = "[A-Za-z0-9-]+(\\.[a-z0-9-]+)*@[A-Za-z0-9]+(\\.[a-z]+)*(\\.[a-z]{2,4})";
	private static final String EMAIL_ADDRESS_ERROR = "Please enter a valid email address!";
	
	private boolean setUp = false;
	private boolean noContacts;
	private Button skipButton;
	private EditText emailField;
	private EditText passwordField;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_input_screen);
		
		Bundle extras = getIntent().getExtras();
		ContactList cList = new ContactList(this);
		cList.readAllContacts();
		
		if(cList.size()==0)
		{
			noContacts = true;
		}
		else
		{
			noContacts = false;
		}
		skipButton = (Button) findViewById(R.id.skipButton);
		emailField = (EditText) findViewById(R.id.emailAddressInput);
		passwordField = (EditText) findViewById(R.id.passwordInput);
		
		if(extras != null)
		{
			setUp = extras.getBoolean("setUpEmail");
			skipButton.setText("Cancel");
		}
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
		if(noContacts)
		{
			dialog.setPositiveButton("Yes", new DialogSingleButtonClickListener(this, AboutActivity.class));
		}
		else
		{
			if(setUp)
			{
				dialog.setPositiveButton("Yes", new DialogSingleButtonClickListener(this));
			}
			else
			{
				dialog.setPositiveButton("Yes", new DialogSingleButtonClickListener(this, DisasterAppActivity.class));
			}
		}
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
		if(noContacts)
		{
			dialog.setPositiveButton("Yes", new DialogSingleButtonClickListener(this, AboutActivity.class));
		}
		else
		{
			if(setUp)
			{
				dialog.setPositiveButton("OK", new DialogSingleButtonClickListener(this));
			}
			else
			{
				dialog.setPositiveButton("OK", new DialogSingleButtonClickListener(this, DisasterAppActivity.class));
			}
		}
		dialog.show();
	}
	
	private boolean isEmailValid(String email)
	{
		return email.matches(EMAIL_ADDRESS_PATTERN);
	}
	
	@Override
	protected void onResume() 
	{
	   super.onResume();
	   onCreate(null);
	}
}