package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.ContactDetailsActivity;
import com.riis.CreateContactListsActivity;
import com.riis.DisasterAppActivity;
import com.riis.EditContactListMembersActivity;
import com.riis.R;
import com.riis.ViewResponseMessagesActivity;
import com.riis.dagger.DaggerApplication;
import com.riis.dagger.DisasterAppTestObjectGraph;

import dagger.ObjectGraph;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity>
{
	private DisasterAppActivity disasterAppActivity;
	
	private ListView contactListDisplay;
	private Context context;
	
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
	}
	
	protected void tearDown() throws Exception
	{
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
		ActivityMonitor monitor = getInstrumentation().addMonitor(EditContactListMembersActivity.class.getName(), null, true);
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.editContactListButton));
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContactButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(ContactDetailsActivity.class.getName(), null, true);
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createContactItem, 0);
		
		monitor.waitForActivityWithTimeout(500);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void testCreateContactListButtonIntent()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(CreateContactListsActivity.class.getName(), null, true);
		
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(disasterAppActivity, R.id.createContactListItem, 0);
		
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
		assertTrue(contactListDisplay.getCount() > 0);	
	}
	
	public void testListItemExpands()
	{
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue));
		
		int visiblility = View.VISIBLE;
		int expandedLayout = contactListDisplay.getChildAt(0).findViewById(R.id.contactListMemberLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testListItemCollapses()
	{
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue));
		
		TouchUtils.clickView(this, contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue));
		
		int visiblility = View.GONE;
		int expandedLayout = contactListDisplay.getChildAt(0).findViewById(R.id.contactListMemberLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testListViewNamePopulates()
	{
		String name = ((TextView) contactListDisplay.getChildAt(0).findViewById(R.id.contactListNameValue))
				.getText().toString();
		assertEquals("Test", name);
	}
}