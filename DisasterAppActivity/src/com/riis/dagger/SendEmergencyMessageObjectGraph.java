package com.riis.dagger;

import android.content.Context;

import com.riis.SendEmergencyMessageActivity;
import com.riis.models.ContactList;
import com.riis.models.TextMessageSender;

import dagger.Module;
import dagger.Provides;

@Module(injects=SendEmergencyMessageActivity.class)
public class SendEmergencyMessageObjectGraph 
{
	Context context;
	
	SendEmergencyMessageObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList() 
	{
		return new ContactList(context);
	}
	
	@Provides TextMessageSender provideTextMessageSender() 
	{
		return new TextMessageSender();
	}
}
