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
import com.riis.models.ResponseMessage;

public class ResponseMessagesAdapter extends ArrayAdapter<Contact>{

	private final Context context;
	private final ArrayList<Contact> values;
	
	public ResponseMessagesAdapter(Context context, ArrayList<Contact> values) {
		super(context, R.layout.response_messages_list_item, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.response_messages_list_item, parent, false);
		
		TextView nameView = (TextView) rowView.findViewById(R.id.listName);
		nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
		
		TextView timeStampView = (TextView) rowView.findViewById(R.id.listTimeStamp);
		TextView messageView = (TextView) rowView.findViewById(R.id.listMessageContent);
		
		DisasterAppDataSource dataSource = new DisasterAppDataSource(context);
		dataSource.open();
		
		ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
		responseMessages = dataSource.getResponseMessageList();
		
		dataSource.close();
		
		for(int i = 0; i < responseMessages.size(); i++) {
			if(responseMessages.get(i).getPhoneNumber().equals(values.get(position).getPhoneNumber())) {
				timeStampView.setText(responseMessages.get(position).getTimeStamp());
				messageView.setText(responseMessages.get(position).getTextMessageContents());
			}
		}

		return rowView;
	}
}