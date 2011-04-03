package com.replica;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.util.Log;


public class TCPClient {
	public static void sendMessage (String ipAddress) {
		try {
	       	InetAddress serverAddr = InetAddress.getByName(ipAddress);
	       	Socket socket = new Socket(serverAddr, 4444);
	       	String message = getLocalIpAddress() + "; " + Pong.gpsMsg;
	       		try {
			    	 PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
			    	 out.println(message);
	            } catch(Exception e) {}
	            finally {
			        socket.close();
	            }
	    } catch (Exception e) {Log.e("TCP","Error",e);}
	}
	
	public static String getLocalIpAddress() throws SocketException {
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
}
