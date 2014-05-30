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
import com.riis.controllers.ContactDataSource;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class SendEmergencyMessageActivityTest extends ActivityInstrumentationTestCase2<SendEmergencyMessageActivity> {
	
	private SendEmergencyMessageActivity sendEmergencyMessageActivity;
	
	private Button cancelEmergencyMessageButton;
	private Button sendEmergencyMessageButton;
	private EditText emergencyMessageField;
	private TextView characterCountLabel;
	private String message;
	
	private Contact contact;
	private ContactDataSource dataSource;
	
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
		
		dataSource = new ContactDataSource(sendEmergencyMessageActivity.getApplicationContext());
		
		contact = new Contact();
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
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
	
//	public void testCancelEmergencyMessageButtonIntent() {
//		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
//		
//		TouchUtils.clickView(this, cancelEmergencyMessageButton);
//		
//		monitor.waitForActivityWithTimeout(5000);
//		assertEquals(1, monitor.getHits());
//		
//		getInstrumentation().removeMonitor(monitor);
//	}
	
//	public void testSendEmergencyMessageButtonIntent() {
//		dataSource.open();
//		dataSource.createContact(contact);
//		Contact newContact = contact;
//		newContact.setPhoneNumber("1234567890");
//		dataSource.createContact(newContact);
//		ContactList contactList = new ContactList();
//		contactList.setContactList(dataSource.getContactList());
//		dataSource.deleteContact(contact);
//		dataSource.deleteContact(newContact);
//		dataSource.close();
//		
//		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
//		
//		message = "";
//		
//		sendEmergencyMessageActivity.runOnUiThread(new Runnable() 
//		{
//			@Override
//			public void run() 
//			{
//				emergencyMessageField.setText("This is a test message.");
//				message = emergencyMessageField.getText().toString();
//				sendEmergencyMessageButton.performClick();
//			}
//		});
//		
//		monitor.waitForActivityWithTimeout(5000);
//		message += " Are you OK?";
//		assertEquals("This is a test message. Are you OK?", message);
//		assertEquals("5555555555", contactList.getContact(0).getPhoneNumber());
//		assertEquals("1234567890", contactList.getContact(1).getPhoneNumber());
//		
//		getInstrumentation().removeMonitor(monitor);
//	}
}
