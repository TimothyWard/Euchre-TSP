package euchre.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

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

	//contains references to all of the communication threads for socket connections
	LinkedList<EuchreConnectionThread> threads = new LinkedList<EuchreConnectionThread>();

	/**
	 * 
	 * 
	 */
	public ServerNetworkManager(){		

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
					threads.addLast(new EuchreConnectionThread("Thread",serverSocket.accept()));
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
