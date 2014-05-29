package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;

public class DisasterAppNewContactTest extends ActivityInstrumentationTestCase2<NewContactActivity>{

	private Button cancelButton;
	private TextView firstNameEditText;
	private TextView sampleLabel;
	
	public DisasterAppNewContactTest() {
		super(NewContactActivity.class);
	}
	
   protected void setUp() throws Exception {
		super.setUp();
		
		NewContactActivity newContactActivity = getActivity();
		
		sampleLabel = (TextView) newContactActivity.findViewById(R.id.sampleLabel);
		firstNameEditText = (TextView) newContactActivity.findViewById(R.id.first_name_editText);
		cancelButton = (Button) newContactActivity.findViewById(R.id.Cancel_button);
	}
	
	public void testCancelButtonExists() {
		assertNotNull(cancelButton);
	}
	
	public void testrandomLabel(){
		assertNotNull(sampleLabel);

	}
	
	public void testBrain()
	{
		assertNotNull(firstNameEditText);
		firstNameEditText.setText("Bob");
		assertEquals("Bob", firstNameEditText.getText());
	}
	
//	public void testCancelButtonIntent() {
//		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, false);
//		
//		TouchUtils.clickView(this, cancelButton);
//		
//		monitor.waitForActivityWithTimeout(5000);
//		assertEquals(1, monitor.getHits());
//		
//		getInstrumentation().removeMonitor(monitor);
//	}
	
}
