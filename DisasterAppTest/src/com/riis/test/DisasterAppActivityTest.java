package com.riis.test;

import java.util.Calendar;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.riis.ContactListsActivity;
import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.dagger.DaggerApplication;
import com.riis.dagger.DisasterAppTestObjectGraph;
import com.riis.models.Contact;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity>
{
	private DisasterAppActivity disasterAppActivity;
	
	private ListView contactIndicatorListView;
	private Context context;
	private Contact contact;
	private ContactList contactList;
	
	public DisasterAppActivityTest()
	{
		super(DisasterAppActivity.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		
		disasterAppActivity = getActivity();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		ObjectGraph objectGraph= ObjectGraph.create(new DisasterAppTestObjectGraph(context));
		DaggerApplication myapp = (DaggerApplication) this.getInstrumentation().
				getTargetContext().getApplicationContext();
		myapp.setDisasterAppObjectGraph(objectGraph);
		
		contactIndicatorListView = (ListView) disasterAppActivity.findViewById(R.id.contactIndicatorListView);

		contact = new Contact(context);
		contact.setFirstName("Robert");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
		Calendar cal = Calendar.getInstance();
		contact.setMessageSentTimeStamp(cal.getTimeInMillis());
		
		disasterAppActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run()
			{	
				Contact secondContact = new Contact(context);
				secondContact.setFirstName("Mike");
				secondContact.setLastName("Richardson");
				secondContact.setEmailAddress("MJ@example.com");
				secondContact.setPhoneNumber("1235550066");
				
				contact.create();
				secondContact.create();
				
				contactList = new ContactList(context);
				contactList.readAllContacts();
				//contactIndicatorListView.setAdapter(new MessageIndicatorAdapter(context,contactList.getContacts()));
			}
		});
	}
	
	protected void tearDown() throws Exception
	{
		disasterAppActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run()
			{	
				Contact secondContact = new Contact(context);
				secondContact.setFirstName("Mike");
				secondContact.setLastName("Richardson");
				secondContact.setEmailAddress("MJ@example.com");
				secondContact.setPhoneNumber("1235550066");
				
				secondContact.read();
				
				contact.delete();
				secondContact.delete();
			}
		});
		
		Thread.sleep(1000);
		super.tearDown();
	}
	
	public void testIndicatorListViewExists()
	{
		assertNotNull(contactIndicatorListView);
	}
	
	public void testCreateContactButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, true);
		
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createContactItem, 0);
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContactListButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(ContactListsActivity.class.getName(), null, true);
		
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createContactListItem, 0);
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateEmergencyMessageButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(SendEmergencyMessageActivity.class.getName(), null, true);

		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createEmergencyMessageItem, 0);
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testViewMessageResponsesScreenButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(ViewResponseMessagesActivity.class.getName(), null, true);

		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.viewResponseMessagesItem, 0);
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testListViewPopulates() 
	{
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertTrue(contactIndicatorListView.getCount() > 0);	
		contact.read();
		contact.delete();
	}
	
	public void testListItemExpands()
	{
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactIndicatorListView.getChildAt(0));
		
		int visiblility = View.VISIBLE;
		int expandedLayout = contactIndicatorListView.getChildAt(0).findViewById(R.id.indicatorExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testListItemCollapses()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactIndicatorListView.getChildAt(0));
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactIndicatorListView.getChildAt(0));
		
		int visiblility = View.INVISIBLE;
		int expandedLayout = contactIndicatorListView.getChildAt(0).findViewById(R.id.indicatorExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}

	public void testAscendingOrderOfContacts()
	{
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		assertEquals("Jones",contactList.getContact(0).getLastName());
		assertEquals("Richardson",contactList.getContact(1).getLastName());
	}
}