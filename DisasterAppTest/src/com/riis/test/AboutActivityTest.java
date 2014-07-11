package com.riis.test;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.riis.AboutActivity;
import com.riis.DisasterAppActivity;
import com.riis.R;

public class AboutActivityTest extends ActivityInstrumentationTestCase2<AboutActivity>
{
	private AboutActivity aboutActivity;
	private Context context;
	private Button returnToMainScreenButton;
	private TextView addContactAboutIcon;
	private TextView addContactAboutLabel;
	private TextView addListAboutIcon;
	private TextView addListAboutLabel;
	private TextView importContactsAboutIcon;
	private TextView importContactsAboutLabel;
	private TextView readMessagesAboutIcon;
	private TextView readMessagesAboutLabel;
	
	public AboutActivityTest()
	{
		super(AboutActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		Intent intent = new Intent(context, DisasterAppActivity.class);
		setActivityIntent(intent);
		aboutActivity = getActivity();
		
		addContactAboutIcon = (TextView) aboutActivity.findViewById(R.id.addContactAboutIcon);
		addContactAboutLabel = (TextView) aboutActivity.findViewById(R.id.addContactAboutLabel);
		addListAboutIcon = (TextView) aboutActivity.findViewById(R.id.addListAboutIcon);
		addListAboutLabel = (TextView) aboutActivity.findViewById(R.id.addListAboutLabel);
		importContactsAboutIcon = (TextView) aboutActivity.findViewById(R.id.importContactsAboutIcon);
		importContactsAboutLabel = (TextView) aboutActivity.findViewById(R.id.importContactsAboutLabel);
		readMessagesAboutIcon = (TextView) aboutActivity.findViewById(R.id.readMessagesAboutIcon);
		readMessagesAboutLabel = (TextView) aboutActivity.findViewById(R.id.readMessagesAboutLabel);
		returnToMainScreenButton = (Button) aboutActivity.findViewById(R.id.returnToMainScreenButton);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testAddContactIconExists()
	{
		assertNotNull(addContactAboutIcon);
	}
	
	public void testAddContactLabelExists()
	{
		assertNotNull(addContactAboutLabel);
	}
	
	public void testAddListAboutIconExists()
	{
		assertNotNull(addListAboutIcon);
	}
	
	public void testAddListAboutLabelExists()
	{
		assertNotNull(addListAboutLabel);
	}
	
	public void testImportContactsAboutIconExists()
	{
		assertNotNull(importContactsAboutIcon);
	}
	
	public void testImportContactsAboutLabelExists()
	{
		assertNotNull(importContactsAboutLabel);
	}
	
	public void testReadMessagesAboutIconExists()
	{
		assertNotNull(readMessagesAboutIcon);
	}
	
	public void testReadMessagesAboutLabelExists()
	{
		assertNotNull(readMessagesAboutLabel);
	}
	
	public void testReturnToMainScreenButtonExists()
	{
		assertNotNull(returnToMainScreenButton);
	}
	
	public void testGoToHomeScreen()
	{
		ActivityMonitor monitor = getInstrumentation().addMonitor(DisasterAppActivity.class.getName(), null, true);
		
		TouchUtils.clickView(this, returnToMainScreenButton);
		
		monitor.waitForActivityWithTimeout(1000);
		assertEquals(1, monitor.getHits());
		
		getInstrumentation().removeMonitor(monitor);
	}
}