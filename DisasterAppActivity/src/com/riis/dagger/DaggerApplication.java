package com.riis.dagger;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;

public class DaggerApplication extends Application
{
	private ObjectGraph createContactListsObjectGraph;
	private ObjectGraph disasterAppObjectGraph;
	private ObjectGraph editContactListMembersObjectGraph;
	private ObjectGraph newContactObjectGraph;
	private ObjectGraph sendEmergencyMessageObjectGraph;
	private ObjectGraph viewResponseMessagesObjectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Context context = getApplicationContext();
		createContactListsObjectGraph = ObjectGraph.create(new CreateContactListsObjectGraph(context));
		disasterAppObjectGraph = ObjectGraph.create(new DisasterAppObjectGraph(context));
		editContactListMembersObjectGraph = ObjectGraph.create(new EditContactListMembersObjectGraph(context));
		newContactObjectGraph = ObjectGraph.create(new NewContactObjectGraph(context));
		sendEmergencyMessageObjectGraph = ObjectGraph.create(new SendEmergencyMessageObjectGraph(context));
		viewResponseMessagesObjectGraph = ObjectGraph.create(new ViewResponseMessagesObjectGraph(context));
	}
	
	public ObjectGraph getCreateContactListsObjectGraph()
	{
		return createContactListsObjectGraph;
	}

	public void setCreateContactListsObjectGraph(ObjectGraph createContactListsObjectGraph)
	{
		this.createContactListsObjectGraph = createContactListsObjectGraph;
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
	
	public ObjectGraph getViewResponseMessagesObjectGraph()
	{
		return viewResponseMessagesObjectGraph;
	}

	public void setViewResponseMessagesObjectGraph(ObjectGraph viewResponseMessagesObjectGraph)
	{
		this.viewResponseMessagesObjectGraph = viewResponseMessagesObjectGraph;
	}
}
