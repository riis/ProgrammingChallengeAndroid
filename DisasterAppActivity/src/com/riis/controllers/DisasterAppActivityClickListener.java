package com.riis.controllers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.riis.R;



public class DisasterAppActivityClickListener 
{
	public void callOnClickListener(final ListView listView)
	{
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
	
}
