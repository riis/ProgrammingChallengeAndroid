package com.riis.controllers.responseMessages;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.riis.R;
import com.riis.controllers.contactListDisplay.ResponseMessageSpinnerItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class ResponseMessagesAdapter extends ArrayAdapter<ContactList>
{
	private Context context;
	private ArrayList<ContactList> values;
	private Spinner spinner;
	@Inject ContactList currentContactList;
	@Inject ResponseMessageList responseMessageList;
	
	private static class ViewHolder
	{
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
		else
		{

			for(ResponseMessage m : responseMessageList.getResponseMessage())
			{
				spinner = new Spinner(context);
				
				StringBuilder builder = new StringBuilder();
				TextView display = new TextView(context);
				display.setGravity(Gravity.CENTER);
				
				ContactReference ref = new ContactReference(context);
				ref.read(m.getReferenceId());
				
				if(m.getReferenceId() == ref.getId())
				{
					Contact c = new Contact(context);
					c.read(ref.getContactId());
					
					if(currentContactList.getMessageSentTimeStamp() != 0L && m.getTimeStamp() != 0L)
					{
						builder = buildRespondedText(m, c);
						
						spinner.setId((int) ref.getId());
						
						
						ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
				        		R.array.contactResponseNoteOptions, android.R.layout.simple_spinner_item);
				        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				        
				        spinner.setAdapter(adapter);
				        spinner.setOnItemSelectedListener(new ResponseMessageSpinnerItemClickListener(context, m));
				        
				        spinner.setSelection(ref.getNotes());
					}
					else if(currentContactList.getMessageSentTimeStamp() != 0)
					{
						builder = buildUnrespondedText(c);
					}
					else
					{
						builder = buildNoMessageText(c);
					}
				}
				
				display.setText(builder.toString());
				
				LinearLayout layout = new LinearLayout(context);
				layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				layout.setOrientation(LinearLayout.HORIZONTAL);
				
				LinearLayout.LayoutParams displayParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
				
				if(spinner.getCount() > 0)
				{
					displayParams.weight = 8;
					
					LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
					spinnerParams.weight = 3;
					
					spinner.setLayoutParams(spinnerParams);
					layout.addView(spinner);
				}
				else
				{
					displayParams.weight = 5;
				}
				
				display.setLayoutParams(displayParams);
				
				layout.addView(display);
				
				holder.listLayout.addView(layout);
			}
		}
		return row;
	}
	
	private StringBuilder buildRespondedText(ResponseMessage message, Contact contact)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(contact.getFirstName()+" "+ contact.getLastName());
		builder.append("\nlast response: ");
		builder.append(message.getMessageContents());
		builder.append(" ("+ contact.getPingCount() +" pings)\n");
		builder.append(message.getFormattedMessageSentTimeStamp());
		builder.append("\n");;
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