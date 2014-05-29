package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewContactActivity extends Activity
{
	
	private Button cancelButton;
	private Button saveButton;
	private TextView firstNameText;
	private TextView lastNameText;
	private TextView emailText;
	private TextView phoneText;
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	private EditText emailEditField;
	private EditText phoneEditField;
	private String empty_field;
	
    /** Called when the new contact button is pressed. */
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.newcontact);
        empty_field = "Field cannot be blank";
        cancelButton = (Button) findViewById(R.id.Cancel_button);
		saveButton = (Button) findViewById(R.id.Save_button);
		firstNameText = (TextView)findViewById(R.id.First_Name);
		lastNameText = (TextView) findViewById(R.id.Last_Name);
		emailText = (TextView) findViewById(R.id.Email_Address);
		phoneText = (TextView) findViewById(R.id.Phone_Number);
		firstNameEditField = (EditText) findViewById(R.id.first_name_editText);
		lastNameEditField = (EditText) findViewById(R.id.last_name_editText);
		emailEditField = (EditText) findViewById(R.id.email_address_editText);
		phoneEditField = (EditText) findViewById(R.id.phone_number_editText);
        
    }
	
	public void cancelCreateContact(View view) {
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
	}
	
	public void saveCreateContact(View view) {
		
		if (firstNameEditField.getText().toString().trim().equalsIgnoreCase("")) //if blank
		{
			firstNameEditField.setError(empty_field);
		}
		if (lastNameEditField.getText().toString().trim().equalsIgnoreCase(""))
		{
			lastNameEditField.setError(empty_field);
		}
		if (emailEditField.getText().toString().trim().equalsIgnoreCase(""))
		{
			emailEditField.setError(empty_field);
		}
		if (phoneEditField.getText().toString().trim().equalsIgnoreCase(""))
		{
			phoneEditField.setError(empty_field);
		}
		
		if (phoneEditField.getText().toString().trim().equalsIgnoreCase("") | emailEditField.getText().toString().trim().equalsIgnoreCase("") |
				lastNameEditField.getText().toString().trim().equalsIgnoreCase("") | firstNameEditField.getText().toString().trim().equalsIgnoreCase(""))
		{
			
		}
		else 
		{
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
		}
		
	}
	
	
	
	
}
