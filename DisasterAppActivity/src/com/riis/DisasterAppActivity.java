package com.riis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.riis.controllers.MessageIndicatorAdapter;
import com.riis.models.ContactList;

public class DisasterAppActivity extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ContactList contactList = new ContactList(this);
        contactList.read();
                
        ListView listView = (ListView) findViewById(R.id.contactIndicatorListView);        
        listView.setAdapter(new MessageIndicatorAdapter(this, contactList.getContacts()));
    }
	
	@Override
	protected void onResume() 
	{
	   super.onResume();
	   onCreate(null);
	}
	
	public void expandOrCollapseContact(View view)
	{
		TextView emailView = (TextView) view.findViewById(R.id.indicatorListEmail);
		TextView phoneView = (TextView) view.findViewById(R.id.indicatorListPhoneNumber);
		
		if(emailView.getVisibility() == View.GONE)
		{
			ListView parent = (ListView) view.getParent();
			
			for(int i = 0; i < parent.getCount(); i++)
			{
				parent.getChildAt(i).findViewById(R.id.indicatorListEmail).setVisibility(View.GONE);
				parent.getChildAt(i).findViewById(R.id.indicatorListPhoneNumber).setVisibility(View.GONE);
			}
			
			emailView.setVisibility(View.VISIBLE);
			phoneView.setVisibility(View.VISIBLE);
		}
		else
		{
			emailView.setVisibility(View.GONE);
			phoneView.setVisibility(View.GONE);
		}
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