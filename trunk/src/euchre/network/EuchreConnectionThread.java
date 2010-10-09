package euchre.network;


import java.net.*;
import java.io.*;

/**
 * A Runnable thread to facilitate communication for the clients and servers
 * 
 * @author mdhelgen
 *
 */

public class EuchreConnectionThread extends Thread {
	private Socket socket = null;
	private String threadName = "defaultname";
	BufferedReader in;

	private boolean running = true;

	public EuchreConnectionThread(String name, Socket s) {
		super(name);
		threadName = name;
		socket = s;
	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String inputLine;

			while(true){
				if(running)
					while((inputLine = in.readLine()) != null){
						System.out.println("Message from client:");
						System.out.println(inputLine);
					}

						System.out.println(threadName + " is running");
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
		/*
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String inputLine, outputLine;
			//KnockKnockProtocol kkp = new KnockKnockProtocol();
			//outputLine = kkp.processInput(null);
			//out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				//outputLine = kkp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye"))
					break;
			}
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		 */
	}

}