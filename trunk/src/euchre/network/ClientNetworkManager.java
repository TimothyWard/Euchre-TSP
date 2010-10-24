package euchre.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import euchre.player.GameManager;


/**
 * A thread to manage the client's network connection
 * 
 * @author mdhelgen
 *
 */
public class ClientNetworkManager extends Thread{ // extends NetworkManager {   abstract this out later

	Socket clientSocket = null;
	PrintWriter out = null;
	BufferedReader in;
	EuchreProtocol protocol;
	GameManager manager;
	
	String hostname;
	int port = 4444;
	boolean connecting = true;
	boolean running = true;
	String inputLine;
	
	/**
	 * Create a ClientNetworkManager to connect to localhost.
	 * 
	 */
	public ClientNetworkManager(){
		
		hostname = "localhost";
		protocol = new EuchreProtocol();
		
	}
	
	/**
	 * Create a ClientNetworkManager to connect to the specified hostname.
	 *  
	 * @param hostname The hostname of the server to connect to.
	 */
	public ClientNetworkManager(String hostname){
		
		this.hostname = hostname;
		protocol = new EuchreProtocol();
	}
	
	public void registerPlayer(String name, int number){
		
		toServer("Name,"+name+","+number);
	}
	
	public void toServer(String tokenizedString){
		
		out.println(tokenizedString);
	}
	
	public void setGameManager(GameManager gm){
		manager = gm;
		protocol.setGameManager(gm);
	}
	
	/**
	 * The thread's actions. Make a socket connection to the server and send a string
	 * 
	 */
	public void run(){
		
		//run this loop continually
		while(true){
			
			if(connecting){
				
				
					//create the new socket connection
					try {
						clientSocket = new Socket(hostname, port);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						System.out.println("Unknown Host:" + hostname);
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
						in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("connected to server");
					
					//send a message to the server
					//out.println("CLIENT");
					connecting = false;
				
			}
			if(running){
				try {
					while((inputLine = in.readLine()) != null){
						
						//output the message
						System.out.println("Message from server:");
					    protocol.clientParse(inputLine);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			
			
			
			
		}
		
		
		
	}
	
	
}
