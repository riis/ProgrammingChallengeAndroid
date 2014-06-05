package com.riis.dagger;

import android.app.Application;
import dagger.ObjectGraph;

public class DaggerApplication extends Application
{
	private ObjectGraph objectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		objectGraph = ObjectGraph.create(new SendEmergencyMessageObjectGraph());
	}

	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}

	public void setObjectGraph(ObjectGraph objectGraph) {
		this.objectGraph = objectGraph;
	}
}
