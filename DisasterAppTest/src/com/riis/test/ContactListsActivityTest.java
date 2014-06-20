package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.ContactListsActivity;
import com.riis.R;

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
		context = this.getInstrumentation()
				.getTargetContext().getApplicationContext();

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
	
}
