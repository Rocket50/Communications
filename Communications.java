package com.Rotis.Communications;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.bind.DatatypeConverter;

import com.Rotis.security.util.EncryptionUtils;



public interface Communications{
	
	final static int MACINDEX = 0;
	final static int MSGINDEX = 1;
	final static int MACSIZE = 16;
	final static int DEFBUFFERSIZE = 1024;
	final static int HEADERSIZE = 1;
	
	
	public String getIP();
	public void setIP(String s) throws Exception;
	public void overridePort(int port)  throws IOException;
	public int getCurrentPort();
	public void sendPlaintext(String message, String password, int port) throws Exception;
	public String receiveMessage(String password) throws Exception;
	public void closeSocket() throws Exception;
	public void sendBytes(byte[] message, String password, int port) throws Exception;
	
}
