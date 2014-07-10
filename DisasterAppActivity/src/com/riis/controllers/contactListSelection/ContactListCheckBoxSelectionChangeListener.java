package com.riis.controllers.contactListSelection;

import java.util.ArrayList;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ContactListCheckBoxSelectionChangeListener implements OnCheckedChangeListener
{
	private ArrayList<Boolean> checked;
	private int position;
	
	public ContactListCheckBoxSelectionChangeListener(int position, ArrayList<Boolean> checked)
	{
		this.position = position;
		this.checked = checked;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if(checked.get(position) != null)
		{
			checked.set(position, isChecked);
		}
		else
		{
			checked.add(position, isChecked);
		}
	}
}