package com.riis.test;

import java.util.Calendar;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.ContactDataSource;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity> {
	
	private Button createContactScreenButton;
	private Button createEmergencyMessageScreenButton;
	private Button viewMessageResponsesScreenButton;
	private TextView sampleLabel;
	
	private Contact contact;
	private Contact secondContact;
	private ContactList contactList;
	

	public DisasterAppActivityTest() {
		super(DisasterAppActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		DisasterAppActivity disasterAppActivity = getActivity();
		
		createContactScreenButton = (Button) disasterAppActivity.findViewById(R.id.createContactScreenButton);
		createEmergencyMessageScreenButton = (Button) disasterAppActivity.findViewById(R.id.createEmergencyMessageScreenButton);
		viewMessageResponsesScreenButton = (Button) disasterAppActivity.findViewById(R.id.viewMessageResponsesScreenButton);
		sampleLabel = (TextView) disasterAppActivity.findViewById(R.id.sampleLabel);
		
		ContactDataSource dataSource = new ContactDataSource(getActivity().getApplicationContext());
		dataSource.open();
		

		try {
			contact = new Contact(dataSource.getContact().getId() + 1);
		} catch (Exception e) {
			contact = new Contact(1);
		}
		
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		secondContact = new Contact(contact.getId() + 1);
		secondContact.setFirstName("Robin");
		secondContact.setLastName("Williams");
		secondContact.setEmailAddress("RWill@comcast.com");
		secondContact.setPhoneNumber("2221234567");
		
		dataSource.createContact(secondContact);
		
		Calendar cal = Calendar.getInstance();
		String date = (cal.get(Calendar.MONTH) + 1) +
				"-"+ cal.get(Calendar.DAY_OF_MONTH) +
				"-"+ cal.get(Calendar.YEAR) +
				" "+cal.getTime().toString().substring(11, 16);
		contact.setMessageSentTimeStamp(date);
		
		dataSource.close();
		
		contactList = new ContactList();
	}
	
	public void testSampleLabelExists() {
		assertNotNull(sampleLabel);
	}
	
	public void testCreateContactScreenButtonExists() {
		assertNotNull(createContactScreenButton);
	}
	
	public void testEmergencyMessageScreenButtonExists() {
		assertNotNull(createEmergencyMessageScreenButton);
	}
	
	public void testViewMessageResponsesScreenButtonExists() {
		assertNotNull(viewMessageResponsesScreenButton);
	}
	
	public void testCreateContactButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, createContactScreenButton);
		
		monitor.waitForActivityWithTimeout(2000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateEmergencyMessageButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(SendEmergencyMessageActivity.class.getName(), null, true);

		TouchUtils.clickView(this, createEmergencyMessageScreenButton);
		
		monitor.waitForActivityWithTimeout(2000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testViewMessageResponsesScreenButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(ViewResponseMessagesActivity.class.getName(), null, true);

		TouchUtils.clickView(this, viewMessageResponsesScreenButton);
		
		monitor.waitForActivityWithTimeout(2000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContact() {
		ContactDataSource dataSource = new ContactDataSource(getActivity().getApplicationContext());
		dataSource.open();
		
		
		dataSource.createContact(contact);
		Contact output = dataSource.getContact();
		dataSource.deleteContact(output);
		dataSource.close();
		assertEquals(output.getFirstName(), contact.getFirstName());
		assertEquals(output.getLastName(), contact.getLastName());
		assertEquals(output.getEmailAddress(), contact.getEmailAddress());
		assertEquals(output.getPhoneNumber(), contact.getPhoneNumber());

	}
	
	public void testTextViewDisplaysContacts() {
		ContactDataSource dataSource = new ContactDataSource(getActivity().getApplicationContext());
		dataSource.open();
		
		
		Contact secondContact = new Contact(contact.getId() + 1);
		secondContact.setFirstName("Rob");
		secondContact.setLastName("Will");
		secondContact.setEmailAddress("RWill@comcast.com");
		secondContact.setPhoneNumber("0001234567");
		
		dataSource.createContact(secondContact);
		dataSource.deleteContact(secondContact);
		
		contactList.setContactList(dataSource.getContactList());
		Log.e("my log ", "text inside: " + sampleLabel.getText());
		assertTrue(sampleLabel.getLineCount()>1);

		dataSource.close();
	}
	
	public void testRecievedMessageIndicatorinTextView() {
		ContactDataSource dataSource = new ContactDataSource(getActivity().getApplicationContext());
		dataSource.open();
		
		dataSource.createContact(contact);
		
		dataSource.close();
		
		String text = sampleLabel.getText().toString();
		
		text = text.substring(0, text.lastIndexOf("\n"));
		text = text.substring(text.lastIndexOf("\n") + 1);
		
		assertTrue(sampleLabel.getText().toString().contains(secondContact.getFirstName() 
				   + " " + secondContact.getLastName() 
				   + "\t "+ secondContact.getEmailAddress() 
				   + "\t "+ secondContact.getPhoneNumber()
				   + "\t "+ "*"
				 ));
		
		
	}
	
	public void testGetAllContactsInTextView() {
		
		
		ContactDataSource dataSource = new ContactDataSource(getActivity().getApplicationContext());
		dataSource.open();
		
		dataSource.createContact(contact);
		
		Contact newContact = new Contact(contact.getId() + 1);
		newContact.setFirstName("Joe");
		newContact.setLastName("Smith");
		newContact.setEmailAddress("jsmith_1@sample.com");
		newContact.setPhoneNumber("1234567890");
		
		dataSource.createContact(newContact);
		contactList.setContactList(dataSource.getContactList());
		dataSource.deleteContact(newContact);
		dataSource.deleteContact(contact);

		dataSource.close();
		assertTrue(contactList.size() > 1);
		
	}
	
	
	
}
