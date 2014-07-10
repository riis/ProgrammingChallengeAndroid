package com.riis.models;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactImporter 
{
	private Context context;

	public ContactImporter(Context context)
	{
		this.context = context;
	}

	public ArrayList<Contact> fetchContacts() 
	{
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		String phoneNumber = null;
		String email = null;
		
		ContactList everyoneList = new ContactList(context);
		everyoneList.readAllContacts();
		
		ContentResolver contentResolver = context.getContentResolver();

		Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Contact newContact = new Contact(context);
			
			String contact_id = cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts._ID ));
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			newContact.parseName(name);
			
			Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID+ " = ?", new String[] {contact_id}, null); 
			while (emailCursor.moveToNext()) 
			{
				email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
				newContact.setEmailAddress(email);
			}

			emailCursor.close();
		
			Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?", new String[] {contact_id}, null); 
			while (phoneCursor.moveToNext()) 
			{
				phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)).substring(2);
				newContact.setPhoneNumber(phoneNumber);
			}
		    
			phoneCursor.close();
			
	        if(!newContact.getFirstName().isEmpty() && !newContact.getLastName().isEmpty()
	        		/*&& !newContact.getEmailAddress().isEmpty()*/ && !newContact.getPhoneNumber().isEmpty())
   			{
	        	if(!newContact.exists())
	        	{
	        		contacts.add(newContact);
	        	}
   			}
				     
			cursor.moveToNext();
		}
		
		cursor.close();
		return contacts;
	}
	
	public void importContacts(ArrayList<Contact> contacts)
	{
		for(Contact c : contacts)
		{
			c.create();
			
			ContactReference ref = new ContactReference(context);
			ref.setContactListId(1);
			ref.setContactId(c.getId());
			ref.create();
			
			ResponseMessage response = new ResponseMessage(context);
			response.setReferenceId(ref.getId());
	        response.setMessageContents(" Are you OK?");
	        response.create();
		}
	}
}