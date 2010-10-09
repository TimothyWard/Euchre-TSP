package euchre.network;


public class TestClass {

	/**
	 * Used to initiate / test the network and client connections
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {


		boolean isClient = true;


		if(isClient){
			//create the client thread and start it
			ClientNetworkManager client = new ClientNetworkManager();
			client.start();
		}
		else{
			//create the server thread and start it
			ServerNetworkManager server = new ServerNetworkManager();
			server.start();
		}



		/*

		for(int i = 0; i<1000; i++)
		{
			System.out.println("Main outputting");
			Thread.sleep(500);
		}
		 */

	}

}
