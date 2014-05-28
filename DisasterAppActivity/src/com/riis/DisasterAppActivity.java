package com.riis;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DisasterAppActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void createContactScreen() {
    	Intent intent = new Intent(this, NewContactActivity.class);
    	startActivity(intent);
    }
}