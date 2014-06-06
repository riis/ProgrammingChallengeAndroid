package com.riis.controllers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.riis.models.ResponseMessage;

public class ResponseMessageDataSource {

	private SQLiteDatabase database;
	private DisasterAppSQLiteHelper dbHelper;
	
	public ResponseMessageDataSource(Context context) {
		dbHelper = new DisasterAppSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public ResponseMessage createResponseMessage(ResponseMessage responseMessage) {
		ContentValues values = new ContentValues();
		values.put("phoneNumber", responseMessage.getPhoneNumber());
		values.put("timeStamp", responseMessage.getTimeStamp());
		values.put("textMessageContents", responseMessage.getTextMessageContents());
		
		long insertId = database.insert("responseMessage", null, values);
		
		Cursor cursor = database.query("responseMessage", null, "_id = "+ insertId, null, null, null, null);
		cursor.moveToFirst();
		responseMessage = convertCursorToResponseMessage(cursor);
		cursor.close();
		return responseMessage;
	}
	
	public void deleteResponseMessage(ResponseMessage responseMessage) {
		String phoneNumber = responseMessage.getPhoneNumber();
		database.delete("responseMessage", "phoneNumber = '"+ phoneNumber + "'", null);
	}
	
	public ResponseMessage updateResponseMessage(ResponseMessage response) {
		ContentValues values = new ContentValues();
		values.put("phoneNumber", response.getPhoneNumber());
		values.put("timeStamp", response.getTimeStamp());
		values.put("textMessageContents", response.getTextMessageContents());
		
		long updateId = database.update("responseMessage", values, "phoneNumber = '"+ response.getPhoneNumber() +"'", null);
		
		Cursor cursor = database.query("contact", null, "_id = "+ updateId, null, null, null, null);
		cursor.moveToFirst();
		response = convertCursorToResponseMessage(cursor);
		cursor.close();
		
		return response;
	}
	
	public ResponseMessage getResponseMessage(int index) {
		Cursor cursor = database.query("responseMessage", null, "_id = "+ index, null, null, null, null);
		
		cursor.moveToLast();
		ResponseMessage responseMessage = convertCursorToResponseMessage(cursor);
		cursor.close();
		
		return responseMessage;
	}
	
	public ArrayList<ResponseMessage> getResponseMessageList() {
		ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
		
		Cursor cursor = database.query("responseMessage", null, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			responseMessages.add(convertCursorToResponseMessage(cursor));
			cursor.moveToNext();
		}
		
		cursor.close();
		return responseMessages;
	}
	
	private ResponseMessage convertCursorToResponseMessage(Cursor cursor) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setPhoneNumber(cursor.getString(1));
		responseMessage.setTimeStamp(cursor.getString(2));
		responseMessage.setTextMessageContents(cursor.getString(3));
		return responseMessage;
	}
}