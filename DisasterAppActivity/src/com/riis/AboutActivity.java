package com.riis;

import android.app.Activity;
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
	
	public void returnHome(View view)
	{
		finish();
	}
}