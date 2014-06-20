package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.EditContactListMembersActivity;
import com.riis.R;
import com.riis.controllers.ContactSelectionAdapter;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class EditContactListMembersTest extends ActivityInstrumentationTestCase2<EditContactListMembersActivity>
{
	private EditContactListMembersActivity editContactListMembersActivity;
	private Button updateContactListButton;
	private Button cancelCreateContactListButton;
	private ListView contactsListView;
	private TextView contactListTextView;
	private Context context;
	private Contact contact;
	
	public EditContactListMembersTest()
	{
		super(EditContactListMembersActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();

		editContactListMembersActivity = getActivity();
		
		contactListTextView = (TextView) editContactListMembersActivity.findViewById(R.id.contactListNameLabel);
		contactsListView = (ListView) editContactListMembersActivity.findViewById(R.id.createContactListsView);
		updateContactListButton = (Button) editContactListMembersActivity.findViewById(R.id.saveCreateContactListSaveButton);
		cancelCreateContactListButton = (Button) editContactListMembersActivity.findViewById(R.id.cancelCreateContactListButton);
		
		contact = new Contact(context);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		
		editContactListMembersActivity.runOnUiThread(new Runnable() 
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
		editContactListMembersActivity.runOnUiThread(new Runnable() 
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
	
//	public void testListViewPopulates() 
//	{
//		try
//		{
//			Thread.sleep(500);
//		}
//		catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
//		assertTrue(contactsListView.getCount() > 0);	
//	}
//	
//	public void testListItemExpands()
//	{
//		try
//		{
//			Thread.sleep(500);
//		}
//		catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
//		
//		TouchUtils.clickView(this, contactsListView.getChildAt(0));
//		
//		int visiblility = View.VISIBLE;
//		int expandedLayout = contactsListView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
//		
//		assertEquals(expandedLayout, visiblility);
//	}
//	
//	public void testListItemCollapses()
//	{
//		try
//		{
//			Thread.sleep(2000);
//		}
//		catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
//		
//		TouchUtils.clickView(this, contactsListView.getChildAt(0));
//		
//		try
//		{
//			Thread.sleep(500);
//		}
//		catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
//		
//		TouchUtils.clickView(this, contactsListView.getChildAt(0));
//		
//		int visiblility = View.INVISIBLE;
//		int expandedLayout = contactsListView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
//		
//		assertEquals(expandedLayout, visiblility);
//	}
	
	public void testUpdateContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		list.create();
		
		list.addContact(contact);
		
		assertTrue(list.update());
		
		list.delete();
	}
	
	public void testUpdateButtonText()
	{
		assertEquals("Update", updateContactListButton.getText().toString());
	}
	
	public void testTextViewExists()
	{
		assertNotNull(contactsListView);
	}
	
	public void testListViewExists()
	{
		assertNotNull(contactListTextView);
	}
	
	public void testUpdateButtonExists()
	{
		assertNotNull(updateContactListButton);
	}
	
	public void testCancelButtonExists()
	{
		assertNotNull(cancelCreateContactListButton);
	}
}