package com.riis.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.NewContactActivity;
import com.riis.R;

public class NewContactActivityTest extends ActivityInstrumentationTestCase2<NewContactActivity>
{
	private Button cancelButton;
	private Button saveButton;
	
	private Context context;
	
	private TextView firstNameText;
	private TextView lastNameText;
	private TextView firstFragmentText;
	private TextView secondFragmentText;
	
	private EditText firstNameEditField;
	private EditText lastNameEditField;
	
	private EditText firstFragmentEditField;
	private EditText secondFragmentEditField;
	
	private Spinner firstFragmentSpinner;
	private Spinner secondFragmentSpinner;
	
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
		
		cancelButton = (Button) newContactActivity.findViewById(R.id.cancelCreateContactButton);
		saveButton = (Button) newContactActivity.findViewById(R.id.saveContactButton);
		firstNameText = (TextView)newContactActivity.findViewById(R.id.firstNameLabel);
		lastNameText = (TextView) newContactActivity.findViewById(R.id.lastNameLabel);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
        		R.array.contactInfoOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        FragmentManager manager = newContactActivity.getFragmentManager();
        
        Fragment firstFragment = manager.findFragmentById(R.id.firstContactInfoFragment);
        firstFragmentSpinner = (Spinner) firstFragment.getView().findViewById(R.id.contactInfoSelection);
        firstFragmentText = (TextView) firstFragment.getView().findViewById(R.id.contactInfoLabel);
        firstFragmentEditField = (EditText) firstFragment.getView().findViewById(R.id.contactInfoEditText);
        
        Fragment secondFragment = manager.findFragmentById(R.id.secondContactInfoFragment);
        secondFragmentSpinner = (Spinner) secondFragment.getView().findViewById(R.id.contactInfoSelection);
        secondFragmentText = (TextView) secondFragment.getView().findViewById(R.id.contactInfoLabel);
        secondFragmentEditField = (EditText) secondFragment.getView().findViewById(R.id.contactInfoEditText);
        
		firstNameEditField = (EditText) newContactActivity.findViewById(R.id.firstNameEditText);
		lastNameEditField = (EditText) newContactActivity.findViewById(R.id.lastNameEditText);
	}
	
//	public void testCreateContact() 
//	{
//		Contact newContact = new Contact(context);
//		newContact.setFirstName("Bob");
//		newContact.setLastName("Jones");
//		newContact.setEmailAddress("bjones@example.com");
//		newContact.setPhoneNumber("5555555555");
//		
//		newContact.create();
//		
//		ContactList contactList = new ContactList(context);
//		contactList.readAllContacts();
//		Contact output = contactList.getContact(contactList.size() - 1);
//
//		newContact.delete();
//		
//		assertEquals(output.getFirstName(), newContact.getFirstName());
//		assertEquals(output.getLastName(), newContact.getLastName());
//		assertEquals(output.getEmailAddress(), newContact.getEmailAddress());
//		assertEquals(output.getPhoneNumber(), newContact.getPhoneNumber());
//	}
//	
//	public void testDeleteContact()
//	{
//		Contact newContact = new Contact(context);
//		newContact.setFirstName("Bob");
//		newContact.setLastName("Jones");
//		newContact.setEmailAddress("bjones@example.com");
//		newContact.setPhoneNumber("5555555555");
//		newContact.create();
//		assertTrue(newContact.delete());
//	}
//	
//	public void testReadContact()
//	{
//		Contact newContact = new Contact(context);
//		newContact.setFirstName("Bob");
//		newContact.setLastName("Jones");
//		newContact.setEmailAddress("bjones@example.com");
//		newContact.setPhoneNumber("5555555555");
//		newContact.setPingCount(2);
//		
//		newContact.create();
//		
//		Contact output = new Contact(context);
//		output.setEmailAddress("bjones@example.com");
//		
//		assertTrue(output.read());
//		
//		newContact.delete();
//	}
//	
//	public void testUpdateContact()
//	{
//		Contact newContact = new Contact(context);
//		newContact.setFirstName("Bob");
//		newContact.setLastName("Jones");
//		newContact.setEmailAddress("bjones@example.com");
//		newContact.setPhoneNumber("5555555555");
//		newContact.setPingCount(2);
//		newContact.create();
//		
//		newContact.setLastName("Smith");
//		newContact.update();
//		
//		Contact output = new Contact(context);
//		output.setEmailAddress("bjones@example.com");
//		output.read();
//		
//		newContact.delete();
//		
//		assertEquals(newContact.getLastName(), output.getLastName());
//	}
		
 	public void testCancelButtonExists() 
	{
		assertNotNull(cancelButton);
	}
   
	public void testSaveButtonExists() 
	{
		assertNotNull(saveButton);
	}
	
	public void testFirstNameLabelExists()
	{
		assertNotNull(firstNameText);
	}
	
	public void testLastNameLabelExists()
    {
		assertNotNull(lastNameText);
    }
	
	public void testFirstNameEditTextExists()
	{
		assertNotNull(firstNameEditField);
	}
	
	public void testLastNameEditTextExists()
	{
		assertNotNull(lastNameEditField);
	}
	
	public void testFirstFragmentSpinnerExists()
	{
		assertNotNull(firstFragmentSpinner);
	}
	
	public void testSecondFragmentSpinnerExists()
	{
		assertNotNull(secondFragmentSpinner);
	}
	
	public void testFirstFragmentLabelExists()
	{
		assertNotNull(firstFragmentText);
	}
	
	public void testSecondFragmentLabelExists()
	{
		assertNotNull(secondFragmentText);
	}
	
	public void testFirstFragmentEditTextExists()
	{
		assertNotNull(firstFragmentEditField);
	}
	
	public void testSecondFragmentEditTextExists()
	{
		assertNotNull(secondFragmentEditField);
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
	
	public void testEmailChangeEditTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstFragmentEditField.setText("bobby@yahoo.com", TextView.BufferType.EDITABLE);
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
		assertEquals("bobby@yahoo.com", firstFragmentEditField.getText().toString());
	}
	
	public void testPhoneChangeEditTextField()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				secondFragmentEditField.setText("(586)000-1234", TextView.BufferType.EDITABLE);
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
		assertEquals("(586)000-1234", secondFragmentEditField.getText().toString());
	}
	
	public void testFirstFragmentSpinnerChange()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstFragmentSpinner.setSelection(1);
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
		assertEquals("Phone Number", firstFragmentSpinner.getSelectedItem().toString());
	}
	
	public void testSecondFragmentSpinnerChange()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstFragmentSpinner.setSelection(0);
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
		assertEquals("Email Address", firstFragmentSpinner.getSelectedItem().toString());
	}
	
	public void testSpinnerChangeWhenSpinnersSelectSameValue()
	{
		newContactActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstFragmentSpinner.setSelection(1);
				saveButton.performClick();
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
		assertEquals("Email Address", secondFragmentSpinner.getSelectedItem().toString());
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
}