package com.riis.controllers.contactListSelection;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.riis.R;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class ContactListSelectionAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private String name;
	private ArrayList<Contact> values;
	@Inject ContactList list;
	

	private static class ViewHolder
	{
		TextView nameView;
		TextView emailView;
		TextView phoneView;
		CheckBox selectContactCheckBox;
	}
	
	public ContactListSelectionAdapter(Context context, ArrayList<Contact> values, String name, Application app)
	{
		super(context, R.layout.select_contacts_list_item, values);
		
		ObjectGraph objectGraph = ((DaggerApplication) app).getEditContactListMembersObjectGraph();
		objectGraph.inject(this);
		
		this.values = values;
		this.name = name;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent)
	{
		//ViewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.select_contacts_list_item, parent, false);
			//holder = new ViewHolder();
			

//			TextView nameView = (TextView) row.findViewById(R.id.selectContactListName);
//			TextView emailView = (TextView) row.findViewById(R.id.selectContactListEmail);
//			TextView phoneView = (TextView) row.findViewById(R.id.selectContactListPhoneNumber);
//			CheckBox selectContactCheckBox = (CheckBox) row.findViewById(R.id.selectContactCheckBox);

		}
		else
		{
			//holder = (ViewHolder) row.getTag();
		}
		

		TextView nameView = (TextView) row.findViewById(R.id.selectContactListName);
		TextView emailView = (TextView) row.findViewById(R.id.selectContactListEmail);
		TextView phoneView = (TextView) row.findViewById(R.id.selectContactListPhoneNumber);
		CheckBox selectContactCheckBox = (CheckBox) row.findViewById(R.id.selectContactCheckBox);

		nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());

		emailView.setText(values.get(position).getEmailAddress());
		
		phoneView.setText(values.get(position).getPhoneNumber());
		
		selectContactCheckBox.setChecked(false);
		
		if(!name.equals(""))
		{
			list.setName(name);
			list.read();
			
			for(int i = 0; i < list.size(); i++)
			{
				if(values.get(position).getId() == list.getContact(i).getId())
				{
					selectContactCheckBox.setChecked(true);
				}
			}
		}
		
		return row;
	}
}