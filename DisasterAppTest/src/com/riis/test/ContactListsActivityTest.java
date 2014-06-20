package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.ContactListsActivity;
import com.riis.R;
import com.riis.controllers.ContactSelectionAdapter;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class ContactListsActivityTest extends ActivityInstrumentationTestCase2<ContactListsActivity> 
{
	private ContactListsActivity contactListsActivity;
	private Button saveCreateContactListSaveButton;
	private Button cancelCreateContactListButton;
	private ListView contactsListView;
	private EditText listName;
	private TextView contactListTextView;
	private Context context;
	private Contact contact;
	
	public ContactListsActivityTest()
	{
		super(ContactListsActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();

		contactListsActivity = getActivity();
		
		contactListTextView = (TextView) contactListsActivity.findViewById(R.id.contactListNameLabel);
		listName = (EditText) contactListsActivity.findViewById(R.id.contactListNameText);
		contactsListView = (ListView) contactListsActivity.findViewById(R.id.createContactListsView);
		saveCreateContactListSaveButton = (Button) contactListsActivity.findViewById(R.id.saveCreateContactListSaveButton);
		cancelCreateContactListButton = (Button) contactListsActivity.findViewById(R.id.cancelCreateContactListButton);
		
		contact = new Contact(context);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		
		contactListsActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run()
			{	
				contact.create();
				
				ContactList contactList = new ContactList(context);
				contactList.readAllContacts();
				contactsListView.setAdapter(new ContactSelectionAdapter(context,
						contactList.getContacts()));
			}
		});
	}
	
	protected void tearDown() throws Exception
	{
		contactListsActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run()
			{	
				contact.delete();
			}
		});
		
		Thread.sleep(1000);
		super.tearDown();
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
		assertTrue(contactsListView.getCount() > 0);	
	}
	
	public void testListItemExpands()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactsListView.getChildAt(0));
		
		int visiblility = View.VISIBLE;
		int expandedLayout = contactsListView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testListItemCollapses()
	{
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactsListView.getChildAt(0));
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		TouchUtils.clickView(this, contactsListView.getChildAt(0));
		
		int visiblility = View.INVISIBLE;
		int expandedLayout = contactsListView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}

	public void testEditTextExists()
	{
		assertNotNull(listName);
	}
	
	public void testTextViewExists()
	{
		assertNotNull(contactsListView);
	}
	
	public void testListViewExists()
	{
		assertNotNull(contactListTextView);
	}
	
	public void testSaveButtonExists()
	{
		assertNotNull(saveCreateContactListSaveButton);
	}
	
	public void testCancelButtonExists()
	{
		assertNotNull(cancelCreateContactListButton);
	}
	
	public void testCreateContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		
		list.addContact(contact);
		assertTrue(list.create());
		
		list.delete();
	}
	
	public void testReadContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		
		list.addContact(contact);
		list.create();
		
		ContactList testList = new ContactList(context);
		testList.setName("Test List");
		
		assertTrue(testList.read());
		
		list.delete();
	}
	
	public void testUpdateContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		list.create();
		
		list.addContact(contact);
		
		assertTrue(list.update());
		
		list.delete();
	}
	
	public void testDeleteContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		
		list.addContact(contact);
		list.create();
		
		assertTrue(list.delete());
	}
}