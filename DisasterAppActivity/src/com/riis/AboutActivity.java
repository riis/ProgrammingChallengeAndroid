package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_screen);
    }
	
	public void returnToMainScreen(View view)
	{
		Intent i = new Intent(this, DisasterAppActivity.class);
		startActivity(i);
		finish();
	}
}