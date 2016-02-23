package com.Rotis.Communications;

import java.io.IOException;

import com.Rotis.client.ClientTCPCommunications;
import com.Rotis.client.ClientUDPCommunications;


public class ClientCommunicationsFactory {
	public static ClientCommunications getInstance(String mode, int port) throws UnknownCommunicationModeException, IOException{
		switch(mode){
		case "UDP":
			return new ClientUDPCommunications(port);
		case "TCP":
			return new ClientTCPCommunications(port);
		default:
			throw new UnknownCommunicationModeException();
		}
		
	}
	
	public static ClientCommunications getInstance(String mode) throws UnknownCommunicationModeException, IOException{
		switch(mode){
		case "UDP":
			return new ClientUDPCommunications();
		case "TCP":
			return new ClientTCPCommunications();
		default:
			throw new UnknownCommunicationModeException();
		}
	}
}
