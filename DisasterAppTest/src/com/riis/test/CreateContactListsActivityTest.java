package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.CreateContactListsActivity;
import com.riis.R;
import com.riis.dagger.CRUDContactListTestObjectGraph;
import com.riis.dagger.DaggerApplication;

import dagger.ObjectGraph;

public class CreateContactListsActivityTest extends ActivityInstrumentationTestCase2<CreateContactListsActivity> 
{
	private CreateContactListsActivity contactListsActivity;
	private Button saveCreateContactListSaveButton;
	private Button cancelCreateContactListButton;
	private Button cloneButton;
	private Button removeContactListButton;
	private ListView contactsListView;
	private EditText listName;
	private TextView contactListTextView;
	private Context context;
	
	public CreateContactListsActivityTest()
	{
		super(CreateContactListsActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		ObjectGraph objectGraph= ObjectGraph.create(new CRUDContactListTestObjectGraph(context));
		DaggerApplication myapp = (DaggerApplication) context;
		myapp.setCRUDContactListObjectGraph(objectGraph);

		contactListsActivity = getActivity();
		
		contactListTextView = (TextView) contactListsActivity.findViewById(R.id.contactListNameLabel);
		listName = (EditText) contactListsActivity.findViewById(R.id.contactListNameText);
		contactsListView = (ListView) contactListsActivity.findViewById(R.id.createContactListsView);
		saveCreateContactListSaveButton = (Button) contactListsActivity.findViewById(R.id.saveCreateContactListSaveButton);
		cancelCreateContactListButton = (Button) contactListsActivity.findViewById(R.id.cancelCreateContactListButton);
		removeContactListButton = (Button) contactListsActivity.findViewById(R.id.removeContactListButton);
		cloneButton = (Button) contactListsActivity.findViewById(R.id.cloneContactListButton);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testListNameChangeTextField()
	{
		contactListsActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				listName.setText("Test", TextView.BufferType.EDITABLE);
			}
		});
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		assertEquals("Test", listName.getText().toString());
	}
	
	public void testListViewPopulates() 
	{
		assertTrue(contactsListView.getCount() > 0);	
	}
	
	public void testListItemExpands()
	{
		TouchUtils.clickView(this, contactsListView.getChildAt(0));
		
		int visiblility = View.VISIBLE;
		int expandedLayout = contactsListView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testListItemCollapses()
	{
		TouchUtils.clickView(this, contactsListView.getChildAt(0));
		
		TouchUtils.clickView(this, contactsListView.getChildAt(0));
		
		int visiblility = View.INVISIBLE;
		int expandedLayout = contactsListView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testCheckBoxExists()
	{
		CheckBox box = (CheckBox) contactsListView.getChildAt(0).findViewById(R.id.selectContactCheckBox);
		assertNotNull(box);
	}
	
	public void testCheckBoxIsNotChecked()
	{
		CheckBox box = (CheckBox) contactsListView.getChildAt(0).findViewById(R.id.selectContactCheckBox);
		assertFalse(box.isChecked());
		box = (CheckBox) contactsListView.getChildAt(1).findViewById(R.id.selectContactCheckBox);
		assertFalse(box.isChecked());
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
	
	public void testRemoveButtonExists()
	{
		assertEquals(View.GONE,removeContactListButton.getVisibility());
	}
	
	public void testcloneButtonExists()
	{
		assertEquals(View.GONE,cloneButton.getVisibility());
	}
	
//	public void testCreateContactList()
//	{
//		ContactList list = new ContactList(context);
//		list.setName("Test List");
//		
//		list.addContact(contact);
//		assertTrue(list.create());
//		
//		list.delete();
//	}
//	
//	public void testReadContactList()
//	{
//		ContactList list = new ContactList(context);
//		list.setName("Test List");
//		
//		list.addContact(contact);
//		list.create();
//		
//		ContactList testList = new ContactList(context);
//		testList.setName("Test List");
//		
//		assertTrue(testList.read());
//		
//		list.delete();
//	}
//	
//	public void testDeleteContactList()
//	{
//		ContactList list = new ContactList(context);
//		list.setName("Test List");
//		
//		list.addContact(contact);
//		list.create();
//		
//		assertTrue(list.delete());
//	}
}