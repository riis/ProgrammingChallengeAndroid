package com.riis.controllers;

import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.R;

public class ContactSpinnerItemClickListener implements OnItemSelectedListener
{
	private TextView textView;
	private EditText editText;
	
	public ContactSpinnerItemClickListener(TextView textView, EditText editText)
	{
		this.textView = textView;
		this.editText = editText;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id)
	{
		switch(pos)
		{
		case 0:
			textView.setText(R.string.emailAddressLabel);
			editText.setHint(R.string.emailAddressHint);
			editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			break;
		case 1:
			textView.setText(R.string.phoneNumberLabel);
			editText.setHint(R.string.phoneNumberHint);
			editText.setInputType(InputType.TYPE_CLASS_PHONE);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
		return;
	}

}
