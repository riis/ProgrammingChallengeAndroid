package com.riis.dagger;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;

public class DaggerApplication extends Application
{
	private ObjectGraph disasterAppObjectGraph;
	private ObjectGraph editContactListMembersObjectGraph;
	private ObjectGraph newContactObjectGraph;
	private ObjectGraph sendEmergencyMessageObjectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Context context = getApplicationContext();
		disasterAppObjectGraph = ObjectGraph.create(new DisasterAppObjectGraph(context));
		editContactListMembersObjectGraph = ObjectGraph.create(new EditContactListMembersObjectGraph(context));
		newContactObjectGraph = ObjectGraph.create(new NewContactObjectGraph(context));
		sendEmergencyMessageObjectGraph = ObjectGraph.create(new SendEmergencyMessageObjectGraph(context));
	}
	
	public ObjectGraph getDisasterAppObjectGraph()
	{
		return disasterAppObjectGraph;
	}

	public void setDisasterAppObjectGraph(ObjectGraph disasterAppObjectGraph)
	{
		this.disasterAppObjectGraph = disasterAppObjectGraph;
	}
	
	public ObjectGraph getEditContactListMembersObjectGraph()
	{
		return editContactListMembersObjectGraph;
	}

	public void setEditContactListMembersObjectGraph(ObjectGraph editContactListMembersObjectGraph)
	{
		this.editContactListMembersObjectGraph = editContactListMembersObjectGraph;
	}
	
	public ObjectGraph getNewContactObjectGraph()
	{
		return newContactObjectGraph;
	}
	
	public void setNewContactObjectGraph(ObjectGraph newContactObjectGraph)
	{
		this.newContactObjectGraph = newContactObjectGraph;
	}

	public ObjectGraph getSendEmergencyMessageObjectGraph()
	{
		return sendEmergencyMessageObjectGraph;
	}

	public void setSendEmergencyMessageObjectGraph(ObjectGraph sendEmergencyMessageObjectGraph)
	{
		this.sendEmergencyMessageObjectGraph = sendEmergencyMessageObjectGraph;
	}
}
