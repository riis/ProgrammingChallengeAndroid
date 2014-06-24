package com.riis;

import java.util.ArrayList;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.riis.controllers.ContactListDisplayItemClickListener;
import com.riis.controllers.ContactListSelectionItemClickListener;
import com.riis.controllers.ImportedContactsAdapter;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactImporter;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;

import dagger.ObjectGraph;

public class ImportContactsActivity  extends Activity
{
	private ContactImporter importer;
	private ListView listView;
	
	@Inject ContactListDisplayItemClickListener item;
	@Inject ContactList contactList;
	private ArrayList<Contact> contacts;
	@Inject ContactList everyoneList;
	boolean contactExists;
	
	Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ObjectGraph objectGraph = ((DaggerApplication) getApplication()).getDisasterAppObjectGraph();
		objectGraph.inject(this);
		contacts = new ArrayList<Contact>();
		contactExists=false;
        setContentView(R.layout.import_contacts_screen);
		  
	    fetchContacts();

        contactList.readAllContacts();
        everyoneList.setName("Everyone");
        everyoneList.read();
        
	    listView = (ListView) findViewById(R.id.importedContactsListView);        
        listView.setAdapter(new ImportedContactsAdapter(this, contacts));
        listView.setOnItemClickListener(new ContactListSelectionItemClickListener());
		
	}
	
	public void returnToMainScreen(View view)
	{
		finish();
	}
	
	public void importContacts(View view)
	{
		for(int i=0; i <contacts.size(); i++)
		{
			everyoneList.read();
			CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectedContactCheckBox);

			if(checkBox.isChecked())
			{
				for(int j=0; j<everyoneList.size();j++)
				{	
					
					    if(contacts.get(i).getFirstName().equals(everyoneList.getContact(j).getFirstName()) 
							&& contacts.get(i).getLastName().equals(everyoneList.getContact(j).getLastName()))
					    {
							contactExists=true;
							break;
						}else{
							contactExists =false;
						}
				}
				Log.e("check checkbox", " checked?"+ checkBox.isChecked() + " exists?"+ contactExists);
				
				if(contactExists==false)
				{
				contacts.get(i).create();
	
				everyoneList.addContact(contacts.get(i));
				everyoneList.update();
				
				ResponseMessage response = new ResponseMessage(this);
		        response.setTextMessageContents(" Are you OK?");
		        response.setPhoneNumber(contacts.get(i).getPhoneNumber());
		        response.setContactListId(1);
		        response.create();
				
				}
			}
		}
		finish();
	}
	
	private void fetchContacts() 
	{
		String phoneNumber = null;
		String email = null;
		

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String PhoneCONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String ADDRESS = ContactsContract.CommonDataKinds.Email.ADDRESS;
		


		Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);	
		int j =0;
		if (cursor.moveToFirst()) 
		{
			while (!cursor.isAfterLast()) 
			{
				Contact newContact = new Contact(this);
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				newContact.parseName(name);
				
					Cursor emailCursor = getContentResolver().query(EmailCONTENT_URI, null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null); 
						while (emailCursor.moveToNext()) 
						{
							email = emailCursor.getString(emailCursor.getColumnIndex(ADDRESS));
							newContact.setEmailAddress(email);
						}
					    emailCursor.close();
				
					Cursor phoneCursor = getContentResolver().query(PhoneCONTENT_URI, null, PhoneCONTACT_ID+ " = ?", new String[] { contact_id }, null); 
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
	}



	
	
}

