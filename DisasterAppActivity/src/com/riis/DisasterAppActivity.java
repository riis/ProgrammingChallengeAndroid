package com.riis;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.controllers.MessageIndicatorAnimation;
import com.riis.dagger.DaggerApplication;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class DisasterAppActivity extends Activity
{
	private ListView listView;
	@Inject ContactList contactList;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getDisasterAppObjectGraph();
		objectGraph.inject(this);
        setContentView(R.layout.main);
        
        contactList.read();
        
        listView = (ListView) findViewById(R.id.contactIndicatorListView);        
        listView.setAdapter(new MessageIndicatorAdapter(this, contactList.getContacts()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				for(int i = 0; i < listView.getCount(); i++)
				{
					View expand = listView.getChildAt(i).findViewById(R.id.indicatorExpandableLayout);
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) expand.getLayoutParams();
					
					if(params.bottomMargin == 0)
					{
						MessageIndicatorAnimation animation = new MessageIndicatorAnimation(expand, 500);
						
						expand.startAnimation(animation);
					}
				}
				
				View expand = view.findViewById(R.id.indicatorExpandableLayout);
				
				MessageIndicatorAnimation animation = new MessageIndicatorAnimation(expand, 500);
				
				expand.startAnimation(animation);
			}
        });
    }
	
	@Override
	protected void onResume() 
	{
	   super.onResume();
	   onCreate(null);
	}
	 
	public void createContactScreen(View view) 
	{
		Intent intent = new Intent(this, NewContactActivity.class);
		startActivity(intent);
	}
    
	public void createEmergencyMessageScreen(View view)
	{
		Intent intent = new Intent(this, SendEmergencyMessageActivity.class);
		startActivity(intent);
	}

	public void viewMessageResponsesScreen(View view) 
	{
		Intent intent = new Intent(this, ViewResponseMessagesActivity.class);
		startActivity(intent);
	}
}