package com.riis.controllers.contactListSelection;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.util.Log;
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

public class ContactSelectionAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private String name;
	private ArrayList<Contact> values;
	@Inject ContactList list;
	
	private static class ViewHolder {
		TextView nameView;
		TextView emailView;
		TextView phoneView;
		CheckBox selectContactCheckBox;
	}
	
	public ContactSelectionAdapter(Context context, ArrayList<Contact> values, String name, Application app)
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
		ViewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.select_contacts_list_item, parent, false);
			holder = new ViewHolder();
			
			holder.nameView = (TextView) row.findViewById(R.id.selectContactListName);
			holder.emailView = (TextView) row.findViewById(R.id.selectContactListEmail);
			holder.phoneView = (TextView) row.findViewById(R.id.selectContactListPhoneNumber);
			holder.selectContactCheckBox = (CheckBox) row.findViewById(R.id.selectContactCheckBox);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());

		holder.emailView.setText(values.get(position).getEmailAddress());
		
		holder.phoneView.setText(values.get(position).getPhoneNumber());
		
		holder.selectContactCheckBox.setChecked(false);
		
		if(!name.equals(""))
		{
			list.setName(name);
			list.read();
			
			for(int i = 0; i < list.size(); i++)
			{
				Log.i("id", list.getContact(i).getId() +"");
				if(values.get(position).getId() == list.getContact(i).getId())
				{
					holder.selectContactCheckBox.setChecked(true);
				}
			}
		}
		
		return row;
	}
}