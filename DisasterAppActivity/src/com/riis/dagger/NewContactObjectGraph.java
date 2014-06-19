package com.riis.dagger;

import android.content.Context;

import com.riis.NewContactActivity;

import dagger.Module;

@Module(injects=NewContactActivity.class)
public class NewContactObjectGraph
{
	Context context;
	
	public NewContactObjectGraph(Context context)
	{
		this.context = context;
	}
}
