package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
		DaggerApplication myapp = (DaggerApplication) context;
		myapp.setImportContactsObjectGraph(objectGraph);
		
		importContactsActivity = getActivity();
		
		cancelButton = (Button) importContactsActivity.findViewById(R.id.cancelImportContactsButton);
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
	
	public void testCheckBoxExists()
	{
		CheckBox box = (CheckBox) listView.getChildAt(0).findViewById(R.id.selectContactCheckBox);
		assertNotNull(box);
	}
	
	public void testCheckBoxIsNotChecked()
	{
		CheckBox box = (CheckBox) listView.getChildAt(0).findViewById(R.id.selectContactCheckBox);
		assertFalse(box.isChecked());
		box = (CheckBox) listView.getChildAt(1).findViewById(R.id.selectContactCheckBox);
		assertFalse(box.isChecked());
	}
	
	public void testListViewPopulates()
	{
		assertTrue(listView.getCount() > 0);
	}
	
	public void testListItemExpands()
	{
		TouchUtils.clickView(this, listView.getChildAt(0));
		
		int visiblility = View.VISIBLE;
		int expandedLayout = listView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
	
	public void testListItemCollapses()
	{
		TouchUtils.clickView(this, listView.getChildAt(0));
		
		TouchUtils.clickView(this, listView.getChildAt(0));
		
		int visiblility = View.INVISIBLE;
		int expandedLayout = listView.getChildAt(0).findViewById(R.id.selectContactListExpandableLayout).getVisibility();
		
		assertEquals(expandedLayout, visiblility);
	}
}