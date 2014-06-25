package com.riis.controllers.contactListDisplay;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.riis.R;

public class ContactListDisplayItemClickListener implements OnItemClickListener
{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		View expand = parent.getChildAt(position).findViewById(R.id.contactListMemberLayout);
		if(expand.getVisibility() == View.GONE)
		{
			for(int i = 0; i < parent.getCount(); i++)
			{
				View others = parent.getChildAt(i).findViewById(R.id.contactListMemberLayout);
				others.setVisibility(View.GONE);
			}
			expand.setVisibility(View.VISIBLE);
		}
		else
		{
			expand.setVisibility(View.GONE);
		}
	}
}