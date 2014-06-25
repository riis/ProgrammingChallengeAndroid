package com.riis.dagger.mock;

import android.content.Context;

import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class MockResponseMessageList extends ResponseMessageList
{
	boolean readCall = false;
	Context context;
	ResponseMessageList messages;
	
	public MockResponseMessageList(Context context) {
		super(context);
		this.context = context;
	}
	
	public boolean read(long id)
	{
		readCall = true;
		
		MockResponseMessage message = new MockResponseMessage(context);
		message.setPhoneNumber("1234567890");
		message.setTextMessageContents("Yes I am.");
		message.updateMessageSentTimeStamp();
		
		messages = new ResponseMessageList(context);
		messages.addResponseMessage(message);
		
		return readCall;
	}
	
	public ResponseMessage getResponseMessage(int i)
	{
		if(readCall)
		{
			return messages.getResponseMessage(i);
		}
		
		return null;
	}
}