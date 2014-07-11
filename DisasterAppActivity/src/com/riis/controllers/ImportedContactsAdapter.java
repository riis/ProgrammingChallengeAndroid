package com.riis.controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riis.R;
import com.riis.models.Contact;
import com.riis.models.ContactList;

public class ImportedContactsAdapter extends ArrayAdapter<Contact>
{
	private Context context;
	private int size;
	private ArrayList<Contact> values;
	private ArrayList<Boolean> checked = new ArrayList<Boolean>();
	private ArrayList<LinearLayout.LayoutParams> params = new ArrayList<LinearLayout.LayoutParams>();
	private ArrayList<ViewHolder> holders = new ArrayList<ViewHolder>();
	@Inject ContactList list;
	
	private static class ViewHolder
	{
		LinearLayout parent;
		TextView contactName;
		TextView emailView;
		TextView phoneView;
		CheckBox checkBox;
	}
	
	public ImportedContactsAdapter(Context context, ArrayList<Contact> values)
	{
		super(context, R.layout.select_contacts_list_item, values);
		this.values = values;
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
			
			holder.parent = (LinearLayout) row.findViewById(R.id.selectContactListExpandableLayout);
			size = ((LinearLayout.LayoutParams) row.findViewById(R.id.selectContactListExpandableLayout).getLayoutParams()).bottomMargin;
			
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
				
				holder.parent = (LinearLayout) row.findViewById(R.id.selectContactListExpandableLayout);

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
			row.setBackgroundColor(Color.WHITE);
			row.invalidate();
			
			LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) row.findViewById(R.id.selectContactListExpandableLayout).getLayoutParams();
			
			if(par.bottomMargin == 0)
			{
				
				par.bottomMargin = -36;
				par.setMargins(0, 0, 0, size);
				row.findViewById(R.id.selectContactListExpandableLayout).setVisibility(View.GONE);
				row.findViewById(R.id.selectContactListExpandableLayout).setLayoutParams(par);
			}
		}
		
		holder.checkBox.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checked.set(p, !checked.get(p));
			}
		});
		
		return row;
	}
	
	public ArrayList<Boolean> getChecked()
	{
		return checked;
	}
}