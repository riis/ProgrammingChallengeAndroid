package com.riis.dagger;

import com.riis.SendEmergencyMessageActivity;
import com.riis.models.ContactList;
import com.riis.models.TextMessageSender;

import dagger.Module;
import dagger.Provides;

@Module (injects=SendEmergencyMessageActivity.class)
public class DisasterTestObjectGraph 
{
	@Provides ContactList provideContactList() 
	{
		return new MockContactList();
	}
	@Provides TextMessageSender provideTextMessageSender() 
	{
		return new MockTextMessageSender();
	}

}
