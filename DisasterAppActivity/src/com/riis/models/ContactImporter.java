package com.riis.models;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.CheckBox;
import android.widget.ListView;

import com.riis.R;

public class ContactImporter 
{
	Context context;
	private boolean contactExists;

	public ContactImporter(Context context)
	{
		this.context = context;
	}

	public ArrayList<Contact> fetchContacts(ContentResolver contentResolver,  ArrayList<Contact> contacts) 
	{
		String phoneNumber = null;
		String email = null;

		Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);	
		int j = 0;
		if (cursor.moveToFirst()) 
		{
			while (!cursor.isAfterLast()) 
			{
				Contact newContact = new Contact(context);
				
				String contact_id = cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts._ID ));
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				newContact.parseName(name);
				
				Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+ " = ?", new String[] { contact_id }, null); 
				while (emailCursor.moveToNext()) 
				{
					email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
					newContact.setEmailAddress(email);
				}

				emailCursor.close();
			
				Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?", new String[] { contact_id }, null); 
				while (phoneCursor.moveToNext()) 
				{
					phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)).substring(2);
					newContact.setPhoneNumber(phoneNumber);
				}
			    
				phoneCursor.close();
				
		        if(!newContact.getFirstName().isEmpty() && !newContact.getLastName().isEmpty()
		        		&& !newContact.getEmailAddress().isEmpty() && !newContact.getPhoneNumber().isEmpty())
	   			{
					contacts.add(j, newContact);
					j++;
	   			}
					     
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		return contacts;
	}
	
	public void importContacts(ArrayList<Contact> contacts, ContactList everyoneList, ListView listView)
	{
		contactExists = false;
		for(int i = 0; i < contacts.size(); i++)
		{
			everyoneList.read();
			CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectedContactCheckBox);

			if(checkBox.isChecked())
			{
				for(Contact c : everyoneList.getContacts())
				{	
				    if(contacts.get(i).getFirstName().equals(c.getFirstName())
				    		&& contacts.get(i).getLastName().equals(c.getLastName()))
				    {
						contactExists = true;
						break;
					}
				    else
				    {
						contactExists = false;
					}
				}
				
				if(!contactExists)
				{
					contacts.get(i).create();
					
					ContactReference ref = new ContactReference(context);
					ref.setContactListId(1);
					ref.setContactId(contacts.get(i).getId());
					ref.create();
					
					ResponseMessage response = new ResponseMessage(context);
					response.setReferenceId(ref.getId());
			        response.setMessageContents(" Are you OK?");
			        response.create();
				}
			}
		}
	}
}