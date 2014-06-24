package com.riis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.controllers.ContactSpinnerItemClickListener;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;

public class NewContactActivity extends Activity
{
	private static final String FIRST_NAME_PATTERN =           "[A-Za-z]([a-z]+)";
	private static final String LAST_NAME_APOSTROPHE_PATTERN = "^[A-Za-z]+('[A-Za-z]+)";
	private static final String LAST_NAME_HYPHEN_PATTERN =     "^[A-Za-z]+(-[A-Za-z]+)*";
	private static final String LAST_NAME_SPACES_PATTERN =     "^[A-Za-z]+(\\s[A-Za-z]+)*";
	private static final String EMAIL_ADDRESS_PATTERN =        "[A-Za-z0-9-]+(\\.[a-z0-9-]+)*@[A-Za-z0-9]+(\\.[a-z]+)*(\\.[a-z]{2,4})";
	private static final String BASIC_PHONE_NUMBER_PATTERN =   "^\\d{10,10}";
	private static final String HYPHEN_PHONE_NUMBER_PATTERN =  "^\\d{3,3}-\\d{3,3}-\\d{4,4}";
	private static final String PARENTHESES_PHONE_NUMBER_PATTERN = "^\\(\\d{3,3}\\)\\s?\\d{3,3}-\\d{4,4}";
	
	private static final String FIRST_NAME_ERROR = "Your contact's first name may only contain characters and spaces";
	private static final String LAST_NAME_ERROR = "Your contact's name may only contain characters,spaces, apostrophes, or hyphens ";
	private static final String EMAIL_ADDRESS_ERROR = "Please enter a valid email address!";
	private static final String PHONE_NUMBER_ERROR = "Please enter a valid 10 digit phone number!";
	
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	private EditText firstFragmentEditField;
	private EditText secondFragmentEditField;
	private Spinner firstFragmentSpinner;
	private Spinner secondFragmentSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact_screen);
        
        firstNameEditField = (EditText) findViewById(R.id.first_name_editText);
		lastNameEditField = (EditText) findViewById(R.id.last_name_editText);
		
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        		R.array.contactInfoOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        FragmentManager manager = getFragmentManager();
        
        Fragment firstFragment = manager.findFragmentById(R.id.firstContactInfoFragment);
        firstFragmentSpinner = (Spinner) firstFragment.getView().findViewById(R.id.contactInfoSelection);
        TextView text = (TextView) firstFragment.getView().findViewById(R.id.contactInfoLabel);
        firstFragmentEditField = (EditText) firstFragment.getView().findViewById(R.id.contactInfoEditText);
        
        firstFragmentSpinner.setAdapter(adapter);
        firstFragmentSpinner.setOnItemSelectedListener(new ContactSpinnerItemClickListener(text, firstFragmentEditField));
        firstFragmentSpinner.setSelection(0);
        
        Fragment secondFragment = manager.findFragmentById(R.id.secondContactInfoFragment);
        secondFragmentSpinner = (Spinner) secondFragment.getView().findViewById(R.id.contactInfoSelection);
        TextView textView = (TextView) secondFragment.getView().findViewById(R.id.contactInfoLabel);
        secondFragmentEditField = (EditText) secondFragment.getView().findViewById(R.id.contactInfoEditText);
        
        secondFragmentSpinner.setAdapter(adapter);
        secondFragmentSpinner.setOnItemSelectedListener(new ContactSpinnerItemClickListener(textView, secondFragmentEditField));
        secondFragmentSpinner.setSelection(1);
    }
	
	public void cancelCreateContact(View view) 
	{
		finish();
	}
	
	public void saveCreateContact(View view) 
	{
		firstNameEditField.setError(null);
		lastNameEditField.setError(null);
		
		EditText emailAddressEditField;
		EditText phoneNumberEditField;
		
		if(firstFragmentSpinner.getSelectedItemPosition() == secondFragmentSpinner.getSelectedItemPosition())
		{
			secondFragmentSpinner.setSelection(1 - firstFragmentSpinner.getSelectedItemPosition());
		}
		
		if(firstFragmentSpinner.getSelectedItemPosition() == 0)
		{
			emailAddressEditField = firstFragmentEditField;
			phoneNumberEditField = secondFragmentEditField;
		}
		else
		{
			emailAddressEditField = secondFragmentEditField;
			phoneNumberEditField = firstFragmentEditField;
		}
		
		if (!isFirstNameValid(firstNameEditField.getText().toString())) 
			firstNameEditField.setError(FIRST_NAME_ERROR);
		else if (!isLastNameValid(lastNameEditField.getText().toString())) 
			lastNameEditField.setError(LAST_NAME_ERROR);
		else if (!isEmailValid(emailAddressEditField.getText().toString())) 
			firstFragmentEditField.setError(EMAIL_ADDRESS_ERROR);
		else if (!isPhoneValid(phoneNumberEditField.getText().toString())) 
			secondFragmentEditField.setError(PHONE_NUMBER_ERROR);
		else 
		{	        
	        Contact newContact = new Contact(this);
	        
			newContact.setFirstName(firstNameEditField.getText().toString());
			newContact.setLastName(lastNameEditField.getText().toString());
			newContact.setEmailAddress(emailAddressEditField.getText().toString());
			newContact.setPhoneNumber(phoneNumberEditField.getText().toString());
	        newContact.create();
	        
	        ContactList list = new ContactList(this);
	        list.setName("Everyone");
	        list.read();
	        
	        list.addContact(newContact);
	        list.update();
	        
	        ResponseMessage response = new ResponseMessage(this);
	        response.setTextMessageContents(" Are you OK?");
	        response.setPhoneNumber(newContact.getPhoneNumber());
	        response.setContactListId(1);
	        response.create();

	        callAlertDialog();
		}
	}
	
	private void callAlertDialog()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewContactActivity.this);// part of new pop up message

		alertDialogBuilder.setTitle("Contact Saved");
		alertDialogBuilder.setMessage("Your contact has been saved")
				   .setCancelable(false)
				   .setPositiveButton("OK", new DialogInterface.OnClickListener()
				   {
						public void onClick(DialogInterface dialog,int id) 
						{
							finish();
						}
				   });
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public boolean isFirstNameValid(String name)
	{
		if( name.matches(FIRST_NAME_PATTERN))
			return true;
		 
		return false;
	}
	
	public boolean isLastNameValid(String name)
	{
		if(name.matches(LAST_NAME_APOSTROPHE_PATTERN) | name.matches(LAST_NAME_HYPHEN_PATTERN)
				| name.matches(LAST_NAME_SPACES_PATTERN))
			return true;
		 
		return false;
	}
	
	public boolean isEmailValid(String email)
	{
		return email.matches(EMAIL_ADDRESS_PATTERN);
	}
	  
	public boolean isPhoneValid(String phone)
	{
		if(phone.matches(HYPHEN_PHONE_NUMBER_PATTERN) | phone.matches(BASIC_PHONE_NUMBER_PATTERN) 
				| phone.matches(PARENTHESES_PHONE_NUMBER_PATTERN)  )
			return true;
		 
		return false;
    }
}