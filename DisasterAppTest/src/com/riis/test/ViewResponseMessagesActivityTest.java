package com.riis.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.riis.R;
import com.riis.ViewResponseMessagesActivity;


public class ViewResponseMessagesActivityTest extends ActivityInstrumentationTestCase2<ViewResponseMessagesActivity>{

	private ViewResponseMessagesActivity viewResponseMessagesActivity;
	private Button returnToMainScreenButton;
	private ListView responseMessagesListView;
	
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
	}
	
//	public void testListViewPopulates() {
//		viewResponseMessagesActivity.runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				ResponseMessageDataSource responseMessageDataSource = new ResponseMessageDataSource(
//						viewResponseMessagesActivity.getApplicationContext());
//				ResponseMessage response = new ResponseMessage();
//	        	response.setPhoneNumber("1234567890");
//	        	response.setTextMessageContents("This is a test");
//	        	response.updateMessageSentTimeStamp();
//	        	responseMessageDataSource.open();
//	        	responseMessageDataSource.createResponseMessage(response);
//	        	responseMessageDataSource.close();
//				ContactDataSource dataSource = new ContactDataSource(viewResponseMessagesActivity.getApplicationContext());
//				dataSource.open();
//				responseMessagesListView.setAdapter(new ResponseMessagesAdapter(viewResponseMessagesActivity.getApplicationContext(),
//						dataSource.getContactList()));
//				dataSource.close();
//			}
//		});
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		assertTrue(responseMessagesListView.getCount() > 0);
//	}
	
	public void testResponseMessagesListViewExists() {
		assertNotNull(responseMessagesListView);
	}
	
	public void testReturnToMainScreenButtonExists() {
		assertNotNull(returnToMainScreenButton);
	}
}