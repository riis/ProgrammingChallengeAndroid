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

import com.riis.controllers.ContactSelectionAdapter;
import com.riis.controllers.ContactSelectionItemClickListener;
import com.riis.controllers.ImportedContactsAdapter;
import com.riis.controllers.MessageIndicatorItemClickListener;
import com.riis.dagger.DaggerApplication;
import com.riis.models.Contact;
import com.riis.models.ContactImporter;
import com.riis.models.ContactList;
import com.riis.models.ListOfContactLists;

import dagger.ObjectGraph;

public class ImportContactsActivity  extends Activity
{
	private ContactImporter importer;
	private ListView listView;
	@Inject MessageIndicatorItemClickListener item;
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
        
	    Log.i("check read list", "read list "+everyoneList.read());
	    listView = (ListView) findViewById(R.id.importedContactsListView);        
        listView.setAdapter(new ImportedContactsAdapter(this, contacts));
        listView.setOnItemClickListener(new ContactSelectionItemClickListener());
		
	}
	
	public void returnToMainScreen(View view)
	{
		finish();
	}
	
	public void importContacts(View view)
	{
		for(int i=0; i <contacts.size(); i++)
		{
		CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.selectedContactCheckBox);
		
		Log.i("check size", " "+ everyoneList.size());
			for(int j=0; j<everyoneList.size();j++)
			{	
				Log.i("compare first names", "first guy "+ contacts.get(i).getFirstName() + " vs. " + everyoneList.getContact(j).getFirstName());
			    if( /*checkBox.isChecked() &&*/ contacts.get(i).getFirstName()==everyoneList.getContact(j).getFirstName() 
					&& contacts.get(i).getLastName()==everyoneList.getContact(j).getLastName())
				{
					contactExists=true;
					break;
				}else{
					contactExists =false;
					
				}
			}

			Log.i("check contact exists", " "+ contactExists);
			if(contactExists==false)
			{
			Log.i("contact name"," this person get added:"+contacts.get(i).getFirstName());
			contacts.get(i).create();
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
		  
		        	Log.i("contact info"," FN:"+newContact.getFirstName() +"\n LN:"+ newContact.getLastName() +"\n Email:"+ newContact.getEmailAddress()+"\n PhoneNumber:"+ newContact.getPhoneNumber());
					Log.i(" ","\n ");
					
					contacts.add(j, newContact);
					
					j++;
					Log.i("adding contact", "contact name 0: "+ contacts.get(0).getFirstName());
					Log.i("adding contact", "contact name: "+ j+ " "+contacts.get(j-1).getFirstName());
		   			}	
					    
					    
					    
				cursor.moveToNext();
			}
		}cursor.close();
	}



	
	
}

