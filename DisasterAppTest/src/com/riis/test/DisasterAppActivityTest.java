package com.riis.test;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.riis.CreateContactListsActivity;
import com.riis.DisasterAppActivity;
import com.riis.EditContactListMembersActivity;
import com.riis.NewContactActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.contactListDisplay.ContactListDisplayAdapter;
import com.riis.dagger.DaggerApplication;
import com.riis.dagger.DisasterAppTestObjectGraph;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;
import com.riis.models.ResponseMessage;

import dagger.ObjectGraph;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity>
{
	private DisasterAppActivity disasterAppActivity;
	
	private ListView contactListDisplay;
	private Context context;
	private Contact contact;
	
	@Inject ContactList contactList;
	@Inject ListOfContactLists listOfLists;
	
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
		DaggerApplication myapp = (DaggerApplication) context;
		myapp.setDisasterAppObjectGraph(objectGraph);
		
		contactListDisplay = (ListView) disasterAppActivity.findViewById(R.id.contactListDisplay);

		contact = new Contact(context);
		contact.setFirstName("Robert");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
		
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
				contactList.setName("Test");
				contactList.create();
				contactList.addContact(contact);
				contactList.addContact(secondContact);
				contactList.update();
				
				ResponseMessage message = new ResponseMessage(context);
				message.setPhoneNumber(contact.getPhoneNumber());
				message.setTextMessageContents("Yes");
				message.setContactListId(contactList.getId());
				message.create();
				
				ArrayList<ContactList> list = new ArrayList<ContactList>();
				list.add(contactList);
				contactListDisplay.setAdapter(new ContactListDisplayAdapter(context,
						list));
			}
		});
		
		Thread.sleep(3000);
	}
	
	protected void tearDown() throws Exception
	{
		disasterAppActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run()
			{
				contact = new Contact(context);
				contact.setFirstName("Robert");
				contact.setLastName("Jones");
				contact.setEmailAddress("bjones@example.com");
				contact.setPhoneNumber("5555555555");
				contact.read();
				
				Contact secondContact = new Contact(context);
				secondContact.setFirstName("Mike");
				secondContact.setLastName("Richardson");
				secondContact.setEmailAddress("MJ@example.com");
				secondContact.setPhoneNumber("1235550066");
				
				secondContact.read();
				
				ResponseMessage message = new ResponseMessage(context);
				message.setPhoneNumber(contact.getPhoneNumber());
				message.setTextMessageContents("Yes");
				message.setContactListId(contactList.getId());
				message.read();
				message.delete();
				
				contactList.getContacts().get(1).delete();
				contactList.getContacts().get(0).delete();
				contactList.update();
				contact.delete();
				secondContact.delete();
			}
		});
		
		Thread.sleep(3000);
		super.tearDown();
	}
	
	public void testContactListViewExists()
	{
		assertNotNull(contactListDisplay);
	}
	
	public void testEditListButtonExists()
	{
		assertNotNull(contactListDisplay.getChildAt(0).findViewById(R.id.editContactListButton));
	}
	
	public void testSendMessageToListButtonExists()
	{
		assertNotNull(contactListDisplay.getChildAt(0).findViewById(R.id.sendMessageContactListButton));
	}
	
	public void testEditContactListButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(EditContactListMembersActivity.class.getName(), null, false);
		
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.editContactListButton));
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContactButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
		
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createContactItem, 0);
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
//	public void testCreateContactListButtonIntent()
//	{
//		ActivityMonitor monitor = getInstrumentation().addMonitor(CreateContactListsActivity.class.getName(), null, true);
//		
//		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
//		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createContactListItem, 0);
//		
//		monitor.waitForActivityWithTimeout(500);
//		assertEquals(1, monitor.getHits());
//		
//		getInstrumentation().removeMonitor(monitor);
//	}
	
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
		
		monitor.waitForActivityWithTimeout(1000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testListViewPopulates() 
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertTrue(contactListDisplay.getCount() > 0);	
	}
	
	public void testListItemExpands()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue));
		
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		int visiblility = View.VISIBLE;
		int expandedLayout = contactListDisplay.getChildAt(0).findViewById(R.id.contactListMemberLayout).getVisibility();
		
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
		
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue));
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue));
		
		int visiblility = View.GONE;
		int expandedLayout = contactListDisplay.getChildAt(0).findViewById(R.id.contactListMemberLayout).getVisibility();
		
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