package com.riis.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.riis.R;
import com.riis.models.Contact;
import com.riis.models.ResponseMessageList;

public class ResponseMessagesAdapter extends ArrayAdapter<Contact>
{
	private final Context context;
	private final ArrayList<Contact> values;
	
	public ResponseMessagesAdapter(Context context, ArrayList<Contact> values)
	{
		super(context, R.layout.response_messages_list_item, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.response_messages_list_item, parent, false);
		
		TextView nameView = (TextView) rowView.findViewById(R.id.listName);
		nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
		
		TextView timeStampView = (TextView) rowView.findViewById(R.id.listTimeStamp);
		TextView messageView = (TextView) rowView.findViewById(R.id.listMessageContent);
		
		ResponseMessageList responseMessages = new ResponseMessageList(context);
		responseMessages.read();
		Log.i("my log", "gets outside for loop" + 		values.get(position).getFirstName());
		
		boolean flag = false;
		
		for(int i = 0; i < responseMessages.size(); i++)
		{
			if(responseMessages.getResponseMessage(i).getPhoneNumber().equals(values.get(position).getPhoneNumber()))
			{
				timeStampView.setText(responseMessages.getResponseMessage(i).getFormattedMessageSentTimeStamp());
				messageView.setText(responseMessages.getResponseMessage(i).getTextMessageContents());
				flag = true;
			}
		}
		
		if(values.get(position).getMessageSentTimeStamp() != 0L)
		{
			if(!flag)
			{
				timeStampView.setVisibility(View.GONE);
				messageView.setText("No messages received");
				messageView.setGravity(Gravity.CENTER_HORIZONTAL);
			}
		} 
		else if(!flag)
		{
			rowView.setVisibility(View.INVISIBLE);
		}

		return rowView;
	}
}