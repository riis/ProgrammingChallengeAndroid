package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity
{
	private TextView iconKey;
	private TextView addContactText;
	private TextView addListText;
	private TextView importContactsText;
	private TextView readMessagesText;
	private TextView addContactIcon;
	private TextView addListIcon;
	private TextView importContactsIcon;
	private TextView readMessagesIcon;
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_screen);
		iconKey = (TextView) findViewById(R.id.iconKeyAboutLabel);
		addContactText = (TextView) findViewById(R.id.addContactAboutLabel);
		addListText = (TextView) findViewById(R.id.addListAboutLabel);
		importContactsText = (TextView) findViewById(R.id.importContactsAboutLabel);
		readMessagesText = (TextView) findViewById(R.id.readMessagesAboutLabel);
		
		addContactIcon = (TextView) findViewById(R.id.addContactAboutIcon);
		addListIcon = (TextView) findViewById(R.id.addListAboutIcon);
		importContactsIcon = (TextView) findViewById(R.id.importContactsAboutIcon);
		readMessagesIcon = (TextView) findViewById(R.id.readMessagesAboutIcon);
        
    }
	
	public void returnToMainScreen(View view)
	{
		Intent i = new Intent();
		i = new Intent(this, DisasterAppActivity.class);
    	startActivity(i);
    	finish();
		
	}
}
