package com.riis.controllers.contactListSelection;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	private int position;
	private ArrayList<Contact> values;
	private ArrayList<Boolean> checked = new ArrayList<Boolean>();
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
		this.position = position;
		Log.i("position", position +"");
		ViewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.select_contacts_list_item, parent, false);
			holder = new ViewHolder();
			holder.contactName = (TextView) row.findViewById(R.id.selectContactListName);
			holder.emailView = (TextView) row.findViewById(R.id.selectContactListEmail);
			holder.phoneView = (TextView) row.findViewById(R.id.selectContactListPhoneNumber);
			holder.checkBox = (CheckBox) row.findViewById(R.id.selectContactCheckBox);
			
			holder.contactName.setText(values.get(position).getFirstName() +" "+ values.get(position).getLastName());
			holder.emailView.setText(values.get(position).getEmailAddress());
			holder.phoneView.setText(values.get(position).getPhoneNumber());
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
			holder.checkBox.setChecked(true);
		}
		
		holder.checkBox.setOnCheckedChangeListener(new ContactListCheckBoxSelectionChangeListener(position, checked));
		
		if(!name.equals(""))
		{
			holder.checkBox.setChecked(false);
			
			list.setName(name);
			list.read();
			
			for(int i = 0; i < list.size(); i++)
			{
				if(values.get(position).getId() == list.getContact(i).getId())
				{
					holder.checkBox.setChecked(true);
				}
			}
		}
		
		return row;
	}
	
	public void setToggleList(ArrayList<Boolean> list)
	{
        this.checked = list;
        notifyDataSetChanged();
    }
}