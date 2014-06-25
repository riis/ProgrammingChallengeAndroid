package com.riis.controllers.contactListDisplay;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riis.EditContactListMembersActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class ContactListDisplayAdapter extends ArrayAdapter<ContactList>
{
	private Context context;
	private ArrayList<ContactList> values;
	@Inject ContactList currentContactList;
	@Inject ResponseMessageList responseMessageList;

	private static class ViewHolder {
		TextView listLabel;
		Button editContactListButton;
		Button sendMessageToContactList;
		LinearLayout listLayout;
	}
	
	public ContactListDisplayAdapter(Context context, ArrayList<ContactList> values, Application app)
	{
		super(context, R.layout.main_contact_lists_item, values);
		
		ObjectGraph objectGraph = ((DaggerApplication) app).getDisasterAppObjectGraph();
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
			row = inflater.inflate(R.layout.main_contact_lists_item, parent, false);
			holder = new ViewHolder();
			holder.listLabel = (TextView) row.findViewById(R.id.contactListNameValue);
			holder.editContactListButton = (Button) row.findViewById(R.id.editContactListButton);
			holder.sendMessageToContactList = (Button) row.findViewById(R.id.sendMessageContactListButton);
			holder.listLayout = (LinearLayout) row.findViewById(R.id.contactListMemberLayout);
			
			holder.editContactListButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					View parent = (View) view.getParent();
					String name = ((TextView) parent.findViewById(R.id.contactListNameValue)).getText().toString();
					Intent intent = new Intent(context, EditContactListMembersActivity.class);
					intent.putExtra("CONTACT_LIST_NAME", name);
					context.startActivity(intent);
				}
			});
			
			holder.sendMessageToContactList.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					View parent = (View) view.getParent();
					String name = ((TextView) parent.findViewById(R.id.contactListNameValue)).getText().toString();
					Intent intent = new Intent(context, SendEmergencyMessageActivity.class);
					intent.putExtra("CONTACT_LIST_NAME", name);
					context.startActivity(intent);
				}
			});
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.listLayout.removeAllViews();
		
		holder.listLabel = (TextView) row.findViewById(R.id.contactListNameValue);
		holder.listLayout = (LinearLayout) row.findViewById(R.id.contactListMemberLayout);
		
		currentContactList = values.get(position);
		currentContactList.read();
		
		holder.listLabel.setText(currentContactList.getName());
		
		if(currentContactList.getName().equals("Everyone"))
		{
			holder.editContactListButton.setVisibility(View.INVISIBLE);
		}
		else
		{
			holder.editContactListButton.setVisibility(View.VISIBLE);
		}
		
		responseMessageList = new ResponseMessageList(context);
		responseMessageList.read(currentContactList.getId());
		
		if(currentContactList.size() == 0)
		{
			TextView display = new TextView(context);
			display.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			display.setGravity(Gravity.CENTER);
			holder.sendMessageToContactList.setVisibility(View.INVISIBLE);
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
					if(responseMessageList.getResponseMessage(j).getTimeStamp() != 0L)
					{
						builder = buildRespondedText(responseMessageList.getResponseMessage(j),
								currentContactList.getContact(i));
						display.setTextColor(Color.GREEN);
					}
					else if(currentContactList.getMessageSentTimeStamp() != 0)
					{
						builder = buildUnrespondedText(currentContactList.getContact(i));
						display.setTextColor(Color.RED);
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