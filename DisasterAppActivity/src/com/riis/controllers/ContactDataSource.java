package com.riis.controllers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.riis.models.Contact;

public class ContactDataSource {

	private SQLiteDatabase database;
	private DisasterAppSQLiteHelper dbHelper;
	private String[] allColumns = {"_id", "firstName", "lastName", "emailAddress", "phoneNumber", "messageSentTimeStamp"};
	
	public ContactDataSource(Context context) {
		dbHelper = new DisasterAppSQLiteHelper(context);
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
		values.put("messageSentTimeStamp", contact.getMessageSentTimeStamp());
		
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
	
	public Contact updateContact(Contact contact) {
		ContentValues values = new ContentValues();
		values.put("firstName", contact.getFirstName());
		values.put("lastName", contact.getLastName());
		values.put("emailAddress", contact.getEmailAddress());
		values.put("phoneNumber", contact.getPhoneNumber());
		values.put("messageSentTimeStamp", contact.getMessageSentTimeStamp());
		
		long updateId = database.update("contact", values, "emailAddress = '"+ contact.getEmailAddress() +"'", null);
		
		Cursor cursor = database.query("contact", allColumns, "_id = "+ updateId, null, null, null, null);
		cursor.moveToFirst();
		contact = convertCursorToContact(cursor);
		cursor.close();
		
		return contact;
	}
	
	public Contact getContact() {
		Cursor cursor = database.query("contact", allColumns, null, null, null, null, null);
		cursor.moveToLast();
		Contact contact = convertCursorToContact(cursor);
		cursor.close();
		return contact;
	}
	
	public ArrayList<Contact> getContactList() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
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
		Contact contact = new Contact(cursor.getLong(0));
		contact.setFirstName(cursor.getString(1));
		contact.setLastName(cursor.getString(2));
		contact.setEmailAddress(cursor.getString(3));
		contact.setPhoneNumber(cursor.getString(4));
		contact.setMessageSentTimeStamp(cursor.getString(5));
		return contact;
	}

}
