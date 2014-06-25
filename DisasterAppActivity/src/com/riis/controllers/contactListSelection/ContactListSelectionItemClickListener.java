package com.riis.controllers.contactListSelection;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.riis.R;
import com.riis.controllers.DropDownListAnimation;

public class ContactListSelectionItemClickListener implements OnItemClickListener
{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		for(int i = 0; i < parent.getCount(); i++)
		{
			View expand = parent.getChildAt(i).findViewById(R.id.selectContactListExpandableLayout);

			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) expand.getLayoutParams();
			
			if(params.bottomMargin == 0)
			{
				DropDownListAnimation animation = new DropDownListAnimation(expand, 500);
				
				expand.startAnimation(animation);
			}
		}
		
		View expand = view.findViewById(R.id.selectContactListExpandableLayout);
		
		DropDownListAnimation animation = new DropDownListAnimation(expand, 500);
		
		expand.startAnimation(animation);
	}
}