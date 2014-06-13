package com.riis.controllers;

import java.util.ArrayList;

import android.content.Context;
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
		
		ResponseMessageList responseMessages = new ResponseMessageList(context);
		responseMessages.read();
		
		if(responseMessages.size() <= position)
		{ 
			TextView nameView = (TextView) rowView.findViewById(R.id.listName);
			nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());

			TextView timeStampView = (TextView) rowView.findViewById(R.id.listTimeStamp);
			TextView messageView = (TextView) rowView.findViewById(R.id.listMessageContent);
			
			timeStampView.setVisibility(View.GONE);
			messageView.setText("No messages received");
			messageView.setGravity(Gravity.CENTER_HORIZONTAL);
		}
		else
		{
			for(int i = 0; i < values.size(); i++)
			{
				if(responseMessages.getResponseMessage(position).getPhoneNumber().equals(values.get(i).getPhoneNumber()))
				{
				    TextView nameView = (TextView) rowView.findViewById(R.id.listName);
					nameView.setText(values.get(i).getFirstName() +" "+ values.get(i).getLastName());
					
					TextView timeStampView = (TextView) rowView.findViewById(R.id.listTimeStamp);
					TextView messageView = (TextView) rowView.findViewById(R.id.listMessageContent);
					
					timeStampView.setText(responseMessages.getResponseMessage(position).getFormattedMessageSentTimeStamp());
					messageView.setText(responseMessages.getResponseMessage(position).getTextMessageContents());
				    break;
				}
			}
		}

		return rowView;
	}
}