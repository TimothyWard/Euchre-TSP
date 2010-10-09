package euchre.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Manages the client's network connection
 * 
 * @author mdhelgen
 *
 */
public class ClientNetworkManager extends Thread{ // extends NetworkManager {   abstract this out later

	Socket clientSocket = null;
	
	boolean running = true;
	
	public ClientNetworkManager(){
		
		
	}
	
	
	public void run(){
		
		while(true){
			if(running){
				
				try {
					clientSocket = new Socket("rover-214-97.rovernet.mtu.edu",4444);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("socket created");
				running = false;
			}
			
			
			
			
			
		}
		
		
		
	}
	
	
}
