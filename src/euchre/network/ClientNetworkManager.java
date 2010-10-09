package euchre.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * A thread to manage the client's network connection
 * 
 * @author mdhelgen
 *
 */
public class ClientNetworkManager extends Thread{ // extends NetworkManager {   abstract this out later

	Socket clientSocket = null;
	PrintWriter out = null;
	
	String hostname = "rover-214-97.rovernet.mtu.edu";
	int port = 4444;
	
	boolean running = true;
	
	public ClientNetworkManager(){
		
		
	}
	
	/**
	 * The thread's actions. Make a socket connection to the server and send a string
	 * 
	 */
	public void run(){
		
		//run this loop continually
		while(true){
			
			if(running){
				
				try {
					//create the new socket connection
					clientSocket = new Socket(hostname, port);
					
					//get reference to the output stream
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					System.out.println("connected to server");
					
					//send a message to the server
					out.println("CLIENT");
					running = false;
					
					
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			
			
			
			
		}
		
		
		
	}
	
	
}
