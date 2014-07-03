package com.riis.dagger;

import android.content.Context;

import com.riis.SendEmergencyMessageActivity;
import com.riis.controllers.textMessage.TextMessageSender;
import com.riis.dagger.mock.MockContactList;
import com.riis.dagger.mock.MockTextMessageSender;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module (injects=SendEmergencyMessageActivity.class)
public class SendEmergencyMessageTestObjectGraph 
{
	Context context;
	
	public SendEmergencyMessageTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList() 
	{
		return new MockContactList(context);
	}
	
	@Provides TextMessageSender provideTextMessageSender() 
	{
		return new MockTextMessageSender();
	}
}