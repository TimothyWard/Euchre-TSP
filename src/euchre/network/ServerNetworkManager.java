package euchre.network;

import java.util.LinkedList;

/**
 * Manages the server's network connection
 * 
 * @author mdhelgen
 *
 */
public class ServerNetworkManager extends Thread{ // extends NetworkManager { Abstract this out later

	boolean listening = true;

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
		while(true){
			if(listening)
			{
				
					threads.addLast(new EuchreConnectionThread("Thread " + i));
					threads.getLast().start();
					
				
				
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
