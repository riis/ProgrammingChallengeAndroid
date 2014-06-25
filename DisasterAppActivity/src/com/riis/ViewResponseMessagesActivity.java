package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.riis.controllers.ResponseMessageItemClickListener;
import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ListOfContactLists;

import dagger.ObjectGraph;

public class ViewResponseMessagesActivity extends Activity
{
	@Inject ListOfContactLists lists;
	@Inject ResponseMessageItemClickListener item;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_messages_screen);
        
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getViewResponseMessagesObjectGraph();
		objectGraph.inject(this);
        
        lists.read();
       
        ListView listView = (ListView) findViewById(R.id.responseMessagesListView);
        listView.setAdapter(new ResponseMessagesAdapter(this, lists.getContactLists(), getApplication()));
        listView.setOnItemClickListener(item);
    }
	
	public void returnToMainScreen(View view)
	{
		finish();
	}
}