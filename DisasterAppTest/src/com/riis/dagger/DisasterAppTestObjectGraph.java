package com.riis.dagger;

import android.content.Context;

import com.riis.DisasterAppActivity;
import com.riis.controllers.MessageIndicatorItemClickListener;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module (injects=DisasterAppActivity.class)
public class DisasterAppTestObjectGraph
{
	Context context;
	
	public DisasterAppTestObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList() 
	{
		return new MockContactList(context);
	}
	
	@Provides MessageIndicatorItemClickListener provideMessageIndicatorItemClickListener() 
	{
		return new MessageIndicatorItemClickListener();
	}
}
