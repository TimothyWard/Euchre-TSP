package euchre.network;

public class TestClass {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ServerNetworkManager network = new ServerNetworkManager();
		network.start();
		
		for(int i = 0; i<1000; i++)
		{
			System.out.println("Main outputting");
			Thread.sleep(500);
		}
		
		
	}

}
