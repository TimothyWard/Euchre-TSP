package euchre.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * Listens for connections, keeps track of existing connections
 * 
 * 
 * 
 * 
 * @author mdhelgen
 *
 */
public class NetworkManager {


	protected static LinkedList<EuchreClient> clientList;
	protected static EuchreServer server;
	
	/**
	 * set up any necessary references, keep track of own network name
	 * 
	 */
	public NetworkManager(){

	}


	/**
	 * Spawn a new thread for a new client
	 * 
	 */
	public void addClient(String hostname, int port){


		try {
			clientList.add(new EuchreClient(hostname,new Socket(hostname,port)));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Set the location of the server
	 * 
	 */
	public void setServer(){


	}

	/**
	 * Get the list of clients
	 * 
	 */
	public LinkedList<EuchreClient> getClientList(){

		return clientList;
	}

	/**
	 * Get the server
	 * 
	 */
	public EuchreServer getServer(){

		return null;
	}

}
