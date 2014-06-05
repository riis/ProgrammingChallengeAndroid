package com.riis.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class TextMessageReceiver implements MessageReceiver
{

    final SmsManager sms = SmsManager.getDefault();
    
	public TextMessageReceiver(){
		
	}
	
	public void onReceive(Context context, Intent intent) {
		 Bundle bundle = intent.getExtras();
		 
        try {
             
            if (bundle != null) {
                 
                final Object[] smsExtra = (Object[]) bundle.get("pdus");
                 
                for (int i = 0; i < smsExtra.length; i++) {
                     
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                     
                    String message = currentMessage.getDisplayMessageBody();
                    String senderNum = phoneNumber;
 
                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                     
                   // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, 
                                 "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();
                } // end for loop
              } // bundle is null
 
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
             
        }
		
	}
}
