package euchre.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
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
	
	String hostname = "localhost";
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
				
				
					//create the new socket connection
					try {
						clientSocket = new Socket(hostname, port);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						System.out.println("blah");
						running = false;
						//e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("Connection refused");
						running = false;
						break;
					}
					
					//get reference to the output stream
					try {
						out = new PrintWriter(clientSocket.getOutputStream(), true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("connected to server");
					
					//send a message to the server
					out.println("CLIENT");
					running = false;
					
					
					
			
				
				
			}
			
			
			
			
			
		}
		
		
		
	}
	
	
}
