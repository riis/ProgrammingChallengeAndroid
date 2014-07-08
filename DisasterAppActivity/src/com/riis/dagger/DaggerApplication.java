package com.riis.dagger;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;

public class DaggerApplication extends Application
{
	private ObjectGraph disasterAppObjectGraph;
	private ObjectGraph crudContactListObjectGraph;
	private ObjectGraph importContactsObjectGraph;
	private ObjectGraph contactDetailsObjectGraph;
	private ObjectGraph sendEmergencyMessageObjectGraph;
	private ObjectGraph viewResponseMessagesObjectGraph;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Context context = getApplicationContext();
		contactDetailsObjectGraph = ObjectGraph.create(new ContactDetailsObjectGraph(context));
		disasterAppObjectGraph = ObjectGraph.create(new DisasterAppObjectGraph(context));
		crudContactListObjectGraph = ObjectGraph.create(new CRUDContactListObjectGraph(context));
		importContactsObjectGraph = ObjectGraph.create(new ImportContactsObjectGraph(context));
		sendEmergencyMessageObjectGraph = ObjectGraph.create(new SendEmergencyMessageObjectGraph(context));
		viewResponseMessagesObjectGraph = ObjectGraph.create(new ViewResponseMessagesObjectGraph(context));
	}
	
	public ObjectGraph getCRUDContactListObjectGraph()
	{
		return crudContactListObjectGraph;
	}

	public void setCRUDContactListObjectGraph(ObjectGraph crudContactListObjectGraph)
	{
		this.crudContactListObjectGraph = crudContactListObjectGraph;
	}
	
	public ObjectGraph getContactDetailsObjectGraph()
	{
		return contactDetailsObjectGraph;
	}
	
	public void setContactDetailsObjectGraph(ObjectGraph contactDetailsObjectGraph)
	{
		this.contactDetailsObjectGraph = contactDetailsObjectGraph;
	}
	
	public ObjectGraph getDisasterAppObjectGraph()
	{
		return disasterAppObjectGraph;
	}

	public void setDisasterAppObjectGraph(ObjectGraph disasterAppObjectGraph)
	{
		this.disasterAppObjectGraph = disasterAppObjectGraph;
	}
	
	public ObjectGraph getImportContactsObjectGraph()
	{
		return importContactsObjectGraph;
	}

	public void setImportContactsObjectGraph(ObjectGraph importContactsObjectGraph)
	{
		this.importContactsObjectGraph = importContactsObjectGraph;
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
