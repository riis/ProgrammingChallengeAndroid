package com.riis.test;

import java.util.Calendar;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity> {
	
	private DisasterAppActivity disasterAppActivity;
	
	private Button createContactScreenButton;
	private Button createEmergencyMessageScreenButton;
	private Button viewMessageResponsesScreenButton;
	private ListView contactIndicatorListView;
	private Context context;
	private Contact contact;
	
	public DisasterAppActivityTest() {
		super(DisasterAppActivity.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		disasterAppActivity = getActivity();
		context = this.getInstrumentation()
				.getTargetContext().getApplicationContext();
		createContactScreenButton = (Button) disasterAppActivity.findViewById(R.id.createContactScreenButton);
		createEmergencyMessageScreenButton = (Button) disasterAppActivity.findViewById(R.id.createEmergencyMessageScreenButton);
		viewMessageResponsesScreenButton = (Button) disasterAppActivity.findViewById(R.id.viewMessageResponsesScreenButton);

		contactIndicatorListView = (ListView) disasterAppActivity.findViewById(R.id.contactIndicatorListView);

		contact = new Contact(context);
		contact.setFirstName("Robert");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		Calendar cal = Calendar.getInstance();
		contact.setMessageSentTimeStamp(cal.getTimeInMillis());
	}
	
	public void testIndicatorListViewExists() {
		assertNotNull(contactIndicatorListView);
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
	
	public void testCreateContact() 
	{
		contact.create();
		
		ContactList contactList = new ContactList(context);
		contactList.read();
		Contact output = contactList.getContact(contactList.size() - 1);

		contact.delete();
		
		assertEquals(output.getFirstName(), contact.getFirstName());
		assertEquals(output.getLastName(), contact.getLastName());
		assertEquals(output.getEmailAddress(), contact.getEmailAddress());
		assertEquals(output.getPhoneNumber(), contact.getPhoneNumber());
	}
	
	public void testListViewPopulates() 
	{
		disasterAppActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() {	
				contact.create();
				ContactList contactList = new ContactList(context);
				contactList.read();
				contactIndicatorListView.setAdapter(new MessageIndicatorAdapter(disasterAppActivity.getApplicationContext(),
						contactList.getContacts()));
				
			}
		});
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(contactIndicatorListView.getCount() > 0);	
		contact.read();
		contact.delete();
	}
	
	public void testAscendingOrderOfContacts()
	{
		Contact secondContact = new Contact(context);
		secondContact.setFirstName("Mike");
		secondContact.setLastName("Richardson");
		secondContact.setEmailAddress("MJ@example.com");
		secondContact.setPhoneNumber("1235550066");
		
		secondContact.create();
		contact.create();
		
		ContactList contactList = new ContactList(context);
		contactList.read();
		
		contact.delete();
		secondContact.delete();
		
		
		assertEquals("Jones",contactList.getContact(0).getLastName());
		assertEquals("Richardson",contactList.getContact(1).getLastName());
		
	}
}
