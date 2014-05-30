package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;

public class DisasterAppNewContactTest extends ActivityInstrumentationTestCase2<NewContactActivity>{
	
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
	private NewContactActivity newContactActivity;
		
	
	public DisasterAppNewContactTest() {
		super(NewContactActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		newContactActivity = getActivity();
		cancelButton = (Button) newContactActivity.findViewById(R.id.Cancel_button);
		saveButton = (Button) newContactActivity.findViewById(R.id.Save_button);
		firstNameText = (TextView)newContactActivity.findViewById(R.id.First_Name);
		lastNameText = (TextView) newContactActivity.findViewById(R.id.Last_Name);
		emailText = (TextView) newContactActivity.findViewById(R.id.Email_Address);
		phoneText = (TextView) newContactActivity.findViewById(R.id.Phone_Number);
		firstNameEditField = (EditText) newContactActivity.findViewById(R.id.first_name_editText);
		lastNameEditField = (EditText) newContactActivity.findViewById(R.id.last_name_editText);
		emailEditField = (EditText) newContactActivity.findViewById(R.id.email_address_editText);
		phoneEditField = (EditText) newContactActivity.findViewById(R.id.phone_number_editText);
	}
		
	public void testCancelButtonExists() 
	{
		assertNotNull(cancelButton);
	}
   
	public void testSaveButtonExists() 
	{
		assertNotNull(saveButton);
	}
	
	public void testFirstNameLabel()
	{
		assertNotNull(firstNameText);
	}
	
	public void testLastNameLabel()
    {
		assertNotNull(lastNameText);
    }
	
	public void testEmailLabel()
	{
		assertNotNull(emailText);
	}
	
	public void testPhoneLabel()
	{
		assertNotNull(phoneText);
	}
	
	public void testFirstNameChangeTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
			firstNameEditField.setText("Bob", TextView.BufferType.EDITABLE);
		}
		
	});
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	assertEquals("Bob", firstNameEditField.getText().toString());
}
	
	public void testLastNameChangeTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
			lastNameEditField.setText("Wszedybyl", TextView.BufferType.EDITABLE);
			}
			
		});
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Wszedybyl", lastNameEditField.getText().toString());
	}
	
	public void testEmailChangeTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
			emailEditField.setText("bobby@yahoo.com", TextView.BufferType.EDITABLE);
			}
			
		});
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("bobby@yahoo.com", emailEditField.getText().toString());
	}
	
	public void testphoneChangeTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
			phoneEditField.setText("(586)000-1234", TextView.BufferType.EDITABLE);
			}
			
		});
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("(586)000-1234", phoneEditField.getText().toString());
	}
	
	public void testValidName()
	{
		assertFalse(newContactActivity.isNameValid("alice@yahoo.com"));
		assertFalse(newContactActivity.isNameValid("1202"));
		assertTrue(newContactActivity.isNameValid("ROBERT"));
		assertTrue(newContactActivity.isNameValid("Allie"));
		assertTrue(newContactActivity.isNameValid("stacy"));
		assertTrue(newContactActivity.isNameValid("ghangis'khan"));
		assertTrue(newContactActivity.isNameValid("williams-Berr"));
		assertTrue(newContactActivity.isNameValid("Williams Berr"));
	}
	
	public void testValidEmail()
	{
		//android util version
		assertTrue(newContactActivity.isEmailValid("alice@yahoo.com"));
		assertTrue(newContactActivity.isEmailValid("Robert24@gmail.edu"));
		assertFalse(newContactActivity.isEmailValid("Robert24@jupiterjupiter"));
		assertFalse(newContactActivity.isEmailValid("SussieQ!@yahoo.com"));
		assertFalse(newContactActivity.isEmailValid("alice"));
		assertFalse(newContactActivity.isEmailValid("@yahoo.com"));
	}
	
	public void testValidPhone()
	{
		assertTrue(newContactActivity.isPhoneValid("5550001234"));
		assertTrue(newContactActivity.isPhoneValid("101-202-6789"));
		assertTrue(newContactActivity.isPhoneValid("(586)202-6789"));
		assertFalse(newContactActivity.isPhoneValid("101-202"));
		assertFalse(newContactActivity.isPhoneValid("alice"));
		assertFalse(newContactActivity.isPhoneValid("123"));
		assertFalse(newContactActivity.isPhoneValid("0001234567a"));
	}
	
/*	public void testEmptyFNField()			///Error are not testing properly///
	{
		// test the error message is not initially there
		assertNull(firstNameEditField.getError());
	
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstNameEditField.setText("");
			}
			
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// find button and click it
		TouchUtils.clickView(this, saveButton);
		
		assertNotNull(firstNameEditField.getError());

	}
	
	public void testEmptyLNField()
	{
		// test the error message is not initially there
		assertNull(lastNameEditField.getError());
	
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				lastNameEditField.setText("");
			}
			
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// find button and click it
		TouchUtils.clickView(this, saveButton);
		
		assertNotNull(lastNameEditField.getError());

	}
	public void testEmptyEmailField()
	{
		// test the error message is not initially there
		assertNull(emailEditField.getError());
	
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				emailEditField.setText("");
			}
			
		});
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// find button and click it
		TouchUtils.clickView(this, saveButton);
		
		assertNotNull(emailEditField.getError());

	}
	
	public void testEmptyPhoneField()
	{
		// test the error message is not initially there
		assertNull(phoneEditField.getError());
		
		// find button and click it
		TouchUtils.clickView(this, saveButton);
		
		assertEquals(getInstrumentation().getTargetContext().getResources().getString(
				R.string.empty_field), phoneEditField.getError().toString());
		
	}
	*/

	public void testSaveButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, saveButton);
		
		monitor.waitForActivityWithTimeout(1000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContactButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, cancelButton);
		
		monitor.waitForActivityWithTimeout(5000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
}