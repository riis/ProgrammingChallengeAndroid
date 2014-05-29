package com.riis.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.R;
import com.riis.SendEmergencyMessageActivity;

public class SendEmergencyMessageActivityTest extends ActivityInstrumentationTestCase2<SendEmergencyMessageActivity> {
	
	private SendEmergencyMessageActivity sendEmergencyMessageActivity;
	
	private Button cancelEmergencyMessageButton;
	private Button sendEmergencyMessageButton;
	
	private EditText emergencyMessageField;
	
	private TextView characterCountLabel;
	
	public SendEmergencyMessageActivityTest() {
		super(SendEmergencyMessageActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		sendEmergencyMessageActivity = getActivity();
		
		cancelEmergencyMessageButton = (Button) sendEmergencyMessageActivity
				.findViewById(R.id.cancelEmergencyMessageButton);
		sendEmergencyMessageButton = (Button) sendEmergencyMessageActivity
				.findViewById(R.id.sendEmergencyMessageButton);
		emergencyMessageField = (EditText) sendEmergencyMessageActivity
				.findViewById(R.id.emergencyMessageField);
		characterCountLabel = (TextView) sendEmergencyMessageActivity
				.findViewById(R.id.characterCountLabel);
	}
	
	public void testCancelEmergencyMessageButton() {
		assertNotNull(cancelEmergencyMessageButton);
	}
	
	public void testSendEmergencyMessageButton() {
		assertNotNull(sendEmergencyMessageButton);
	}
	
	public void testEmergencyMessageField() {
		assertNotNull(emergencyMessageField);
	}
	
	public void testCharacterCountLabel() {
		assertNotNull(characterCountLabel);
	}
	
	public void testChangeCharacterCountLabel() {
		sendEmergencyMessageActivity.runOnUiThread(new Runnable() 
		{
			@Override
			public void run() 
			{
				emergencyMessageField.setText("This is a test message");
			}
		});
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(98, Integer.parseInt(characterCountLabel.getText().toString()));
	}

}
