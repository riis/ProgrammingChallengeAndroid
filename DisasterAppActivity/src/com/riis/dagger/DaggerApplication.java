package com.riis.dagger;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;

public class DaggerApplication extends Application
{
	private ObjectGraph sendEmergencyMessageObjectGraph;
	private ObjectGraph disasterAppObjectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Context context = getApplicationContext();
		disasterAppObjectGraph = ObjectGraph.create(new DisasterAppObjectGraph(context));
		sendEmergencyMessageObjectGraph = ObjectGraph.create(new SendEmergencyMessageObjectGraph(context));
	}
	
	public ObjectGraph getDisasterAppObjectGraph() {
		return disasterAppObjectGraph;
	}

	public void setDisasterAppObjectGraph(ObjectGraph disasterAppObjectGraph) {
		this.disasterAppObjectGraph = disasterAppObjectGraph;
	}

	public ObjectGraph getSendEmergencyMessageObjectGraph() {
		return sendEmergencyMessageObjectGraph;
	}

	public void setSendEmergencyMessageObjectGraph(ObjectGraph sendEmergencyMessageObjectGraph) {
		this.sendEmergencyMessageObjectGraph = sendEmergencyMessageObjectGraph;
	}
}
