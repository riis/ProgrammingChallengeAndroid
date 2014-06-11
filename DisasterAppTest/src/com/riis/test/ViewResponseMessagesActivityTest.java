package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.riis.R;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;


public class ViewResponseMessagesActivityTest extends ActivityInstrumentationTestCase2<ViewResponseMessagesActivity>
{
	private ViewResponseMessagesActivity viewResponseMessagesActivity;
	private Button returnToMainScreenButton;
	private ListView responseMessagesListView;
	
	private Contact contact;
	private Context context;
	
	public ViewResponseMessagesActivityTest() 
	{
		super(ViewResponseMessagesActivity.class);
	}
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		context = this.getInstrumentation()
				.getTargetContext().getApplicationContext();
		viewResponseMessagesActivity = getActivity();
		
		returnToMainScreenButton = (Button) viewResponseMessagesActivity
				.findViewById(R.id.returnToMainScreenButton);
		responseMessagesListView = (ListView) viewResponseMessagesActivity
				.findViewById(R.id.responseMessagesListView);
		
		contact = new Contact(context);
		contact.setFirstName("Robert");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
	}
	
	public void testListViewPopulates() 
	{
		viewResponseMessagesActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				ResponseMessage response = new ResponseMessage(context);
	        	response.setPhoneNumber("5555555555");
	        	response.setTextMessageContents("This is a test");
	        	response.updateMessageSentTimeStamp();
	        	response.create();
	    		
				contact.create();
				
				ContactList contactList = new ContactList(context);
				contactList.read();
				responseMessagesListView.setAdapter(new ResponseMessagesAdapter(viewResponseMessagesActivity.getApplicationContext(),
						contactList.getContacts()));
			}
		});
		try 
		{
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertTrue(responseMessagesListView.getCount() > 0);
		
		ResponseMessage response = new ResponseMessage(context);
    	response.setPhoneNumber("5555555555");
    	response.setTextMessageContents("This is a test");
    	response.updateMessageSentTimeStamp();
				
		contact.read();
		contact.delete();
		response.read();
		response.delete();		
	}
	
	public void testResponseMessagesListViewExists() 
	{
		assertNotNull(responseMessagesListView);
	}
	
	public void testReturnToMainScreenButtonExists() 
	{
		assertNotNull(returnToMainScreenButton);
	}
}