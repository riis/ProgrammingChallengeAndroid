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

public class MessageRecepiantsListAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private ArrayList<Contact> values;
	
	public MessageRecepiantsListAdapter(Context context, ArrayList<Contact> values) {
		super(context, R.layout.message_recepiants_list_item, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent)
	{
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.message_recepiants_list_item, parent, false);
		}
		
		TextView name = (TextView) row.findViewById(R.id.recepiantNameLabel);
		name.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
		
		return row;
	}
}