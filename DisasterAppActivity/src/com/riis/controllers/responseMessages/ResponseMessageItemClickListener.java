package com.riis.controllers.responseMessages;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.riis.R;
import com.riis.controllers.DropDownListAnimation;

public class ResponseMessageItemClickListener implements OnItemClickListener
{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		for(int i = 0; i < parent.getCount(); i++)
		{
			if(parent.getChildAt(i) != null)
			{
				View expand = parent.getChildAt(i).findViewById(R.id.responseMessagesListLayout);
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) expand.getLayoutParams();
				
				if(params.bottomMargin == 0)
				{
					View otherTitle = parent.getChildAt(i).findViewById(R.id.responseMessagesListItem);
					otherTitle.setBackgroundColor(Color.WHITE);
					
					DropDownListAnimation animation = new DropDownListAnimation(expand, 500);
					expand.startAnimation(animation);
				}
			}
		}
		
		View expand = view.findViewById(R.id.responseMessagesListLayout);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) expand.getLayoutParams();
		
		if(params.bottomMargin < 0)
		{
			View listTitle = parent.getChildAt(position).findViewById(R.id.responseMessagesListItem);
			listTitle.setBackgroundColor(Color.LTGRAY);
		}
		
		DropDownListAnimation animation = new DropDownListAnimation(expand, 500);
		expand.startAnimation(animation);
	}
}