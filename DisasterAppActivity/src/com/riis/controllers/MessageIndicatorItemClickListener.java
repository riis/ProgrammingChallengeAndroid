package com.riis.controllers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.riis.R;

public class MessageIndicatorItemClickListener implements OnItemClickListener
{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
//		for(int i = 0; i < parent.getCount(); i++)
//		{
//			View expand = parent.getChildAt(i).findViewById(R.id.indicatorExpandableLayout);
//			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) expand.getLayoutParams();
//			
//			if(params.bottomMargin == 0)
//			{
//				MessageIndicatorAnimation animation = new MessageIndicatorAnimation(expand, 500);
//				
//				expand.startAnimation(animation);
//			}
//		}
//		
//		View expand = view.findViewById(R.id.indicatorExpandableLayout);
//		
//		MessageIndicatorAnimation animation = new MessageIndicatorAnimation(expand, 500);
//		
//		expand.startAnimation(animation);
	}
}