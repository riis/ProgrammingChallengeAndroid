package com.riis.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactImporter 
{
	Context context;
	Contact contact;

	public void fetchContacts(ContentResolver contentResolver) 
	{
		String phoneNumber = null;
		String email = null;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String Email_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;

		contact = new Contact(context);
	
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) 
		{
			while (cursor.moveToNext()) 
			{
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
				contact.parseName(name);
				
				if (hasPhoneNumber > 0)
				{
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

						while (phoneCursor.moveToNext()) 
						{
							phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
							contact.setPhoneNumber(phoneNumber);
						}
						phoneCursor.close();
						
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, Email_CONTACT_ID + " = ?", new String[] { contact_id }, null);
						while (emailCursor.moveToNext()) 
						{
							email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
							contact.setEmailAddress(email);
						}
						emailCursor.close();
				}
				contact.create();
			}
		}
		
	}

	
}
