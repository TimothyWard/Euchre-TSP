package euchre.network;


import java.net.*;
import java.io.*;

/**
 * A Runnable thread to facilitate the server's connections with the clients
 * 
 * @author mdhelgen
 *
 */

public class EuchreConnectionThread extends Thread {
	
	private Socket socket = null;
	private String threadName = "defaultname";
	BufferedReader in;
	private boolean running = true;
	EuchreProtocol protocol;
	ServerNetworkManager server;
	PrintWriter out = null;


	
	/**
	 * Create a new EuchreConnectionThread.
	 * 
	 * @param name A identifier for the client
	 * @param s The socket connection to the client
	 */
	public EuchreConnectionThread(String name, Socket s, ServerNetworkManager server) {
		super(name);
		threadName = name;
		socket = s;
		protocol = new EuchreProtocol();
		this.server = server;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The thread's actions. It will wait for input to arrive from the socket, and output when it is recieved.
	 * 
	 */
	public void run() {
		try {
			//get reference to the socket's input stream
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String inputLine;
			
			//continually run this loop
			while(true){
				
				if(running)
					//if a message is recieved
					while((inputLine = in.readLine()) != null){
						
						//output the message
						server.parse(inputLine,this.hashCode());
					    //server.toClients(inputLine,this.hashCode());
					}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * FILL THIS IN
	 * @return
	 */
	public PrintWriter getPrintWriter(){
		return out;
	}
}