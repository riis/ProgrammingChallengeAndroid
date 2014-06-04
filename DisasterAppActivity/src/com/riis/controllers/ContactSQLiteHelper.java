package com.riis.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactSQLiteHelper extends SQLiteOpenHelper {
	
	private static final String CONTACT_DATABASE_NAME = "contact.db";
	private static final int CONTACT_DATABASE_VERSION = 1;
	
	private static final String CONTACT_DATABASE_CREATE_STMT = "create table "
			+ "contact(_id integer primary key autoincrement, "
			+ "firstName text not null, "
			+ "lastName text not null, "
			+ "emailAddress text not null, "
			+ "phoneNumber integer not null, "
			+ "messageSentTimeStamp text not null);";

	public ContactSQLiteHelper(Context context) {
		super(context, CONTACT_DATABASE_NAME, null, CONTACT_DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CONTACT_DATABASE_CREATE_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS contact");
		onCreate(database);
	}

}
