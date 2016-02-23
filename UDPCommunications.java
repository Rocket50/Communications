package com.Rotis.Communications;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.bind.DatatypeConverter;

import com.Rotis.security.util.EncryptionUtils;

public class UDPCommunications implements Communications {
	
	protected InetAddress addr = null; 
	protected DatagramSocket socket = null;
	
	protected static final byte PLAINTEXTHEADER = 0b0;
	protected static final byte PINGHEADER = 0b1;
	protected static final byte FILEHEADER = 0b10;
	
	//Binds to specific port 
	public UDPCommunications(int port) throws IOException{
		
		socket = new DatagramSocket(port);
	}
	//Binds to any open port
	public UDPCommunications() throws IOException{
		
		socket = new DatagramSocket();
	}
	
	@Override
	public void sendBytes(byte[] message, String password, int port) throws Exception{
		DatagramPacket packet; 
		//generate hashes and encryption
		byte[][] cipherPackage = EncryptionUtils.encyptAES256(password, message);
		int packetSize = cipherPackage[MSGINDEX].length + cipherPackage[MACINDEX].length + HEADERSIZE ;
		System.out.println("SENDING PACKET SIZE: " + packetSize);
		//Package and send mac and body - MAC is always 16bytes
		byte[] buf = new byte[packetSize];
		//System.out.println("MAC RAW: " + DatatypeConverter.printHexBinary(cipherPackage[MACINDEX]));
		//System.out.println("MACSIZE:" + cipherPackage[MACINDEX].length);
		//System.out.println("DATA RAW: " + DatatypeConverter.printHexBinary(cipherPackage[MSGINDEX]));
		System.arraycopy(cipherPackage[MACINDEX], 0, buf, HEADERSIZE, cipherPackage[MACINDEX].length );
		System.arraycopy(cipherPackage[MSGINDEX], 0, buf, MACSIZE + HEADERSIZE, cipherPackage[MSGINDEX].length);
		
		buf[0] = PLAINTEXTHEADER;
		//System.out.println("COMPLETE RAW: " +  DatatypeConverter.printHexBinary(buf));
		packet = new DatagramPacket(buf, buf.length, addr, port);
		socket.send(packet);
	}
	@Override
	public void sendPlaintext(String message, String password, int port) throws Exception{
		sendBytes(message.getBytes(), password, port);

	}

	@Override
	public String receiveMessage(String password) throws Exception{

		byte[] buf = new byte[DEFBUFFERSIZE];

		DatagramPacket packet = new DatagramPacket(buf,buf.length);

		
		byte[] mac = null;
		byte[] msg = null;

		socket.receive(packet);
		System.out.print("========RECIEVED PACKET FROM: ");
		System.out.println(packet.getAddress() + ":" + packet.getPort());

		byte[] itemized = packet.getData();

		System.out.println("CONTAINS: " + itemized.length  + " BYTES");
		
		byte header = itemized[0];
		
		if(header == PLAINTEXTHEADER){
			int dataSize = packet.getLength();
			int messageSize = dataSize - MACSIZE - HEADERSIZE;
			System.out.println("---PLAINTEXT PACKET");
			System.out.println("SIZE: " + dataSize +  " BYTES - OFFSET: "  + packet.getOffset());
			
			System.arraycopy(itemized, HEADERSIZE, mac = new byte[MACSIZE], 0, MACSIZE);
			System.out.println("MAC RAW: " + DatatypeConverter.printHexBinary(mac));
			System.arraycopy(itemized, MACSIZE + HEADERSIZE, msg = new byte[messageSize], 0, messageSize);
			System.out.println("BODY RAW: " + DatatypeConverter.printHexBinary(msg));
			//System.out.println("COMPLETE RAW: " +  DatatypeConverter.printHexBinary(itemized));
		}
		else{
			System.out.println("UNKNOWN PACKET HEADER: " + header);
		}

		String data = EncryptionUtils.decryptAES256(password, msg, mac);
		return data;
	}
	
	@Override
	public void closeSocket(){
		socket.close();
	}

	@Override
	public String getIP() {

		return addr.toString();
	}

	@Override
	public void setIP(String s) throws UnknownHostException {
		addr = InetAddress.getByName(s);
	}

	@Override
	public void overridePort(int port) throws IOException {
		socket = new DatagramSocket(port);
	}

	@Override
	public int getCurrentPort() {
		return socket.getLocalPort();
	}

}
