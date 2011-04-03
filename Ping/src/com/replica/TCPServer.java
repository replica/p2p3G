package com.replica;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.util.Log;

public class TCPServer implements Runnable {
	
    public static final int SERVERPORT = 4444;
    Activity activity;
    public void run() {
         try {
              ServerSocket serverSocket = new ServerSocket(SERVERPORT);
              while (true) {
            	  Socket client = serverSocket.accept();
            	  try {
                      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                      final String str = in.readLine();
                      Log.d("TCP", "S: Received: '" + str + "'");
                  } catch(Exception e) {
                    } finally {
                    	client.close();
                    }
              }
              
         } catch (Exception e) {}
    }
}