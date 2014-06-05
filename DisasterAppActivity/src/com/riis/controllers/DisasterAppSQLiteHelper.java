package com.riis.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DisasterAppSQLiteHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "contact.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CONTACT_TABLE_CREATE_STMT = "create table "
			+ "contact(_id integer primary key autoincrement, "
			+ "firstName text not null, "
			+ "lastName text not null, "
			+ "emailAddress text not null, "
			+ "phoneNumber integer not null, "
			+ "messageSentTimeStamp text not null);";
	
	private static final String RESPONSE_MESSAGE_TABLE_CREATE_STMT = "create table "
			+ "responseMessage(_id integer primary key autoincrement, "
			+ "phoneNumber text not null, "
			+ "timeStamp text not null, "
			+ "textMessageContents text not null);";

	public DisasterAppSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CONTACT_TABLE_CREATE_STMT);
		database.execSQL(RESPONSE_MESSAGE_TABLE_CREATE_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS contact");
		database.execSQL("DROP TABLE IF EXISTS responseMessage");
		onCreate(database);
	}

}
