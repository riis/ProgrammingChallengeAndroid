package com.riis.test;

import javax.inject.Inject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.ContactDetailsActivity;
import com.riis.R;
import com.riis.dagger.ContactDetailsTestObjectGraph;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;

import dagger.ObjectGraph;

public class EditContactDetailsActivityTest extends ActivityInstrumentationTestCase2<ContactDetailsActivity>
{
	private Button cancelButton;
	private Button deleteButton;
	private Button updateButton;
	
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
	
	private ContactDetailsActivity contactDetailsActivity;
	
	@Inject Contact newContact;
	
	public EditContactDetailsActivityTest()
	{
		super(ContactDetailsActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		Intent intent = new Intent(context, ContactDetailsActivity.class);
		intent.putExtra("id", 1);
		setActivityIntent(intent);
		contactDetailsActivity = getActivity();
		
		ObjectGraph objectGraph= ObjectGraph.create(new ContactDetailsTestObjectGraph(context));
		DaggerApplication myapp = (DaggerApplication) context;
		myapp.setContactDetailsObjectGraph(objectGraph);
		
		firstNameText = (TextView)contactDetailsActivity.findViewById(R.id.firstNameLabel);
		lastNameText = (TextView) contactDetailsActivity.findViewById(R.id.lastNameLabel);
		
		FragmentManager manager = contactDetailsActivity.getFragmentManager();
        
        Fragment firstFragment = manager.findFragmentById(R.id.firstContactInfoFragment);
        firstFragmentSpinner = (Spinner) firstFragment.getView().findViewById(R.id.contactInfoSelection);
        firstFragmentText = (TextView) firstFragment.getView().findViewById(R.id.contactInfoLabel);
        firstFragmentEditField = (EditText) firstFragment.getView().findViewById(R.id.contactInfoEditText);
        
        Fragment secondFragment = manager.findFragmentById(R.id.secondContactInfoFragment);
        secondFragmentSpinner = (Spinner) secondFragment.getView().findViewById(R.id.contactInfoSelection);
        secondFragmentText = (TextView) secondFragment.getView().findViewById(R.id.contactInfoLabel);
        secondFragmentEditField = (EditText) secondFragment.getView().findViewById(R.id.contactInfoEditText);
        
		firstNameEditField = (EditText) contactDetailsActivity.findViewById(R.id.firstNameEditText);
		lastNameEditField = (EditText) contactDetailsActivity.findViewById(R.id.lastNameEditText);
		cancelButton = (Button) contactDetailsActivity.findViewById(R.id.cancelEditContactButton);
		deleteButton = (Button) contactDetailsActivity.findViewById(R.id.deleteContactButton);
		updateButton = (Button) contactDetailsActivity.findViewById(R.id.updateContactButton);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
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
	
	public void testCancelButtonExists()
	{
		assertNotNull(cancelButton);
	}
	
	public void testDeleteButtonExists()
	{
		assertNotNull(deleteButton);
	}
	
	public void testSaveButtonExists()
	{
		assertNotNull(updateButton);
	}
	
	public void testInitialFirstNameField()
	{
		assertEquals("Bob", firstNameEditField.getText().toString());
	}
	
	public void testInitialLastNameField()
	{
		assertEquals("Jones", lastNameEditField.getText().toString());
	}
	
	public void testInitialEmailField()
	{
		assertEquals("bjones@example.com", firstFragmentEditField.getText().toString());
	}
	
	public void testInitialPhoneNumber()
	{
		assertEquals("1234567890", secondFragmentEditField.getText().toString());
	}
	
	public void testFirstNameChangeTextField()
	{
		contactDetailsActivity.runOnUiThread(new Runnable()
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
		contactDetailsActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
			lastNameEditField.setText("Jones", TextView.BufferType.EDITABLE);
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
		assertEquals("Jones", lastNameEditField.getText().toString());
	}
	
	public void testEmailChangeEditTextField()
	{
		contactDetailsActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstFragmentEditField.setText("bjones@example.com", TextView.BufferType.EDITABLE);
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
		assertEquals("bjones@example.com", firstFragmentEditField.getText().toString());
	}
	
	public void testPhoneChangeEditTextField()
	{
		contactDetailsActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				secondFragmentEditField.setText("1234567890", TextView.BufferType.EDITABLE);
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
		assertEquals("1234567890", secondFragmentEditField.getText().toString());
	}
	
	public void testFirstFragmentSpinnerChange()
	{
		contactDetailsActivity.runOnUiThread(new Runnable()
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
		contactDetailsActivity.runOnUiThread(new Runnable()
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
		contactDetailsActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				firstFragmentSpinner.setSelection(1);
				updateButton.performClick();
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
	
	public void testCancelButtonIntent()
	{
		TouchUtils.clickView(this, cancelButton);
		assertTrue(contactDetailsActivity.isFinishing());
	}
	
	public void testValidFirstName()
	{
		assertFalse(contactDetailsActivity.isFirstNameValid("alice@yahoo.com"));
		assertFalse(contactDetailsActivity.isFirstNameValid("1202"));
		assertFalse(contactDetailsActivity.isFirstNameValid("ghangis'khan"));
		assertFalse(contactDetailsActivity.isFirstNameValid("williams-Berr"));
		assertFalse(contactDetailsActivity.isFirstNameValid("ROBERT"));
		assertTrue(contactDetailsActivity.isFirstNameValid("Allie"));
		assertTrue(contactDetailsActivity.isFirstNameValid("stacy"));
		assertTrue(contactDetailsActivity.isFirstNameValid("Williams"));
	}
	
	public void testValidLastName()
	{
		assertFalse(contactDetailsActivity.isLastNameValid("alice@yahoo.com"));
		assertFalse(contactDetailsActivity.isLastNameValid("1202"));
		assertTrue(contactDetailsActivity.isLastNameValid("ROBERT"));
		assertTrue(contactDetailsActivity.isLastNameValid("Allie"));
		assertTrue(contactDetailsActivity.isLastNameValid("stacy"));
		assertTrue(contactDetailsActivity.isLastNameValid("ghangis'khan"));
		assertTrue(contactDetailsActivity.isLastNameValid("williams-Berr"));
		assertTrue(contactDetailsActivity.isLastNameValid("Williams Berr"));
	}

	public void testValidEmail()
	{
		assertTrue(contactDetailsActivity.isEmailValid("alice@yahoo.com"));
		assertTrue(contactDetailsActivity.isEmailValid("Robert24@gmail.edu"));
		assertFalse(contactDetailsActivity.isEmailValid("Robert24@jupiterjupiter"));
		assertFalse(contactDetailsActivity.isEmailValid("SussieQ!@yahoo.com"));
		assertFalse(contactDetailsActivity.isEmailValid("alice"));
		assertFalse(contactDetailsActivity.isEmailValid("@yahoo.com"));
	}
	
	public void testValidPhone()
	{
		assertTrue(contactDetailsActivity.isPhoneValid("5550001234"));
		assertTrue(contactDetailsActivity.isPhoneValid("101-202-6789"));
		assertTrue(contactDetailsActivity.isPhoneValid("(586)202-6789"));
		assertFalse(contactDetailsActivity.isPhoneValid("101-202"));
		assertFalse(contactDetailsActivity.isPhoneValid("alice"));
		assertFalse(contactDetailsActivity.isPhoneValid("123"));
		assertFalse(contactDetailsActivity.isPhoneValid("0001234567a"));
	}
}