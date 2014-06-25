package com.riis.models.test;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.R;
import com.riis.SendEmergencyMessageActivity;

public class EmergencyTextMessageWatcherTest extends ActivityInstrumentationTestCase2<SendEmergencyMessageActivity>{
	
	private SendEmergencyMessageActivity sendEmergencyMessageActivity;
	private EditText emergencyMessageField;
	private TextView characterCountLabel;

	public EmergencyTextMessageWatcherTest() {
		super(SendEmergencyMessageActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		Intent intent = new Intent(context, SendEmergencyMessageActivity.class);
		intent.putExtra("CONTACT_LIST_NAME", "Test");
		setActivityIntent(intent);
		sendEmergencyMessageActivity = getActivity();
		
		emergencyMessageField = (EditText) sendEmergencyMessageActivity
				.findViewById(R.id.emergencyMessageField);
		characterCountLabel = (TextView) sendEmergencyMessageActivity
				.findViewById(R.id.characterCountLabel);
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
