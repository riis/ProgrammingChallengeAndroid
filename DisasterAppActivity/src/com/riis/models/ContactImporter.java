package com.riis.models;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String PhoneCONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String ADDRESS = ContactsContract.CommonDataKinds.Email.ADDRESS;

		Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);	
		int j =0;
		if (cursor.moveToFirst()) 
		{
			while (!cursor.isAfterLast()) 
			{
				Contact newContact = new Contact(context);
				
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				newContact.parseName(name);
				
				Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null); 
				while (emailCursor.moveToNext()) 
				{
					email = emailCursor.getString(emailCursor.getColumnIndex(ADDRESS));
					newContact.setEmailAddress(email);
				}
			    emailCursor.close();
			
				Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, PhoneCONTACT_ID+ " = ?", new String[] { contact_id }, null); 
				while (phoneCursor.moveToNext()) 
				{
					phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)).substring(2);
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
		}cursor.close();
		return contacts;
	}
	
	public void importContacts(ArrayList<Contact> contacts, ContactList everyoneList, ListView listView)
	{
		contactExists=false;
		for(int i=0; i < contacts.size(); i++)
		{
			everyoneList.read();
			CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectedContactCheckBox);

			if(checkBox.isChecked())
			{
				for(int j=0; j < everyoneList.size();j++)
				{	
				    if(contacts.get(i).getFirstName().equals(everyoneList.getContact(j).getFirstName())
				    		&& contacts.get(i).getLastName().equals(everyoneList.getContact(j).getLastName()))
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
		
					everyoneList.addContact(contacts.get(i));
					everyoneList.update();
					
					ResponseMessage response = new ResponseMessage(context);
			        response.setTextMessageContents(" Are you OK?");
			        response.setPhoneNumber(contacts.get(i).getPhoneNumber());
			        response.setContactListId(1);
			        response.create();
				}
			}
		}
	}
	
}
