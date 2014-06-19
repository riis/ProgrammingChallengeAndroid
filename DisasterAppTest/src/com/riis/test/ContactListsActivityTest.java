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
		Log.e("my logging", "contructor ");

	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		Log.e("my logging", "basic ");
		context = this.getInstrumentation()
				.getTargetContext().getApplicationContext();

		contactListsActivity = getActivity();
		
		contactListTextView = (TextView) contactListsActivity.findViewById(R.id.Contact_Lists_Label);
		listName = (EditText) contactListsActivity.findViewById(R.id.contactListText);
		contactslistView = (ListView) contactListsActivity.findViewById(R.id.contactListsView);
	 // Log.e("my logging", "textview = " + contactListTextView.getText().toString());
	}

	public void testEditTextExists()
	{
		Log.e("my logging", "inside editText test ");
		assertNotNull(listName);
	}
	
	public void testTextViewExists()
	{
		Log.e("my logging", "inside textView test ");
		assertNotNull(contactslistView);
	}
	
	public void testListViewExists()
	{
		Log.e("my logging", "inside ListView test ");
		assertNotNull(contactListTextView);
	}
	
}