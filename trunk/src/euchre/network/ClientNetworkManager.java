package euchre.network;

import java.net.Socket;


/**
 * Manages the client's network connection
 * 
 * @author mdhelgen
 *
 */
public class ClientNetworkManager extends NetworkManager {

	private Socket mySocket;
	
	public ClientNetworkManager(Socket s){
		mySocket = s;
		
	}
	
	
	
}
