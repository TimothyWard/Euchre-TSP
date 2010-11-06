package euchre.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

import euchre.player.GameManager;

/**
 * Manages the server's network connections
 * 
 * @author mdhelgen
 *
 */
public class ServerNetworkManager extends Thread{ // extends NetworkManager { Abstract this out later

	boolean listening = true;
	ServerSocket serverSocket = null;
	int port = 4444;
	EuchreProtocol protocol;
	GameManager manager;
	
	boolean debug = true;

	//contains references to all of the communication threads for socket connections
	LinkedList<EuchreConnectionThread> threads = new LinkedList<EuchreConnectionThread>();

	/**
	 * 
	 * 
	 */
	public ServerNetworkManager(){		
		protocol = new EuchreProtocol();
		protocol.setServerNetworkManager(this);
	}

	/**
	 * Tell the thread to start listening for connections
	 * 
	 */
	public void startListening(){

		listening = true;
	}

	/**
	 * Tell the thread to stop listening for connections
	 * 
	 */
	public void stopListening(){

		listening = false;
	}

	/**
	 * Find if the server is actively listening for connections
	 * 
	 * @return true if the thread is listening, false otherwise.
	 */
	public boolean isListening(){

		return listening;
	}

	/**
	 * Get the number of connected clients
	 * 
	 * @return The number of dispatched client threads
	 */
	public int getNumClients(){


		return threads.size();

	}

	
	/**
	 * Send a message to all of the clients
	 * 
	 * @param tokenizedString The message to send to the clients, to be interpreted by EuchreProtocol
	 */
	public void toClients(String tokenizedString){

		if(debug)
			System.out.println(tokenizedString);
		
		for(EuchreConnectionThread t : threads){
			t.getPrintWriter().println(tokenizedString);
		}
	}
	
	/**
	 * Send a message to the parser in EuchreProtocol
	 * 
	 * @param message The message to be parsed by EuchreProtocol
	 */
	public void parse(String message){
		
		protocol.serverParse(message);
	}
	
	/**
	 * Get reference to the parser object
	 * 
	 * @return The EuchreProtocol object
	 */
	public EuchreProtocol getParser(){
		return protocol;
	}
	
	/**
	 * Sets reference in the protocol to the gamemanager, where the player info is stored
	 * 
	 * @param gm The GameManager object for our game
	 */
	public void setGameManager(GameManager gm){
		manager = gm;
		protocol.setGameManager(gm);
	}

	/**
	 * The thread's actions. Creates a ServerSocket, and if it is listening for connections, will accept incoming requests,
	 * create a new socket, and dispatch a thread to handle communication.
	 *
	 * 
	 */
	public void run(){

		//create the server socket on port 4444
		try{
			serverSocket = new ServerSocket(port);
		}
		catch(IOException e){
			System.err.println("Could not listen on port: " + port);
			e.printStackTrace();
		}

		//continuously run this loop while the thread is running
		while(true){

			//if the thread is not listening, do not accept connections
			if(listening)
			{

				System.out.println("Listening for connections");
				try {

					//accept a connection and dispatch a new thread with that socket as a parameter
					threads.addLast(new EuchreConnectionThread("Thread",serverSocket.accept(), this));
					//start the thread
					threads.getLast().start();
					System.out.println("Connection recieved/started");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}	

		}
	}
}
