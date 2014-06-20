package com.riis.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.riis.R;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessageList;

public class MessageIndicatorAdapter extends ArrayAdapter<ContactList>
{
	private Context context;
	private ArrayList<Contact> values;
	private ArrayList<ContactList> CLvalues;

	public MessageIndicatorAdapter(Context context, ArrayList<ContactList> CLvalues)
	{
		super(context, R.layout.main_contact_lists_item, CLvalues);
		this.context = context;
		
		this.CLvalues = CLvalues;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.main_contact_lists_item, parent, false);
		
		TextView contactListView = (TextView) rowView.findViewById(R.id.contactListValue);
		contactListView.setText(CLvalues.get(position).getName());
		
		
//		TextView indicatorView = (TextView) rowView.findViewById(R.id.indicatorValue);
		
		if(CLvalues.get(position).size() != 0)
		{
			TextView nameView = (TextView) rowView.findViewById(R.id.contactName);
			nameView.setText(CLvalues.get(position).getContact(0).getFirstName() +" "+ CLvalues.get(position).getContact(0).getLastName());
		}
		
//		TextView timeStampView = (TextView) rowView.findViewById(R.id.lastResponseTimeStamp);
		
		
//		ResponseMessageList responseMessageList = new ResponseMessageList(context);
//		responseMessageList.read();
//		
//		timeStampView.setText(responseMessageList.getResponseMessage(position).getFormattedMessageSentTimeStamp());
	
//		boolean flag = false;
//		for(int i = 0; i < responseMessageList.size(); i++)
//		{
//			if(responseMessageList.getResponseMessage(i).getPhoneNumber().equals(values.get(position).getPhoneNumber()))
//			{
//				if(values.get(position).getMessageSentTimeStamp() == 0L)
//				{
//					indicatorView.setText("Responded");
//					indicatorView.setTextColor(Color.GREEN);
//					flag = true;
//				}
//			}
//		}
		
//		if(values.get(position).getMessageSentTimeStamp() != 0L)
//		{
//			if(!flag)
//			{
//				indicatorView.setText("Not Responded");
//				indicatorView.setTextColor(Color.RED);
//			}
//		} 
//		else if(!flag)
//		{
//			indicatorView.setVisibility(View.INVISIBLE);
//		}

		return rowView;
	}
}