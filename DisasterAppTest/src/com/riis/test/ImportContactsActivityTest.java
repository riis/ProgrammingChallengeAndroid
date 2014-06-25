package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.riis.ImportContactsActivity;
import com.riis.R;
import com.riis.dagger.DaggerApplication;
import com.riis.dagger.ImportContactsTestObjectGraph;

import dagger.ObjectGraph;


public class ImportContactsActivityTest extends ActivityInstrumentationTestCase2<ImportContactsActivity>
{
	private Context context;
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
		context = this.getInstrumentation().getTargetContext().getApplicationContext();
		
		ObjectGraph objectGraph= ObjectGraph.create(new ImportContactsTestObjectGraph(context));
		DaggerApplication myapp = (DaggerApplication) this.getInstrumentation().
				getTargetContext().getApplicationContext();
		myapp.setImportContactsObjectGraph(objectGraph);
		
		importContactsActivity = getActivity();
		
		cancelButton = (Button) importContactsActivity.findViewById(R.id.returnButton);
		saveButton = (Button) importContactsActivity.findViewById(R.id.saveImportedContactsButton);
		listView = (ListView) importContactsActivity.findViewById(R.id.importedContactsListView);
	}
	
	protected void tearDown() throws Exception
	{
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