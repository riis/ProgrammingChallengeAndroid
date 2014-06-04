package com.riis.dagger;

import com.riis.SendEmergencyMessageActivity;
import com.riis.models.ContactList;
import com.riis.models.TextMessageSender;

import dagger.Module;
import dagger.Provides;

@Module(injects=SendEmergencyMessageActivity.class)
public class SendEmergencyMessageObjectGraph {

	@Provides ContactList provideContactList() 
	{
		return new ContactList();
	}
	
	@Provides TextMessageSender provideTextMessageSender() 
	{
		return new TextMessageSender();
	}
}
