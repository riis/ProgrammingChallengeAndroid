package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;

public class DisasterAppActivityTest extends ActivityInstrumentationTestCase2<DisasterAppActivity> {
	
	private Button createContactScreenButton;
	private TextView sampleLabel;

	public DisasterAppActivityTest() {
		super(DisasterAppActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		DisasterAppActivity disasterAppActivity = getActivity();
		
		createContactScreenButton = (Button) disasterAppActivity.findViewById(R.id.createContactScreenButton);
		sampleLabel = (TextView) disasterAppActivity.findViewById(R.id.sampleLabel);
	}
	
	public void testSampleLabelExists() {
		assertNotNull(sampleLabel);
	}
	
	public void testCreateContactScreenButtonExists() {
		assertNotNull(createContactScreenButton);
	}
	

	public void testCreateContactButtonIntent() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
		
		TouchUtils.clickView(this, createContactScreenButton);
		
		monitor.waitForActivityWithTimeout(5000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
}
