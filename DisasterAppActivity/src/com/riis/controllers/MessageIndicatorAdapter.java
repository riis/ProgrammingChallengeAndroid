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
import com.riis.models.ResponseMessage;

public class MessageIndicatorAdapter extends ArrayAdapter<Contact>{
	
	private ResponseMessageDataSource dataSource;
	private Context context;
	private ArrayList<Contact> values;

	public MessageIndicatorAdapter(Context context, ArrayList<Contact> values) {
		super(context, R.layout.message_indicator_list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.message_indicator_list_item, parent, false);
		
		TextView nameView = (TextView) rowView.findViewById(R.id.indicatorListName);
		nameView.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
		
		TextView indicatorView = (TextView) rowView.findViewById(R.id.indicatorListValue);
		
		dataSource = new ResponseMessageDataSource(context);
		dataSource.open();
		
		ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
		responseMessages = dataSource.getResponseMessageList();
		
		dataSource.close();
		
		boolean flag = false;
		
		for(int i = 0; i < responseMessages.size(); i++) {
			if(responseMessages.get(i).getPhoneNumber().equals(values.get(position).getPhoneNumber())) {
				if(values.get(position).getMessageSentTimeStamp().equals("")) {
					indicatorView.setText("Responded");
					indicatorView.setTextColor(Color.GREEN);
					flag = true;
				}
			}
		}
		
		if(!values.get(position).getMessageSentTimeStamp().equals("")) {
			if(!flag) {
				indicatorView.setText("Not Responded");
				indicatorView.setTextColor(Color.RED);

			}
		} else if(!flag)
			indicatorView.setVisibility(View.GONE);

		return rowView;
	}
}