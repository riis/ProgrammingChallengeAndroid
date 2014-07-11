package com.riis.test;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.dagger.DaggerApplication;
import com.riis.dagger.SendEmergencyMessageTestObjectGraph;

import dagger.ObjectGraph;

public class SendEmergencyMessageActivityTest extends ActivityInstrumentationTestCase2<SendEmergencyMessageActivity> {
	
	private SendEmergencyMessageActivity sendEmergencyMessageActivity;
	
	private Button cancelEmergencyMessageButton;
	private Button sendEmergencyMessageButton;
	private EditText emergencyMessageField;
	private ListView recipientList;
	private TextView characterCountLabel;
	private TextView recipientLabel;
	private Context context;
	
	public SendEmergencyMessageActivityTest()
	{
		super(SendEmergencyMessageActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();

		ObjectGraph objectGraph= ObjectGraph.create(new SendEmergencyMessageTestObjectGraph(context));
		DaggerApplication myapp = (DaggerApplication) this.getInstrumentation().getTargetContext().getApplicationContext();
		myapp.setSendEmergencyMessageObjectGraph(objectGraph);
		
		Intent intent = new Intent(context, SendEmergencyMessageActivity.class);
		intent.putExtra("CONTACT_LIST_NAME", "Test");
		setActivityIntent(intent);
		sendEmergencyMessageActivity = getActivity();
		
		cancelEmergencyMessageButton = (Button) sendEmergencyMessageActivity.findViewById(R.id.cancelEmergencyMessageButton);
		sendEmergencyMessageButton = (Button) sendEmergencyMessageActivity.findViewById(R.id.sendEmergencyMessageButton);
		emergencyMessageField = (EditText) sendEmergencyMessageActivity.findViewById(R.id.emergencyMessageField);
		characterCountLabel = (TextView) sendEmergencyMessageActivity.findViewById(R.id.characterCountLabel);
		recipientLabel = (TextView) sendEmergencyMessageActivity.findViewById(R.id.recipientsLabel);
		recipientList = (ListView) sendEmergencyMessageActivity.findViewById(R.id.messageRecipientsList);
	}
	
	public void testRecepiantListExists()
	{
		assertNotNull(recipientList);
	}
	
	public void testRecepiantLabelExists()
	{
		assertNotNull(recipientLabel);
	}
	
	public void testCancelEmergencyMessageButtonExists()
	{
		assertNotNull(cancelEmergencyMessageButton);
	}
	
	public void testSendEmergencyMessageButtonExists()
	{
		assertNotNull(sendEmergencyMessageButton);
	}
	
	public void testEmergencyMessageFieldExists()
	{
		assertNotNull(emergencyMessageField);
	}
	
	public void testCharacterCountLabelExists()
	{
		assertNotNull(characterCountLabel);
	}
	
	public void testRecepiantListPopulates()
	{
		assertTrue(recipientList.getCount() > 0);
	}
	
	public void testChangeCharacterCountLabel()
	{
		sendEmergencyMessageActivity.runOnUiThread(new Runnable() 
		{
			@Override
			public void run() 
			{
				emergencyMessageField.setText("This is a test message");
			}
		});
		
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		assertEquals(98, Integer.parseInt(characterCountLabel.getText().toString()));
	}
	
	public void testCancelButtonIntent()
	{
		TouchUtils.clickView(this, cancelEmergencyMessageButton);
		assertTrue(sendEmergencyMessageActivity.isFinishing());
	}
}