package com.riis.models;

import android.content.Context;
import android.content.Intent;



public interface MessageReceiver {
	public void onReceive(Context context, Intent intent);
}
