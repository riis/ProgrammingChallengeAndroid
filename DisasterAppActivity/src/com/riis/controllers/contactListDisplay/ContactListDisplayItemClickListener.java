package com.riis.controllers.contactListDisplay;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.riis.R;

public class ContactListDisplayItemClickListener implements OnItemClickListener
{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		View listTitle = parent.getChildAt(position).findViewById(R.id.main_contact_list_item);

		View expand = parent.getChildAt(position).findViewById(R.id.contactListMemberLayout);
		
		if(expand.getVisibility() == View.GONE)
		{
			for(int i = 0; i < parent.getCount(); i++)
			{
				if(parent.getChildAt(i)!=null)
				{
					View otherTitles = parent.getChildAt(i).findViewById(R.id.main_contact_list_item);
					otherTitles.setBackgroundColor(Color.WHITE);
					View others = parent.getChildAt(i).findViewById(R.id.contactListMemberLayout);
					others.setVisibility(View.GONE);
				}
				
				
			}
			expand.setVisibility(View.VISIBLE);
			listTitle.setBackgroundColor(Color.GRAY);
		}
		else
		{
			listTitle.setBackgroundColor(Color.WHITE);
			expand.setVisibility(View.GONE);
		}
	}
}