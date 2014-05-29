package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DisasterAppActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void createContactScreen(View view) {
    	Intent intent = new Intent(this, NewContactActivity.class);
    	startActivity(intent);
    }
}