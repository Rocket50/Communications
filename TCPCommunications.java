package com.Rotis.Communications;

import java.io.IOException;

public class TCPCommunications implements Communications {
	
	public TCPCommunications(int port){
		
	}
	public TCPCommunications(){
		
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIP(String s) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void overridePort(int port) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendPlaintext(String message, String password, int port)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String receiveMessage(String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeSocket() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendBytes(byte[] message, String password, int port)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
