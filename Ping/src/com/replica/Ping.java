package com.replica;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ping extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Thread sThread = new Thread(new TCPServer());
        sThread.start();
        try {
               Thread.sleep(100);
          } catch (InterruptedException e) {}
        
        final EditText phoneBox = (EditText) findViewById(R.id.phonebox);
        Button btnSend = (Button) findViewById(R.id.sendbutton);
		btnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick (View v) {
				try {
					sendSMS(phoneBox.getText().toString(),getLocalIpAddress());
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
	}
	
	public void sendSMS(String phoneNumber, String message)
    {        
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Ping.class), 0);                
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);        
    }
}