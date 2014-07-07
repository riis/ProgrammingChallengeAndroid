package com.riis.controllers.contactListDisplay;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.riis.models.ContactReference;
import com.riis.models.ResponseMessage;

public class ResponseMessageSpinnerItemClickListener implements OnItemSelectedListener
{
	private Context context;
	private ResponseMessage message;
	
	public ResponseMessageSpinnerItemClickListener(Context context, ResponseMessage message)
	{
		this.context = context;
		this.message = message;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
	{
		if(message.getReferenceId() == -1)
		{
			return;
		}
		
		ContactReference ref = new ContactReference(context);
		ref.read(message.getReferenceId());
		
		ref.setNotes(pos);
		ref.update();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
		return;
	}
}