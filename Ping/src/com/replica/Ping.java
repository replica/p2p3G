package com.replica;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ping extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final int SERVERPORT = 4444;
        final Handler mHandler = new Handler();
        
        new Thread(new Runnable() {
            public void run() {
            	try {
                    ServerSocket serverSocket = new ServerSocket(SERVERPORT);
                    while (true) {
                    	Socket client = serverSocket.accept();
                    	try {
                    		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            final String str = in.readLine();
                            final String clientIpAddress = client.getInetAddress().getHostAddress();
                            Log.d("TCP", "S: Received: '" + str + "'");
                            final Runnable mUpdateResults = new Runnable() {
                                public void run() {
                                	Toast.makeText(Ping.this, "Message from IP" + clientIpAddress + ": " + str, Toast.LENGTH_SHORT).show();
                                }
                            };
                            mHandler.post(mUpdateResults);
                    	} catch(Exception e) {}
                  	  	finally {
                  	  		client.close();
                  	  	}
                    }
            	} catch (Exception e) {}
            }
        }).start();

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