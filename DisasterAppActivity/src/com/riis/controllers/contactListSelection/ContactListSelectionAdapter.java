package com.riis.controllers.contactListSelection;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.riis.R;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactList;

import dagger.ObjectGraph;

public class ContactListSelectionAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private String name;
	private ArrayList<Contact> values;
	private ArrayList<Boolean> checked = new ArrayList<Boolean>();
	private ArrayList<ViewHolder> holders = new ArrayList<ViewHolder>();
	@Inject ContactList list;
	
	private static class ViewHolder
	{
		TextView contactName;
		TextView emailView;
		TextView phoneView;
		CheckBox checkBox;
	}
	
	public ContactListSelectionAdapter(Context context, ArrayList<Contact> values, String name, Application app)
	{
		super(context, R.layout.select_contacts_list_item, values);
		
		ObjectGraph objectGraph = ((DaggerApplication) app).getCRUDContactListObjectGraph();
		objectGraph.inject(this);
		
		this.values = values;
		this.name = name;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent)
	{
		final int p = position;
		ViewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.select_contacts_list_item, parent, false);
			holder = new ViewHolder();

			holder.contactName = (TextView) row.findViewById(R.id.selectContactListName);
			holder.contactName.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
			
			holder.emailView = (TextView) row.findViewById(R.id.selectContactListEmail);
			holder.emailView.setText(values.get(position).getEmailAddress());
			
			holder.phoneView = (TextView) row.findViewById(R.id.selectContactListPhoneNumber);
			holder.phoneView.setText(values.get(position).getPhoneNumber());
			
			holder.checkBox = (CheckBox) row.findViewById(R.id.selectContactCheckBox);
			checked.add(false);
			
			holders.add(position, holder);
			
			row.setTag(holder);
		}
		else
		{
			if(holder == null)
			{
				holder = new ViewHolder();
				
				holder.contactName = (TextView) row.findViewById(R.id.selectContactListName);
				holder.contactName.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
				
				holder.emailView = (TextView) row.findViewById(R.id.selectContactListEmail);
				holder.emailView.setText(values.get(position).getEmailAddress());
				
				holder.phoneView = (TextView) row.findViewById(R.id.selectContactListPhoneNumber);
				holder.phoneView.setText(values.get(position).getPhoneNumber());
				
				holder.checkBox = (CheckBox) row.findViewById(R.id.selectContactCheckBox);
				checked.add(false);
				
				holders.add(position, holder);
				
				row.setTag(holder);
			}
			
			holder = holders.get(position);
			holder.checkBox.setChecked(checked.get(p));
			row.setTag(holder);
		}
		
		holder.checkBox.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checked.set(p, !checked.get(p));
			}
		});
		
		if(!name.equals(""))
		{
			list.setName(name);
			list.read();
			
			for(int i = 0; i < list.size(); i++)
			{
				if(values.get(position).getId() == list.getContact(i).getId())
				{
					holder.checkBox.setChecked(true);
					checked.set(p, true);
				}
			}
		}
		
		return row;
	}
	
	public ArrayList<Boolean> getChecked()
	{
		return checked;
	}
}