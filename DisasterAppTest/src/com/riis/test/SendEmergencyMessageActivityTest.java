package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.mocks.MockTextMessage;

public class SendEmergencyMessageActivityTest extends ActivityInstrumentationTestCase2<SendEmergencyMessageActivity> {
	
	private SendEmergencyMessageActivity sendEmergencyMessageActivity;
	
	private Button cancelEmergencyMessageButton;
	private Button sendEmergencyMessageButton;
	private EditText emergencyMessageField;
	private TextView characterCountLabel;
	private String[] randomPhoneNumbersForTesting = new String[5];
	
	private MockTextMessage fakeText;
	
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
		
		fakeText = new MockTextMessage();
	}
	
	public void testCancelEmergencyMessageButtonExists() {
		assertNotNull(cancelEmergencyMessageButton);
	}
	
	public void testSendEmergencyMessageButtonExists() {
		assertNotNull(sendEmergencyMessageButton);
	}
	
	public void testEmergencyMessageFieldExists() {
		assertNotNull(emergencyMessageField);
	}
	
	public void testCharacterCountLabelExists() {
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
	
	public void testCancelEmergencyMessageButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, cancelEmergencyMessageButton);
		
		monitor.waitForActivityWithTimeout(5000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testSendEmergencyMessageButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(SendEmergencyMessageActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, sendEmergencyMessageButton);
		
		sendEmergencyMessageActivity.runOnUiThread(new Runnable() 
		{
			@Override
			public void run() 
			{
				emergencyMessageField.setText("This is a test message");
			}
		});
		monitor.waitForActivityWithTimeout(5000);
		
		randomPhoneNumbersForTesting[0]="5869332211";
		randomPhoneNumbersForTesting[1]="0001234567";

		randomPhoneNumbersForTesting[2]="0001234567";

		randomPhoneNumbersForTesting[3]="0001234567";

		randomPhoneNumbersForTesting[4]="0001234567";

		sendEmergencyMessageActivity.sendEmergencyMessage(randomPhoneNumbersForTesting);
		assertEquals("This is a test message", sendEmergencyMessageActivity.getMessageBack());
		assertEquals("5869332211", sendEmergencyMessageActivity.getFirstPhoneNumberBack());
		assertEquals("0001234567", sendEmergencyMessageActivity.getLastPhoneNumberBack());
		
		
		getInstrumentation().removeMonitor(monitor);
	}

}
