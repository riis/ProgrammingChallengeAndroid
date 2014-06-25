package com.riis.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactImporter 
{
	Context context;
	//Contact contact;

	public void fetchContacts(Contact contact, ContentResolver contentResolver) 
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
		
		if (cursor.moveToFirst()) 
		{
			while (!cursor.isAfterLast()) 
			{
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

				contact.setFirstName("");
				contact.setLastName("");
				contact.setPhoneNumber("");
				contact.setEmailAddress("");
				
				contact.parseName(name);
				
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null); 
						while (emailCursor.moveToNext()) 
						{
							email = emailCursor.getString(emailCursor.getColumnIndex(ADDRESS));
							contact.setEmailAddress(email);
						}
					    emailCursor.close();
				
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, PhoneCONTACT_ID+ " = ?", new String[] { contact_id }, null); 
						while (phoneCursor.moveToNext()) 
						{
							phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)).substring(2);
							contact.setPhoneNumber(phoneNumber);
						}
					    phoneCursor.close();
						    
		        if(!contact.getFirstName().isEmpty() && !contact.getLastName().isEmpty() 
		   		 && !contact.getEmailAddress().isEmpty() && !contact.getPhoneNumber().isEmpty())
		   			{
		   			//populate view 
		        	Log.i("contact info"," FN:"+contact.getFirstName() +"\n LN:"+ contact.getLastName() +"\n Email:"+ contact.getEmailAddress()+"\n PhoneNumber:"+ contact.getPhoneNumber());
					Log.i(" ","\n ");
		   			}	
				cursor.moveToNext();
			}
	
		}cursor.close();
	
	}
	
}
