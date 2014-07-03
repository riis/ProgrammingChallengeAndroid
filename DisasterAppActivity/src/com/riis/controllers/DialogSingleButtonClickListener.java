package com.riis.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class DialogSingleButtonClickListener implements DialogInterface.OnClickListener
{
	private Class<?> destination;
	private Context context;
	
	public DialogSingleButtonClickListener(Context context)
	{
		this.context = context;
	}
	
	public DialogSingleButtonClickListener(Context context, Class<?> destination)
	{
		this.context = context;
		this.destination = destination;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		if(destination == null)
		{
			((Activity) context).finish();
		}
		else
		{
			Intent i = new Intent(context, destination);
			context.startActivity(i);
		}
	}
}