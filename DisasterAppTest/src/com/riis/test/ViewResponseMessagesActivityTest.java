package com.riis.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.riis.R;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.DisasterAppDataSource;
import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.models.Contact;
import com.riis.models.ResponseMessage;


public class ViewResponseMessagesActivityTest extends ActivityInstrumentationTestCase2<ViewResponseMessagesActivity>{

	private ViewResponseMessagesActivity viewResponseMessagesActivity;
	private Button returnToMainScreenButton;
	private ListView responseMessagesListView;
	
	private Contact contact;
	
	public ViewResponseMessagesActivityTest() {
		super(ViewResponseMessagesActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		viewResponseMessagesActivity = getActivity();
		
		returnToMainScreenButton = (Button) viewResponseMessagesActivity
				.findViewById(R.id.returnToMainScreenButton);
		responseMessagesListView = (ListView) viewResponseMessagesActivity
				.findViewById(R.id.responseMessagesListView);
		
		contact = new Contact();
		contact.setFirstName("Robert");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
	}
	
	public void testListViewPopulates() {
		viewResponseMessagesActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				DisasterAppDataSource dataSource = new DisasterAppDataSource(
						viewResponseMessagesActivity.getApplicationContext());
				
				ResponseMessage response = new ResponseMessage();
	        	response.setPhoneNumber("5555555555");
	        	response.setTextMessageContents("This is a test");
	        	response.updateMessageSentTimeStamp();
	        	
	        	dataSource.open();
	        	dataSource.createResponseMessage(response);
				
				dataSource.createContact(contact);
				responseMessagesListView.setAdapter(new ResponseMessagesAdapter(viewResponseMessagesActivity.getApplicationContext(),
						dataSource.getContactList()));
				dataSource.close();
			}
		});
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(responseMessagesListView.getCount() > 0);
		
		ResponseMessage response = new ResponseMessage();
    	response.setPhoneNumber("5555555555");
    	response.setTextMessageContents("This is a test");
    	response.updateMessageSentTimeStamp();
		
		DisasterAppDataSource dataSource = new DisasterAppDataSource(viewResponseMessagesActivity.getApplicationContext());
		dataSource.open();
		
		dataSource.deleteContact(contact);
		dataSource.deleteResponseMessage(response);
		
		dataSource.close();
	}
	
	public void testResponseMessagesListViewExists() {
		assertNotNull(responseMessagesListView);
	}
	
	public void testReturnToMainScreenButtonExists() {
		assertNotNull(returnToMainScreenButton);
	}
}