package com.riis.models;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class EmergencyMessageTextWatcher implements TextWatcher{
	
	private TextView textView;
	
	public EmergencyMessageTextWatcher(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int length = 120 - s.length();
		textView.setText(String.valueOf(""+ length));
	}

}
