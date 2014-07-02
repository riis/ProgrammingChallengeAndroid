package com.riis.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.EditContactActivity;
import com.riis.R;

public class EditContactActivityTest extends ActivityInstrumentationTestCase2<EditContactActivity>
{
	private EditContactActivity editContactActivity;
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
	private Button saveButton;
	private Button cancelButton;
	private Button deleteButton;
	
	public EditContactActivityTest()
	{
		super(EditContactActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		editContactActivity = getActivity();
	
		firstNameText = (TextView)editContactActivity.findViewById(R.id.firstNameLabel);

		lastNameText = (TextView) editContactActivity.findViewById(R.id.lastNameLabel);
		FragmentManager manager = editContactActivity.getFragmentManager();
        
        Fragment firstFragment = manager.findFragmentById(R.id.firstContactInfoFragment);
        firstFragmentSpinner = (Spinner) firstFragment.getView().findViewById(R.id.contactInfoSelection);
        firstFragmentText = (TextView) firstFragment.getView().findViewById(R.id.contactInfoLabel);
        firstFragmentEditField = (EditText) firstFragment.getView().findViewById(R.id.contactInfoEditText);
        
        Fragment secondFragment = manager.findFragmentById(R.id.secondContactInfoFragment);
        secondFragmentSpinner = (Spinner) secondFragment.getView().findViewById(R.id.contactInfoSelection);
        secondFragmentText = (TextView) secondFragment.getView().findViewById(R.id.contactInfoLabel);
        secondFragmentEditField = (EditText) secondFragment.getView().findViewById(R.id.contactInfoEditText);
        
		firstNameEditField = (EditText) editContactActivity.findViewById(R.id.firstNameEditText);
		lastNameEditField = (EditText) editContactActivity.findViewById(R.id.lastNameEditText);
		saveButton = (Button) editContactActivity.findViewById(R.id.saveContactButton);
		cancelButton = (Button) editContactActivity.findViewById(R.id.cancelEditContactButton);
		deleteButton = (Button) editContactActivity.findViewById(R.id.deleteContactButton);
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
		assertNotNull(saveButton);
	}
	
	public void testLastNameChangeTextField()
	{
		editContactActivity.runOnUiThread(new Runnable()
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
		editContactActivity.runOnUiThread(new Runnable()
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
		editContactActivity.runOnUiThread(new Runnable()
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
		editContactActivity.runOnUiThread(new Runnable()
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
		editContactActivity.runOnUiThread(new Runnable()
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
		editContactActivity.runOnUiThread(new Runnable()
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
		assertFalse(editContactActivity.isFirstNameValid("alice@yahoo.com"));
		assertFalse(editContactActivity.isFirstNameValid("1202"));
		assertFalse(editContactActivity.isFirstNameValid("ghangis'khan"));
		assertFalse(editContactActivity.isFirstNameValid("williams-Berr"));
		assertFalse(editContactActivity.isFirstNameValid("ROBERT"));
		assertTrue(editContactActivity.isFirstNameValid("Allie"));
		assertTrue(editContactActivity.isFirstNameValid("stacy"));
		assertTrue(editContactActivity.isFirstNameValid("Williams"));
	}
	
	public void testValidLastName()
	{
		assertFalse(editContactActivity.isLastNameValid("alice@yahoo.com"));
		assertFalse(editContactActivity.isLastNameValid("1202"));
		assertTrue(editContactActivity.isLastNameValid("ROBERT"));
		assertTrue(editContactActivity.isLastNameValid("Allie"));
		assertTrue(editContactActivity.isLastNameValid("stacy"));
		assertTrue(editContactActivity.isLastNameValid("ghangis'khan"));
		assertTrue(editContactActivity.isLastNameValid("williams-Berr"));
		assertTrue(editContactActivity.isLastNameValid("Williams Berr"));
	}

	public void testValidEmail()
	{
		assertTrue(editContactActivity.isEmailValid("alice@yahoo.com"));
		assertTrue(editContactActivity.isEmailValid("Robert24@gmail.edu"));
		assertFalse(editContactActivity.isEmailValid("Robert24@jupiterjupiter"));
		assertFalse(editContactActivity.isEmailValid("SussieQ!@yahoo.com"));
		assertFalse(editContactActivity.isEmailValid("alice"));
		assertFalse(editContactActivity.isEmailValid("@yahoo.com"));
	}
	
	public void testValidPhone()
	{
		assertTrue(editContactActivity.isPhoneValid("5550001234"));
		assertTrue(editContactActivity.isPhoneValid("101-202-6789"));
		assertTrue(editContactActivity.isPhoneValid("(586)202-6789"));
		assertFalse(editContactActivity.isPhoneValid("101-202"));
		assertFalse(editContactActivity.isPhoneValid("alice"));
		assertFalse(editContactActivity.isPhoneValid("123"));
		assertFalse(editContactActivity.isPhoneValid("0001234567a"));
	}
}