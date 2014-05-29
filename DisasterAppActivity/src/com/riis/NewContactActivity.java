package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.riis.controllers.ContactDataSource;
import com.riis.models.Contact;

public class NewContactActivity extends Activity
{
	private ContactDataSource dataSource;
	
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	private EditText emailEditField;
	private EditText phoneEditField;
	
    /** Called when the new contact button is pressed. */
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.newcontact);
        
        dataSource = new ContactDataSource(this);
        dataSource.open();
        
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
		Contact newContact =  new Contact();
		newContact.setFirstName(firstNameEditField.getText().toString());
		newContact.setLastName(lastNameEditField.getText().toString());
		newContact.setEmailAddress(emailEditField.getText().toString());
		newContact.setPhoneNumber(phoneEditField.getText().toString());
		
		dataSource.createContact(newContact);
		dataSource.close();
		
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
	}
	
	
}
