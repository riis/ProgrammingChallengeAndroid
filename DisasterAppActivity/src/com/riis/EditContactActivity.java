package com.riis;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.controllers.ContactSpinnerItemClickListener;
import com.riis.models.Contact;

public class EditContactActivity extends Activity
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
        setContentView(R.layout.edit_contact_screen);
        
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
        
//      firstNameEditField.setText(contact.getFirstName());
//		lastNameEditField.setText(contact.getLastName());
//		firstFragmentEditField.setText(contact.getEmailAddress());
//		secondFragmentEditField.setText(contact.getPhoneNumber());
        
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
