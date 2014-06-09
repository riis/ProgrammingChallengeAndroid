package com.riis.test;

import java.util.Calendar;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.DisasterAppDataSource;
import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity> {
	
	private DisasterAppActivity disasterAppActivity;
	
	private Button createContactScreenButton;
	private Button createEmergencyMessageScreenButton;
	private Button viewMessageResponsesScreenButton;
	private ListView contactIndicatorListView;
	
	private Contact contact;
	
	public DisasterAppActivityTest() {
		super(DisasterAppActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		disasterAppActivity = getActivity();
		
		createContactScreenButton = (Button) disasterAppActivity.findViewById(R.id.createContactScreenButton);
		createEmergencyMessageScreenButton = (Button) disasterAppActivity.findViewById(R.id.createEmergencyMessageScreenButton);
		viewMessageResponsesScreenButton = (Button) disasterAppActivity.findViewById(R.id.viewMessageResponsesScreenButton);

		contactIndicatorListView = (ListView) disasterAppActivity.findViewById(R.id.contactIndicatorListView);

		contact = new Contact();
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
	
	public void testCreateContact() {
		DisasterAppDataSource dataSource = new DisasterAppDataSource(getActivity().getApplicationContext());
		dataSource.open();
		
		dataSource.createContact(contact);
		
		ContactList contactList = new ContactList();
		contactList.setContactList(dataSource.getContactList());
		Contact output = contactList.getContact(contactList.size() - 1);

		dataSource.deleteContact(output);
		dataSource.close();
		
		assertEquals(output.getFirstName(), contact.getFirstName());
		assertEquals(output.getLastName(), contact.getLastName());
		assertEquals(output.getEmailAddress(), contact.getEmailAddress());
		assertEquals(output.getPhoneNumber(), contact.getPhoneNumber());
	}
	
	public void testListViewPopulates() {
		disasterAppActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				DisasterAppDataSource dataSource = new DisasterAppDataSource(
						disasterAppActivity.getApplicationContext());
				
	        	dataSource.open();
	        	
				dataSource.createContact(contact);
				contactIndicatorListView.setAdapter(new MessageIndicatorAdapter(disasterAppActivity.getApplicationContext(),
						dataSource.getContactList()));
				dataSource.close();
			}
		});
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(contactIndicatorListView.getCount() > 0);
		
		DisasterAppDataSource dataSource = new DisasterAppDataSource(disasterAppActivity.getApplicationContext());
		dataSource.open();
		
		dataSource.deleteContact(contact);
		
		dataSource.close();
	}
}
