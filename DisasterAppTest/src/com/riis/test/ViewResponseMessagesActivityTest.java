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
	
	public void testResponseMessagesListViewExists() {
		assertNotNull(responseMessagesListView);
	}
	
	public void testReturnToMainScreenButtonExists() {
		assertNotNull(returnToMainScreenButton);
	}
}