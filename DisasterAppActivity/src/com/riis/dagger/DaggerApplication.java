package com.riis.dagger;

import dagger.ObjectGraph;
import android.app.Application;

public class DaggerApplication extends Application
{
	private ObjectGraph objectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		objectGraph = ObjectGraph.create(new DisasterAppObjectGraph());
	}

	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}

	public void setObjectGraph(ObjectGraph objectGraph) {
		this.objectGraph = objectGraph;
	}
}
