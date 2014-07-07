package com.riis.controllers.contactListDisplay;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.riis.ContactDetailsActivity;
import com.riis.EditContactListMembersActivity;
import com.riis.R;
import com.riis.SendEmergencyMessageActivity;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

import dagger.ObjectGraph;

public class ContactListDisplayAdapter extends ArrayAdapter<ContactList>
{
	private Context context;
	private ArrayList<ContactList> values;
	private Button editContactButton;
	@Inject ContactList currentContactList;
	@Inject ResponseMessageList responseMessageList;

	private static class ViewHolder
	{
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
			
			holder.editContactListButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View view) 
				{
					View parent = (View) view.getParent();
					String name = ((TextView) parent.findViewById(R.id.contactListNameValue)).getText().toString();
					Intent intent = new Intent(context, EditContactListMembersActivity.class);
					intent.putExtra("CONTACT_LIST_NAME", name);
					context.startActivity(intent);
				}
			});
			
			holder.sendMessageToContactList.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View view) 
				{
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
		
		if(currentContactList.size() == 0)
		{
			TextView display = new TextView(context);
			display.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			display.setGravity(Gravity.CENTER);
			holder.sendMessageToContactList.setVisibility(View.INVISIBLE);
			display.setText("There are no contacts in this list! Please add a contact!");
			holder.listLayout.addView(display);
		}
		else
		{
			responseMessageList = new ResponseMessageList(context);
			responseMessageList.read(currentContactList.getId());
		}
		
		for(Contact c : currentContactList.getContacts())
		{
			StringBuilder builder = new StringBuilder();
			TextView display = new TextView(context);
			display.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			display.setGravity(Gravity.CENTER);
			
			Drawable img = getContext().getResources().getDrawable( R.drawable.patch_standard_button );
			Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
			Drawable d = new BitmapDrawable(getContext().getResources(), Bitmap.createScaledBitmap(bitmap, 450, 150, true));

			editContactButton = new Button(context);
			editContactButton.setText("Edit Contact");

			editContactButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			editContactButton.setBackground(d);


			ContactReference ref = new ContactReference(context);
			ref.setContactListId(currentContactList.getId());
			ref.setContactId(c.getId());
			ref.read();
			
			builder = buildNoMessageText(c);
			for(ResponseMessage m : responseMessageList.getResponseMessage())
			{
				if(m.getReferenceId() == ref.getId())
				{
					editContact(c);
					
					if(m.getTimeStamp() != 0L)
					{
						builder = buildRespondedText(m, c);
						display.setTextColor(Color.GREEN);
					}
					else if(currentContactList.getMessageSentTimeStamp() != 0)
					{
						builder = buildUnrespondedText(c);
						display.setTextColor(Color.RED);
					}
					else
					{
						builder = buildNoMessageText(c);
					}
					break;
				}
			}
			RelativeLayout layout = new RelativeLayout(context);
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
			editContactButton.setLayoutParams(buttonParams);
			layout.addView(editContactButton);
			
			RelativeLayout.LayoutParams displayParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			displayParams.addRule(RelativeLayout.LEFT_OF, editContactButton.getId());
			display.setText(builder.toString());

			display.setLayoutParams(displayParams);
			display.setTextSize(16);
			
			layout.addView(display);
			
			holder.listLayout.addView(layout); 
		}
		return row;
	}

	private void editContact(final Contact c) 
	{
		editContactButton.setId(Integer.parseInt(""+ c.getId()));
		editContactButton.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{ 
				Intent intent = new Intent(context, ContactDetailsActivity.class);
				intent.putExtra("id", c.getId());
				context.startActivity(intent);
			}
		});
	}
	
	private StringBuilder buildRespondedText(ResponseMessage message, Contact contact)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(message.getMessageContents());
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