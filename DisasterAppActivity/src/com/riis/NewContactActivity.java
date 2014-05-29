package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewContactActivity extends Activity
{
	
    /** Called when the new contact button is pressed. */
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.newcontact);
    }
	
	public void cancelCreateContact(View view) {
		Intent intent = new Intent(this, DisasterAppActivity.class);
		startActivity(intent);
	}
}
