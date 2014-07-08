package com.riis.test;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.EmailInputActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;

public class LaterEmailInputActivityTest extends ActivityInstrumentationTestCase2<EmailInputActivity>
{
private EmailInputActivity emailInputActivity;
	
	private Context context;
	private EditText emailAddressInput;
	private EditText passwordInput;
	private Button skipButton;
	private Button submitEmailInfoButton;
	
	public LaterEmailInputActivityTest()
	{
		super(EmailInputActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		Intent intent = new Intent(context, SendEmergencyMessageActivity.class);
		intent.putExtra("setUpEmail", true);
		setActivityIntent(intent);
		emailInputActivity = getActivity();

		emailAddressInput = (EditText) emailInputActivity.findViewById(R.id.emailAddressInput);
		passwordInput = (EditText) emailInputActivity.findViewById(R.id.passwordInput);
		skipButton = (Button) emailInputActivity.findViewById(R.id.skipButton);
		submitEmailInfoButton = (Button) emailInputActivity.findViewById(R.id.submitEmailInfoButton);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testEmailAddressInputExists()
	{
		assertNotNull(emailAddressInput);
	}
	
	public void testPasswordInputExists()
	{
		assertNotNull(passwordInput);
	}
	
	public void testSkipButtonExists()
	{
		assertNotNull(skipButton);
	}
	
	public void testSubmitEmailInfoButtonExists()
	{
		assertNotNull(submitEmailInfoButton);
	}
	
	public void testSkipButtonLabel()
	{
		assertEquals("Cancel", skipButton.getText().toString());
	}
	
	public void testEmailAddressChangeTextField()
	{
		emailInputActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				emailAddressInput.setText("test@disaster.com", TextView.BufferType.EDITABLE);
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
		assertEquals("test@disaster.com", emailAddressInput.getText().toString());
	}
	
	public void testPasswordChangeTextField()
	{
		emailInputActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				passwordInput.setText("password1", TextView.BufferType.EDITABLE);
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
		assertEquals("password1", passwordInput.getText().toString());
	}
}