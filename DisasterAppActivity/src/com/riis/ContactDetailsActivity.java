package com.riis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.controllers.ContactSpinnerItemClickListener;
import com.riis.models.Contact;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;

public class ContactDetailsActivity extends Activity

{
	protected static final String FIRST_NAME_PATTERN =           "[A-Za-z]([a-z]+)";
	protected static final String LAST_NAME_APOSTROPHE_PATTERN = "^[A-Za-z]+('[A-Za-z]+)";
	protected static final String LAST_NAME_HYPHEN_PATTERN =     "^[A-Za-z]+(-[A-Za-z]+)*";
	protected static final String LAST_NAME_SPACES_PATTERN =     "^[A-Za-z]+(\\s[A-Za-z]+)*";
	protected static final String EMAIL_ADDRESS_PATTERN =        "[A-Za-z0-9-]+(\\.[a-z0-9-]+)*@[A-Za-z0-9]+(\\.[a-z]+)*(\\.[a-z]{2,4})";
	protected static final String BASIC_PHONE_NUMBER_PATTERN =   "^\\d{10,10}";
	protected static final String HYPHEN_PHONE_NUMBER_PATTERN =  "^\\d{3,3}-\\d{3,3}-\\d{4,4}";
	protected static final String PARENTHESES_PHONE_NUMBER_PATTERN = "^\\(\\d{3,3}\\)\\s?\\d{3,3}-\\d{4,4}";
	
	protected static final String FIRST_NAME_ERROR = "Your contact's first name may only contain characters and spaces";
	protected static final String LAST_NAME_ERROR = "Your contact's name may only contain characters,spaces, apostrophes, or hyphens ";
	protected static final String EMAIL_ADDRESS_ERROR = "Please enter a valid email address!";
	protected static final String PHONE_NUMBER_ERROR = "Please enter a valid 10 digit phone number!";
	
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	private EditText firstFragmentEditField;
	private EditText secondFragmentEditField;
	private Spinner firstFragmentSpinner;
	private Spinner secondFragmentSpinner;
	private Contact existingContact;
	private EditText emailAddressEditField;
	private EditText phoneNumberEditField;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		
        if(extras != null)
        {
        	setContentView(R.layout.edit_contact_screen);
        }
        else
        {
        	setContentView(R.layout.new_contact_screen);
        }
        
        firstNameEditField = (EditText) findViewById(R.id.firstNameEditText);
		lastNameEditField = (EditText) findViewById(R.id.lastNameEditText);
		
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
        
        
        if(extras != null)
        {
        	existingContact = new Contact(getApplicationContext());
        	existingContact.read(extras.getLong("id"));
	        
			firstNameEditField.setText(existingContact.getFirstName());
			lastNameEditField.setText(existingContact.getLastName());
			firstFragmentEditField.setText(existingContact.getEmailAddress());
			secondFragmentEditField.setText(existingContact.getPhoneNumber());
        }
        
    }
	
	public void cancelCreateContact(View view) 
	{
		finish();
	}
	
	public void deleteContact(View view) 
	{
        callDeleteAlertDialog(); 
        
	}
	
	public void saveCreateContact(View view) 
	{
		saveOrUpdateContact(1);
	}

	public void updateContact(View view) 
	{
		saveOrUpdateContact(0);
	}
	
	protected void callDeleteAlertDialog()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Contact Deleted");
		alertDialogBuilder.setMessage("Are you sure you want to delete this contact?")
				   .setCancelable(true)
				   .setPositiveButton("Yes", new DialogInterface.OnClickListener()
				   {
						public void onClick(DialogInterface dialog,int id) 
						{
//							ContactReference ref = new ContactReference(this);
//					        ref.setContactId(existingContact.getId());
//							ResponseMessage response = new ResponseMessage(this);
//							response.setReferenceId(ref.getId());
//							ref.delete();
//						    response.delete();
						    existingContact.delete();
							finish();
						}
				   })
				   .setNegativeButton("No", new DialogInterface.OnClickListener()
				   {
						public void onClick(DialogInterface dialog,int id) 
						{
							dialog.cancel();
						}
				   });
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	
	protected void callSavedAlertDialog()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
	
	protected void callUpdatedAlertDialog()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Contact Updated");
		alertDialogBuilder.setMessage("Your contact has been updated")
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
	
	private void saveOrUpdateContact(int savedOrUpdated) 
	{
		firstNameEditField.setError(null);
		lastNameEditField.setError(null);
		
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
			if(savedOrUpdated==1)
			{
		        Contact newContact = new Contact(this);
				newContact.setFirstName(firstNameEditField.getText().toString());
				newContact.setLastName(lastNameEditField.getText().toString());
				newContact.setEmailAddress(emailAddressEditField.getText().toString());
				newContact.setPhoneNumber(phoneNumberEditField.getText().toString().replaceAll("[^\\d.]", ""));
		        newContact.create();
		        
		        ContactReference ref = new ContactReference(this);
		        ref.setContactListId(1L);
		        ref.setContactId(newContact.getId());
		        ref.create();
		        
		        ResponseMessage response = new ResponseMessage(this);
		        response.setReferenceId(ref.getId());
		        response.setMessageContents(" Are you OK?");
		        response.create();
	
		        callSavedAlertDialog();
			}
			else if(savedOrUpdated==0)
			{
				existingContact.setFirstName(firstNameEditField.getText().toString());
				existingContact.setLastName(lastNameEditField.getText().toString());
				existingContact.setEmailAddress(emailAddressEditField.getText().toString());
				existingContact.setPhoneNumber(phoneNumberEditField.getText().toString());
				existingContact.update();
		        
		        callUpdatedAlertDialog();
			}
		}
		
	}
}
