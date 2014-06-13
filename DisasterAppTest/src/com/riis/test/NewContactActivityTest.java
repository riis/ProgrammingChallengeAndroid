package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class NewContactActivityTest extends ActivityInstrumentationTestCase2<NewContactActivity>
{
	private Button cancelButton;
	private Button saveButton;
	
	private Context context;
	
	private TextView firstNameText;
	private TextView lastNameText;
	private TextView emailText;
	private TextView phoneText;
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	private EditText emailEditField;
	private EditText phoneEditField;
	private NewContactActivity newContactActivity;
	
	public NewContactActivityTest()
	{
		super(NewContactActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		newContactActivity = getActivity();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
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
	
	public void testCreateContact() 
	{
		Contact newContact = new Contact(context);
		newContact.setFirstName("Bob");
		newContact.setLastName("Jones");
		newContact.setEmailAddress("bjones@example.com");
		newContact.setPhoneNumber("5555555555");
		
		newContact.create();
		
		ContactList contactList = new ContactList(context);
		contactList.read();
		Contact output = contactList.getContact(contactList.size() - 1);

		newContact.delete();
		
		assertEquals(output.getFirstName(), newContact.getFirstName());
		assertEquals(output.getLastName(), newContact.getLastName());
		assertEquals(output.getEmailAddress(), newContact.getEmailAddress());
		assertEquals(output.getPhoneNumber(), newContact.getPhoneNumber());
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
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
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
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
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
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertEquals("bobby@yahoo.com", emailEditField.getText().toString());
	}
	
	public void testPhoneChangeTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				phoneEditField.setText("(586)000-1234", TextView.BufferType.EDITABLE);
			}
		});
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertEquals("(586)000-1234", phoneEditField.getText().toString());
	}
	
	public void testValidFirstName()
	{
		assertFalse(newContactActivity.isFirstNameValid("alice@yahoo.com"));
		assertFalse(newContactActivity.isFirstNameValid("1202"));
		assertFalse(newContactActivity.isFirstNameValid("ghangis'khan"));
		assertFalse(newContactActivity.isFirstNameValid("williams-Berr"));
		assertFalse(newContactActivity.isFirstNameValid("ROBERT"));
		assertTrue(newContactActivity.isFirstNameValid("Allie"));
		assertTrue(newContactActivity.isFirstNameValid("stacy"));
		assertTrue(newContactActivity.isFirstNameValid("Williams"));
	}
	
	public void testValidLastName()
	{
		assertFalse(newContactActivity.isLastNameValid("alice@yahoo.com"));
		assertFalse(newContactActivity.isLastNameValid("1202"));
		assertTrue(newContactActivity.isLastNameValid("ROBERT"));
		assertTrue(newContactActivity.isLastNameValid("Allie"));
		assertTrue(newContactActivity.isLastNameValid("stacy"));
		assertTrue(newContactActivity.isLastNameValid("ghangis'khan"));
		assertTrue(newContactActivity.isLastNameValid("williams-Berr"));
		assertTrue(newContactActivity.isLastNameValid("Williams Berr"));
	}

	public void testValidEmail()
	{
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

	public void testSaveButtonIntent() {
//		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
//		monitor.waitForActivityWithTimeout(1000);
//
//		TouchUtils.clickView(this, saveButton);
//		
//		monitor.waitForActivityWithTimeout(1000);
//		assertEquals(1, monitor.getHits());
//		
//		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContactButtonIntent() {
//		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
//		
//		TouchUtils.clickView(this, cancelButton);
//		
//		monitor.waitForActivityWithTimeout(5000);
//		assertEquals(1, monitor.getHits());
//		
//		getInstrumentation().removeMonitor(monitor);
	}
}