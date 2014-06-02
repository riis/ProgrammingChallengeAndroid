package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.controllers.ContactDataSource;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity> {
	
	private Button createContactScreenButton;
	private Button createEmergencyMessageScreenButton;
	private TextView sampleLabel;
	
	private Contact contact;
	private ContactList contactList;
	private ContactDataSource dataSource;

	public DisasterAppActivityTest() {
		super(DisasterAppActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		DisasterAppActivity disasterAppActivity = getActivity();
		
		createContactScreenButton = (Button) disasterAppActivity.findViewById(R.id.createContactScreenButton);
		createEmergencyMessageScreenButton = (Button) disasterAppActivity.findViewById(R.id.createEmergencyMessageScreenButton);
		sampleLabel = (TextView) disasterAppActivity.findViewById(R.id.sampleLabel);
		
		contact = new Contact();
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		dataSource = new ContactDataSource(getActivity().getApplicationContext());
		dataSource.open();
		
		contactList = new ContactList();
	}
	
	public void testSampleLabelExists() {
		assertNotNull(sampleLabel);
	}
	
	public void testCreateContactScreenButtonExists() {
		assertNotNull(createContactScreenButton);
	}
	
	public void testCreateContactButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, createContactScreenButton);
		
		monitor.waitForActivityWithTimeout(5000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateEmergencyMessageButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(SendEmergencyMessageActivity.class.getName(), null, true);

		TouchUtils.clickView(this, createEmergencyMessageScreenButton);
		
		monitor.waitForActivityWithTimeout(5000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContact() {
		dataSource.createContact(contact);
		Contact output = dataSource.getContact();
		dataSource.deleteContact(output);
		dataSource.close();
		assertEquals(output.getFirstName(), contact.getFirstName());
		assertEquals(output.getLastName(), contact.getLastName());
		assertEquals(output.getEmailAddress(), contact.getEmailAddress());
		assertEquals(output.getPhoneNumber(), contact.getPhoneNumber());
	}
	
	public void testGetAllContacts() {
		dataSource.createContact(contact);
		Contact newContact = contact;
		dataSource.createContact(newContact);
		contactList.setContactList(dataSource.getContactList());
		dataSource.deleteContact(contact);
		dataSource.close();
		assertTrue(contactList.size() > 1);
	}
}
