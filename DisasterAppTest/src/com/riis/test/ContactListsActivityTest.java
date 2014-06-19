package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.ContactListsActivity;
import com.riis.R;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class ContactListsActivityTest extends ActivityInstrumentationTestCase2<ContactListsActivity> 
{
	private ContactListsActivity contactListsActivity;
	private ListView contactslistView;
	private EditText listName;
	private TextView contactListTextView;
	private Context context;
	
	public ContactListsActivityTest()
	{
		super(ContactListsActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();

		contactListsActivity = getActivity();
		
		contactListTextView = (TextView) contactListsActivity.findViewById(R.id.Contact_Lists_Label);
		listName = (EditText) contactListsActivity.findViewById(R.id.contactListText);
		contactslistView = (ListView) contactListsActivity.findViewById(R.id.contactListsView);
	}

	public void testEditTextExists()
	{
		assertNotNull(listName);
	}
	
	public void testTextViewExists()
	{
		assertNotNull(contactslistView);
	}
	
	public void testListViewExists()
	{
		assertNotNull(contactListTextView);
	}
	
	public void testCreateContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		
		Contact contact = new Contact(context);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		contact.create();
		
		list.addContact(contact);
		assertTrue(list.create());
		
		list.delete();
		contact.delete();
	}
	
	public void testReadContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		
		Contact contact = new Contact(context);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		contact.create();
		
		list.addContact(contact);
		list.create();
		
		ContactList testList = new ContactList(context);
		testList.setName("Test List");
		
		assertTrue(testList.read());
		
		list.delete();
		contact.delete();
	}
	
	public void testUpdateContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		list.create();
		
		Contact contact = new Contact(context);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		contact.create();
		
		list.addContact(contact);
		
		assertTrue(list.update());
		
		list.delete();
		contact.delete();
	}
	
	public void testDeleteContactList()
	{
		ContactList list = new ContactList(context);
		list.setName("Test List");
		
		Contact contact = new Contact(context);
		contact.setFirstName("Bob");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("1234567890");
		contact.create();
		
		list.addContact(contact);
		list.create();
		
		assertTrue(list.delete());
		
		contact.delete();
	}
}