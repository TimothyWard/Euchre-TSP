/**
 * 
 */
package euchre.network;

import java.util.StringTokenizer;

import euchre.player.GameManager;
import euchre.player.Human;

/**
 * @author mdhelgen
 * Facilitates network communication between the clients and the server. 
 */
public class EuchreProtocol {

	private int numConnectedClients = 0;

	private GameManager manager;
	private ClientNetworkManager client;
	private ServerNetworkManager server;
	String connectedClients;

	/**
	 * Get any necessary references
	 * 
	 */
	public EuchreProtocol(){

	}


	public void serverParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		System.out.println("PARSING '" + input + "'");
		while(parser.hasMoreTokens()){
			token = parser.nextToken();

			//"name", player name, player number
			if(token.equals("Name")){
				String name = parser.nextToken();

			}
			else if(token.equals("RegisterPlayer")){
				String name = parser.nextToken();
				System.out.println("Player: " + name);

				if(connectedClients == null)
					connectedClients = name;
				else{
					connectedClients = connectedClients +"," +name;
					numConnectedClients++;
					if(numConnectedClients == 3){
						server.toClients("SetPlayers,"+connectedClients);
						serverParse("SetPlayers,"+connectedClients);

					}
				}
				System.out.println(connectedClients);

			}
			else if(token.equals("SetPlayers")){
				Human one = new Human();
				Human two = new Human();
				Human three = new Human();
				Human four = new Human();




				String host = parser.nextToken();
				String player1 = parser.nextToken();
				String player2 = parser.nextToken();
				String player3 = parser.nextToken();


				one.setName(host);
				two.setName(player1);
				three.setName(player2);
				four.setName(player3);
				manager.setAllPlayers(one, two, three, four);
				System.out.println("Player 1 name:" + manager.getp1().getName());
				System.out.println("Player 2 name:" + manager.getp2().getName());
				System.out.println("Player 3 name:" + manager.getp3().getName());
				System.out.println("Player 4 name:" + manager.getp4().getName());


			}

			else if(token.equals("SendPlayerList"))
			{
				server.toClients("SetPlayers," + connectedClients);
			}
			else{
				System.out.println("Undefined token: " + token);
			}



		}

	}

	public void clientParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		System.out.println("PARSING - " + input);
		while(parser.hasMoreTokens()){
			token = parser.nextToken();
			if(token.equals("SetPlayers")){
				Human one = new Human();
				Human two = new Human();
				Human three = new Human();
				Human four = new Human();




				String host = parser.nextToken();
				String player1 = parser.nextToken();
				String player2 = parser.nextToken();
				String player3 = parser.nextToken();


				one.setName(host);
				two.setName(player1);
				three.setName(player2);
				four.setName(player3);
				manager.setAllPlayers(one, two, three, four);
				System.out.println("Player 1 name:" + manager.getp1().getName());
				System.out.println("Player 2 name:" + manager.getp2().getName());
				System.out.println("Player 3 name:" + manager.getp3().getName());
				System.out.println("Player 4 name:" + manager.getp4().getName());

				
			}



			else
				System.out.println("Undefined token: " + token);
		}

	}

	public void setName(String name, int number){
		System.out.println("Player name: " + name);
		System.out.println("Player number: " + number);


	}

	public void setGameManager(GameManager gm){
		manager = gm;
	}

	public void setClientNetworkManager(ClientNetworkManager c){
		client = c;
	}

	public void setServerNetworkManager(ServerNetworkManager s){
		server = s;
	}




}
