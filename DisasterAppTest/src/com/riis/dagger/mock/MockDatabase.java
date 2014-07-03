package com.riis.dagger.mock;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.riis.models.BasePersistentModel;

public abstract class MockDatabase extends BasePersistentModel
{
	private static final String DATABASE_NAME = "disasterAppTest.db";
	private static final int DATABASE_VERSION = 1;

	protected SQLiteDatabase database;
	
	private static final String CONTACT_TABLE_CREATE_STMT = "create table "
			+ "contact(_id integer primary key autoincrement, "
			+ "firstName text not null, "
			+ "lastName text not null, "
			+ "emailAddress text not null, "
			+ "phoneNumber integer not null, "
			+ "pingCount integer not null);";
	
	private static final String RESPONSE_MESSAGE_TABLE_CREATE_STMT = "create table "
			+ "responseMessage(_id integer primary key autoincrement, "
			+ "referenceId integer not null, "
			+ "timeStamp integer not null, "
			+ "messageContents text not null);";
	
	private static final String CONTACT_LIST_TABLE_CREATE_STMT = "create table "
			+ "contactList(_id integer primary key autoincrement, "
			+ "name text not null, "
			+ "messageSentTimeStamp integer not null);";
	
	private static final String CONTACT_LIST_MEMBERS_TABLE_CREATE_STMT = "create table "
			+ "contactListMembers(_id integer primary key autoincrement, "
			+ "contactListId integer not null, "
			+ "contactId integer not null);";
	
	public MockDatabase(Context context)
	{
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(CONTACT_TABLE_CREATE_STMT);
		database.execSQL(RESPONSE_MESSAGE_TABLE_CREATE_STMT);
		database.execSQL(CONTACT_LIST_TABLE_CREATE_STMT);
		database.execSQL(CONTACT_LIST_MEMBERS_TABLE_CREATE_STMT);
		database.execSQL("insert into contactList (name, messageSentTimeStamp) values ('Everyone', 0)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		database.execSQL("DROP TABLE IF EXISTS contact");
		database.execSQL("DROP TABLE IF EXISTS responseMessage");
		database.execSQL("DROP TABLE IF EXISTS contactList");
		database.execSQL("DROP TABLE IF EXISTS contactListMembers");
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