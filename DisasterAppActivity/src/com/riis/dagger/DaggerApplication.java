package com.riis.dagger;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;

public class DaggerApplication extends Application
{
	private ObjectGraph objectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Context context = getApplicationContext();
		objectGraph = ObjectGraph.create(new SendEmergencyMessageObjectGraph(context));
	}

	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}

	public void setObjectGraph(ObjectGraph objectGraph) {
		this.objectGraph = objectGraph;
	}
}
