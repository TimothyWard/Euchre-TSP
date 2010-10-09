package euchre.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

/**
 * Manages the server's network connection
 * 
 * @author mdhelgen
 *
 */
public class ServerNetworkManager extends Thread{ // extends NetworkManager { Abstract this out later

	boolean listening = true;
	ServerSocket serverSocket = null;

	LinkedList<EuchreConnectionThread> threads = new LinkedList<EuchreConnectionThread>();

	public ServerNetworkManager(){		



	}


	public void startListening(){

		listening = true;
	}

	public void stopListening(){

		listening = false;
	}

	public boolean isListening(){

		return listening;
	}

	public void run(){
		
		int i = 0;
		
		try{
			serverSocket = new ServerSocket(4444);
		}
		catch(IOException e){
			System.err.println("Could not listen on port: 4444");
			e.printStackTrace();
		}
		while(true){
			if(listening)
			{
				
					//threads.addLast(new EuchreConnectionThread("Thread " + i));
					System.out.println("Listening for connections");
				try {
					threads.addLast(new EuchreConnectionThread("Thread",serverSocket.accept()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				threads.getLast().start();
				System.out.println("Connection recieved/started");
					
				
				
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
}
