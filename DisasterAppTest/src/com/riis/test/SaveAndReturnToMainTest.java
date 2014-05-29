package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;


public class SaveAndReturnToMainTest extends ActivityInstrumentationTestCase2<NewContactActivity> {

	private Button saveScreenButton;

	public SaveAndReturnToMainTest() {
		super(NewContactActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		NewContactActivity activity = getActivity();
		
		saveScreenButton = (Button) activity.findViewById(R.id.Save_button);
		
	}
	
	public void testSaveButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, saveScreenButton);
		
		monitor.waitForActivityWithTimeout(1000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
	
	public void checkContactInfoTest()
	{
		
	}
	
}
