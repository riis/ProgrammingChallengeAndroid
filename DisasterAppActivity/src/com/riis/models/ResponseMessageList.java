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
		return false;
	}

	@Override
	public boolean delete() 
	{
		return false;
	}

	@Override
	public boolean read() 
	{
		String[] columns = {"_id"};
		
		open();
		Cursor cursor = database.query("responseMessage", columns, null, null, null, null, "timeStamp DESC");

		boolean returnVal = cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			ResponseMessage next = new ResponseMessage(context);
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
	
	public boolean read(long contactListId)
	{
		open();
		String t = "SELECT * FROM responseMessage r INNER JOIN contactListMembers c ON c._id=r.referenceId WHERE c.contactListId="
				+ contactListId +" ORDER BY r.timeStamp DESC";
		
		Cursor cursor = database.rawQuery(t, null);
		boolean returnVal = cursor.moveToFirst();
		responseMessage = new ArrayList<ResponseMessage>();
		while (!cursor.isAfterLast()) 
		{
			ResponseMessage next = new ResponseMessage(context);
			boolean success = next.read(cursor.getLong(0));
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
	
	public boolean allContactsResponded(long contactListId)
	{
		open();
		String t = "SELECT * FROM responseMessage r INNER JOIN contactListMembers c ON c._id=r.referenceId WHERE c.contactListId="
				+ contactListId +" ORDER BY r.timeStamp DESC";
		
		Cursor cursor = database.rawQuery(t, null);
		cursor.moveToFirst();
		boolean returnVal = false;
		while (!cursor.isAfterLast()) 
		{
			ResponseMessage next = new ResponseMessage(context);
			boolean success = next.read(cursor.getLong(0));
			
			if (success)
			{
				if(next.getTimeStamp() != 0)
				{
					returnVal = true;
				}
				else
				{
					cursor.close();
					close();
					return false;
				}
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
}