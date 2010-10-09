package euchre.network;


public class TestClass {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		

		
		
		
		
		//ServerNetworkManager server = new ServerNetworkManager();
		//server.start();
		
		ClientNetworkManager client = new ClientNetworkManager();
		client.start();
		
		/*
		
		for(int i = 0; i<1000; i++)
		{
			System.out.println("Main outputting");
			Thread.sleep(500);
		}
		*/
		
	}

}
