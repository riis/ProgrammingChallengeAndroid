package com.riis.test;

//import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
//import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

//import com.riis.DisasterAppActivity;
import com.riis.NewContactActivity;
import com.riis.R;

public class DisasterAppNewContactTest extends ActivityInstrumentationTestCase2<NewContactActivity>{

	private Button cancelButton;
	private TextView testTextView;
	NewContactActivity newContactActivity = getActivity();
	public DisasterAppNewContactTest() {
		super(NewContactActivity.class);
	}
	
   protected void setUp() throws Exception {
		super.setUp();
		
		
		
		cancelButton = (Button) newContactActivity.findViewById(R.id.Cancel_button);
		
		
	}
	
	public void testButtonExists() {
		assertNotNull(cancelButton);
	}
	
	public void testrandomLabel(){
		testTextView = (TextView) newContactActivity.findViewById(R.id.sampleLabel);
		assertNotNull(testTextView);

	}
	
	public void testBrain()
	{
		testTextView = (TextView)newContactActivity.findViewById(R.id.first_name_editText);
		assertNotNull(testTextView);
		testTextView.setText("Bob");
		assertEquals("Bob", testTextView.getText());
	}
	
	
	
}
