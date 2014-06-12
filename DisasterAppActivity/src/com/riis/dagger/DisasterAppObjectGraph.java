package com.riis.dagger;

import android.content.Context;

import com.riis.DisasterAppActivity;
import com.riis.models.ContactList;

import dagger.Module;
import dagger.Provides;

@Module(injects=DisasterAppActivity.class)
public class DisasterAppObjectGraph 
{
	Context context;
	
	public DisasterAppObjectGraph(Context context)
	{
		this.context = context;
	}
	
	@Provides ContactList provideContactList() 
	{
		return new ContactList(context);
	}
}
