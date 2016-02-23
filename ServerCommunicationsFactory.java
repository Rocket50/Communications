package com.Rotis.Communications;

import java.io.IOException;

import com.Rotis.server.ServerTCPCommunications;
import com.Rotis.server.ServerUDPCommunications;

public class ServerCommunicationsFactory {
	public static ServerCommunications getInstance(String mode, int port) throws UnknownCommunicationModeException, IOException{
		switch(mode){
		case "UDP":
			return new ServerUDPCommunications(port);
		case "TCP":
			return new ServerTCPCommunications(port);
		default:
			throw new UnknownCommunicationModeException();
		}
		
	}
	
	public static ServerCommunications getInstance(String mode) throws UnknownCommunicationModeException, IOException{
		switch(mode){
		case "UDP":
			return new ServerUDPCommunications();
		case "TCP":
			return new ServerTCPCommunications();
		default:
			throw new UnknownCommunicationModeException();
		}
	}
}
