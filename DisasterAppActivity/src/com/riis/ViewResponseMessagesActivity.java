package com.riis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ViewResponseMessagesActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_messages_screen);
        
    }
	
	public void returnToMainScreen(View view) {
		finish();
	}
}
