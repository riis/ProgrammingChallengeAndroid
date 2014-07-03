package com.riis.test;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.EditContactListMembersActivity;
import com.riis.R;
import com.riis.dagger.DaggerApplication;
import com.riis.dagger.EditContactListMembersTestObjectGraph;

import dagger.ObjectGraph;

public class EditContactListMembersActivityTest extends ActivityInstrumentationTestCase2<EditContactListMembersActivity>
{
	private EditContactListMembersActivity editContactListMembersActivity;
	private Button updateContactListButton;
	private Button cancelCreateContactListButton;
	private ListView contactsListView;
	private TextView contactListTextView;
	
	private Context context;
	
	public EditContactListMembersActivityTest()
	{
		super(EditContactListMembersActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		ObjectGraph objectGraph= ObjectGraph.create(new EditContactListMembersTestObjectGraph(context));
		DaggerApplication myapp = (DaggerApplication) this.getInstrumentation().
				getTargetContext().getApplicationContext();
		myapp.setEditContactListMembersObjectGraph(objectGraph);
		
		Intent intent = new Intent(context, EditContactListMembersActivity.class);
		intent.putExtra("CONTACT_LIST_NAME", "Test");
		setActivityIntent(intent);
		editContactListMembersActivity = getActivity();
		
		contactListTextView = (TextView) editContactListMembersActivity.findViewById(R.id.contactListNameLabel);
		contactsListView = (ListView) editContactListMembersActivity.findViewById(R.id.createContactListsView);
		updateContactListButton = (Button) editContactListMembersActivity.findViewById(R.id.saveCreateContactListSaveButton);
		cancelCreateContactListButton = (Button) editContactListMembersActivity.findViewById(R.id.cancelCreateContactListButton);
		
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testCheckBoxExists()
	{
		CheckBox box = (CheckBox) contactsListView.getChildAt(0).findViewById(R.id.selectContactCheckBox);
		assertNotNull(box);
	}
	
	public void testCheckBoxIsChecked()
	{
		CheckBox box = (CheckBox) contactsListView.getChildAt(0).findViewById(R.id.selectContactCheckBox);
		assertTrue(box.isChecked());
		box = (CheckBox) contactsListView.getChildAt(1).findViewById(R.id.selectContactCheckBox);
		assertFalse(box.isChecked());
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
	
	public void testUpdateContactList()
	{
//		ContactList list = new ContactList(context);
//		list.setName("Test List");
//		list.create();
//		
//		list.addContact(contact);
//		
//		assertTrue(list.update());
//		
//		list.delete();
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