package com.riis.controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riis.R;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class ResponseMessagesAdapter extends ArrayAdapter<ContactList>
{
	private Context context;
	private ArrayList<ContactList> values;
	@Inject ContactList currentContactList;
	@Inject ResponseMessageList responseMessageList;
	
	private static class ViewHolder {
		TextView nameView;
		LinearLayout listLayout;
	}
	
	public ResponseMessagesAdapter(Context context, ArrayList<ContactList> values, Application app)
	{
		super(context, R.layout.response_messages_list_item, values);
		
		ObjectGraph objectGraph = ((DaggerApplication) app).getViewResponseMessagesObjectGraph();
		objectGraph.inject(this);
		
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent)
	{
		ViewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.response_messages_list_item, parent, false);
			holder = new ViewHolder();
			holder.nameView = (TextView) row.findViewById(R.id.responseListName);
			holder.listLayout = (LinearLayout) row.findViewById(R.id.responseMessagesListLayout);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.nameView = (TextView) row.findViewById(R.id.responseListName);
		holder.listLayout = (LinearLayout) row.findViewById(R.id.responseMessagesListLayout);
		
		holder.listLayout.removeAllViews();
		
		currentContactList = values.get(position);
		currentContactList.read();
		
		holder.nameView.setText(currentContactList.getName());
		
		responseMessageList.read(currentContactList.getId());
		
		if(currentContactList.size() == 0)
		{
			TextView display = new TextView(context);
			display.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			display.setGravity(Gravity.CENTER);
			display.setText("There are no contacts in this list! Please add a contact!");
			holder.listLayout.addView(display);
		}
		
		for(int i = 0; i < currentContactList.size(); i++)
		{
			StringBuilder builder = new StringBuilder();
			TextView display = new TextView(context);
			display.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			display.setGravity(Gravity.CENTER);

			for(int j = 0; j < responseMessageList.size(); j++)
			{
				if(responseMessageList.getResponseMessage(j).getPhoneNumber().equals(currentContactList.getContact(i).getPhoneNumber()))
				{
					if(currentContactList.getMessageSentTimeStamp() != 0L
							&& responseMessageList.getResponseMessage(j).getTimeStamp() != 0L)
					{
						builder = buildRespondedText(responseMessageList.getResponseMessage(j),
								currentContactList.getContact(i));
					}
					else if(currentContactList.getMessageSentTimeStamp() != 0)
					{
						builder = buildUnrespondedText(currentContactList.getContact(i));
					}
					else
					{
						builder = buildNoMessageText(currentContactList.getContact(i));
					}
					
					break;
				}
			}
			
			display.setText(builder.toString());
			holder.listLayout.addView(display);
		}

		return row;
	}
	
	private StringBuilder buildRespondedText(ResponseMessage message, Contact contact)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(message.getTextMessageContents());
		builder.append("("+ contact.getPingCount() +" pings) - ");
		builder.append(contact.getFirstName()+" "+ contact.getLastName());
		builder.append(" - last response: ");
		builder.append(message.getFormattedMessageSentTimeStamp());
		return builder;
	}
	
	private StringBuilder buildUnrespondedText(Contact contact)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Unknown ("+ contact.getPingCount() +" pings) - ");
		builder.append(contact.getFirstName() +" "+ contact.getLastName());
		return builder;
	}
	
	private StringBuilder buildNoMessageText(Contact contact)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(contact.getFirstName() +" "+ contact.getLastName());
		return builder;
	}
}