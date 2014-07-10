package com.riis.controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.riis.R;
import com.riis.controllers.contactListSelection.ContactListCheckBoxSelectionChangeListener;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class ImportedContactsAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private String name;
	private ArrayList<Contact> values;
	private ArrayList<Boolean> checked = new ArrayList<Boolean>();
	private int position;
	@Inject ContactList list;
	
	private static class ViewHolder
	{
		TextView contactName;
		TextView emailView;
		TextView phoneView;
		CheckBox checkBox;
	}
	
	public ImportedContactsAdapter(Context context, ArrayList<Contact> values)
	{
		super(context, R.layout.select_contacts_list_item, values);
		this.values = values;
		this.name = name;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent)
	{
		this.position = position;
		Log.i("position", position +"");
		ViewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.select_contacts_list_item, parent, false);
			holder = new ViewHolder();
			holder.contactName = (TextView) rowView.findViewById(R.id.selectContactListName);
			holder.contactName.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());

			holder.emailView = (TextView) rowView.findViewById(R.id.selectContactListEmail);
			holder.emailView.setText(values.get(position).getEmailAddress());
			
			holder.phoneView = (TextView) rowView.findViewById(R.id.selectContactListPhoneNumber);
			holder.phoneView.setText(values.get(position).getPhoneNumber());
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
			holder.checkBox.setChecked(true);
		}
		
		holder.checkBox.setOnCheckedChangeListener(new ContactListCheckBoxSelectionChangeListener(position, checked));
		
		if(!name.equals(""))
		{
			holder.checkBox.setChecked(false);
			
			list.setName(name);
			list.read();
			
			for(int i = 0; i < list.size(); i++)
			{
				if(values.get(position).getId() == list.getContact(i).getId())
				{
					holder.checkBox.setChecked(true);
				}
			}
		}
		
		
		return row;
	}
	
	public void setToggleList(ArrayList<Boolean> list)
	{
        this.checked = list;
        notifyDataSetChanged();
    }
	
}