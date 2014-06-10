package com.riis.models;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class ResponseMessageList extends BasePersistentModel 
{
	private ArrayList<ResponseMessage> responseMessage;
	private Context context;
	
	public ResponseMessageList(Context context) 
	{
		super(context);
		this.context = context;
		responseMessage = new ArrayList<ResponseMessage>();
	}
	
	public int size() 
	{
		return responseMessage.size();
	}
	
	public ResponseMessage getResponseMessage(int index) 
	{
		return responseMessage.get(index);
	}
	
	public void addResponseMessage(ResponseMessage responseMessage) 
	{
		this.responseMessage.add(responseMessage);
	}
	
	public void setResponseMessage(ArrayList<ResponseMessage> responseMessage) 
	{
		this.responseMessage = responseMessage;
	}
	
	public ArrayList<ResponseMessage> getResponseMessage() 
	{
		return responseMessage;
	}

	@Override
	public boolean create() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean read() 
	{
		String[] columns = {"_id"};
		
		open();
		Cursor cursor = database.query("responseMessage", columns, null, null, null, null, null);

		boolean returnVal = cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			ResponseMessage next = new ResponseMessage(this.context);
			boolean success = next.read(cursor.getInt(0));
			if (success)
			{
				responseMessage.add(next);
			}
			else
			{
				returnVal = false;
			}
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return returnVal;
	}

	@Override
	public boolean update() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}