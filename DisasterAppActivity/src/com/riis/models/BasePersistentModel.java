package com.riis.models;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public abstract class BasePersistentModel extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "disasterApp.db";
	private static final int DATABASE_VERSION = 1;

	protected SQLiteDatabase database;
	
	private static final String CONTACT_TABLE_CREATE_STMT = "create table "
			+ "contact(_id integer primary key autoincrement, "
			+ "firstName text not null, "
			+ "lastName text not null, "
			+ "emailAddress text not null, "
			+ "phoneNumber integer not null, "
			+ "messageSentTimeStamp integer not null, "
			+ "pingCount integer not null);";
	
	private static final String RESPONSE_MESSAGE_TABLE_CREATE_STMT = "create table "
			+ "responseMessage(_id integer primary key autoincrement, "
			+ "phoneNumber text not null, "
			+ "timeStamp integer not null, "
			+ "textMessageContents text not null);";

	public BasePersistentModel(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(CONTACT_TABLE_CREATE_STMT);
		database.execSQL(RESPONSE_MESSAGE_TABLE_CREATE_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		database.execSQL("DROP TABLE IF EXISTS contact");
		database.execSQL("DROP TABLE IF EXISTS responseMessage");
		onCreate(database);
	}
	
	protected void open() throws SQLException 
	{
		database = getWritableDatabase();
	}
	
	public void close() 
	{
		database.close();
	}
	
	abstract public boolean create();
	abstract public boolean delete();
	abstract public boolean read();
	abstract public boolean update();
}