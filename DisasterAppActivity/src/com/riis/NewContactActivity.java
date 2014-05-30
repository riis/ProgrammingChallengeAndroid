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
	private static final String FIRST_NAME_PATTERN = "[A-Za-z]([a-z]+)";
	private static final String LAST_NAME_APOSTROPHE_PATTERN = "^[A-Za-z]+('[A-Za-z]+)";
	private static final String LAST_NAME_HYPHEN_PATTERN =     "^[A-Za-z]+(-[A-Za-z]+)*";
	private static final String LAST_NAME_SPACES_PATTERN =     "^[A-Za-z]+(\\s[A-Za-z]+)*";
	private static final String EMAIL_ADDRESS_PATTERN = "[A-Za-z0-9-]+(\\.[a-z0-9-]+)*@[A-Za-z0-9]+(\\.[a-z]+)*(\\.[a-z]{2,4})";
	private static final String BASIC_PHONE_NUMBER_PATTERN = "^\\d{10,10}";
	private static final String HYPHEN_PHONE_NUMBER_PATTERN = "^\\d{3,3}-\\d{3,3}-\\d{4,4}";
	private static final String PARENTHESES_PHONE_NUMBER_PATTERN = "^\\(\\d{3,3}\\)\\s?\\d{3,3}-\\d{4,4}";
	
	private ContactDataSource dataSource;
	
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	private EditText emailEditField;
	private EditText phoneEditField;
	private String empty_field;
	private String name_error_field;
	private String email_error_field;
	private String phone_error_field;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.newcontact);

        empty_field = "Field cannot be blank";
        name_error_field ="May only contain characters and spaces";
        email_error_field ="Invalid email";
        phone_error_field ="Try 10 digits including areacode";


        dataSource = new ContactDataSource(this);
        dataSource.open();
        
		firstNameEditField = (EditText) findViewById(R.id.first_name_editText);
		lastNameEditField = (EditText) findViewById(R.id.last_name_editText);
		emailEditField = (EditText) findViewById(R.id.email_address_editText);
		phoneEditField = (EditText) findViewById(R.id.phone_number_editText);
    }
	
	public void cancelCreateContact(View view) {
		dataSource.close();
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
	}
	
	public void saveCreateContact(View view) {

////// check if any fields are blank/////////////////		
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
		
////// check if any fields are invalid by any means/////////////////		
		if (isNameValid(firstNameEditField.getText().toString())) 
		{
			firstNameEditField.setError(name_error_field);
		}
		if (isNameValid(lastNameEditField.getText().toString())) 
		{
			lastNameEditField.setError(name_error_field);
		}
		if (isEmailValid(emailEditField.getText().toString())) 
		{
			emailEditField.setError(email_error_field);
		}
		if (isPhoneValid(phoneEditField.getText().toString())) 
		{
			phoneEditField.setError(phone_error_field);
		}
		
		
		if (phoneEditField.getText().toString().trim().equalsIgnoreCase("") | emailEditField.getText().toString().trim().equalsIgnoreCase("") |
				lastNameEditField.getText().toString().trim().equalsIgnoreCase("") | firstNameEditField.getText().toString().trim().equalsIgnoreCase(""))
		{
			
		}
		else 
		{
			dataSource.close();
			Intent intent = new Intent(this, DisasterAppActivity.class);
			startActivity(intent);
		}
		
		if(!isEmailValid(emailEditField.getText().toString()))
		{
			
		}
		
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


	private boolean isNameValid(String name)
	 {
		 if(name.matches(LAST_NAME_APOSTROPHE_PATTERN) | name.matches(FIRST_NAME_PATTERN) 
				 | name.matches(LAST_NAME_HYPHEN_PATTERN)  | name.matches(LAST_NAME_SPACES_PATTERN)  )
		 { return true;}
		 
		 return false;
	 } 
	
	 private boolean isEmailValid(String email)
	 {
		 return email.matches(EMAIL_ADDRESS_PATTERN);
	 }
	  
	 private boolean isPhoneValid(String phone)
	 {
		 if(phone.matches(HYPHEN_PHONE_NUMBER_PATTERN) | phone.matches(BASIC_PHONE_NUMBER_PATTERN) 
				 | phone.matches(PARENTHESES_PHONE_NUMBER_PATTERN)  )
		 { return true;}
		 
		 return false;
     }
	 
	
	
	
}
