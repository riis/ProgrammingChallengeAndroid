package com.riis.controllers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.riis.R;

public class ResponseMessageItemClickListener implements OnItemClickListener
{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		View expand = parent.getChildAt(position).findViewById(R.id.responseMessagesListLayout);
		if(expand.getVisibility() == View.GONE)
		{
			for(int i = 0; i < parent.getCount(); i++)
			{
				View others = parent.getChildAt(i).findViewById(R.id.responseMessagesListLayout);
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
