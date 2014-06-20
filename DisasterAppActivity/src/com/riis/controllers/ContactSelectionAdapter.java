package com.riis.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.riis.R;
import com.riis.models.Contact;

public class ContactSelectionAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private ArrayList<Contact> values;
	
	public ContactSelectionAdapter(Context context, ArrayList<Contact> values)
	{
		super(context, R.layout.select_contacts_list_item, values);
		this.values = values;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.select_contacts_list_item, parent, false);
		
		TextView nameView = (TextView) rowView.findViewById(R.id.selectContactListName);
		nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());

		TextView emailView = (TextView) rowView.findViewById(R.id.selectContactListEmail);
		emailView.setText(values.get(position).getEmailAddress());
		
		TextView phoneView = (TextView) rowView.findViewById(R.id.selectContactListPhoneNumber);
		phoneView.setText(values.get(position).getPhoneNumber());
		
		return rowView;
	}
}