package com.riis.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.riis.ImportContactsActivity;
import com.riis.R;


public class ImportContactsActivityTest extends ActivityInstrumentationTestCase2<ImportContactsActivity>
{

	private ImportContactsActivity importContactsActivity;
	private Button cancelButton;
	private Button saveButton;
	private ListView listView;
	
	
	public ImportContactsActivityTest()
	{
		super(ImportContactsActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		importContactsActivity = getActivity();
		cancelButton = (Button) importContactsActivity.findViewById(R.id.returnButton);
		saveButton = (Button) importContactsActivity.findViewById(R.id.saveImportedContactsButton);
		listView = (ListView) importContactsActivity.findViewById(R.id.importedContactsListView);
	}
	
	protected void tearDown() throws Exception
	{
		importContactsActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run()
			{
				
			}
		});
		
		Thread.sleep(30);
		super.tearDown();
	}
	
	public void testCancelButtonExists() 
	{
		assertNotNull(cancelButton);
	}
	
	public void testSaveButtonExists() 
	{
		assertNotNull(saveButton);
	}
	
	public void testListViewExists() 
	{
		assertNotNull(listView);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
