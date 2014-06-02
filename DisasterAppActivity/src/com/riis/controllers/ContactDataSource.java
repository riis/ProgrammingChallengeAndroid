package com.riis.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.riis.models.Contact;

public class ContactDataSource {
	
	private SQLiteDatabase database;
	private ContactSQLiteHelper dbHelper;
	private String[] allColumns = {"_id", "firstName", "lastName", "emailAddress", "phoneNumber"};
	
	
	
	public ContactDataSource(Context context) {
		dbHelper = new ContactSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Contact createContact(Contact contact) {
		ContentValues values = new ContentValues();
		values.put("firstName", contact.getFirstName());
		values.put("lastName", contact.getLastName());
		values.put("emailAddress", contact.getEmailAddress());
		values.put("phoneNumber", contact.getPhoneNumber());
		
		long insertId = database.insert("contact", null, values);
		
		Cursor cursor = database.query("contact", allColumns, "_id = "+ insertId, null, null, null, null);
		cursor.moveToFirst();
		contact = convertCursorToContact(cursor);
		cursor.close();
		return contact;
	}
	
	public void deleteContact(Contact contact) {
		long id = contact.getId();
		database.delete("contact", "_id = "+ id, null);
	}
	
	public Contact getContact() {
		Contact contact = new Contact();
		
		Cursor cursor = database.query("contact", allColumns, null, null, null, null, null);
		
		cursor.moveToLast();
		contact = convertCursorToContact(cursor);
		cursor.close();
		
		return contact;
	}
	
	public List<Contact> getContactList() {
		List<Contact> contacts = new ArrayList<Contact>();
		
		Cursor cursor = database.query("contact", allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			contacts.add(convertCursorToContact(cursor));
			cursor.moveToNext();
		}
		
		cursor.close();
		return contacts;
	}
	
	private Contact convertCursorToContact(Cursor cursor) {
		Contact contact = new Contact();
		contact.setId(cursor.getLong(0));
		contact.setFirstName(cursor.getString(1));
		contact.setLastName(cursor.getString(2));
		contact.setEmailAddress(cursor.getString(3));
		contact.setPhoneNumber(cursor.getString(4));
		return contact;
	}

}
