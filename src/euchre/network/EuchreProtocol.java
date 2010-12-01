/**
 * 
 */
package euchre.network;

import java.util.StringTokenizer;

import euchre.game.Game;
import euchre.player.Card;
import euchre.player.GameManager;
import euchre.player.Human;
import euchre.player.Player;

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
	
	boolean debug = false;

	/**
	 * Get any necessary references
	 * 
	 */
	public EuchreProtocol(){

	}

	/**
	 * FILL THIS IN
	 * @param input
	 */
	public void serverParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		if(debug)
			System.out.println("PARSING '" + input + "'");
		while(parser.hasMoreTokens()){
			token = parser.nextToken();

			//"name", player name, player number
			if(token.equals("Name")){
				String name = parser.nextToken();

			}
			else if(token.equals("RegisterPlayer")){
				String name = parser.nextToken();
				String type = parser.nextToken();
				int randomNum = Integer.parseInt(parser.nextToken());
				if(debug)
					System.out.println("Player: " + name);
				
				
				if(connectedClients == null)
					connectedClients = name + ","+ randomNum;
				else{
					connectedClients = connectedClients +"," +name + "," + randomNum;
					numConnectedClients++;
					if(numConnectedClients == 3){
						server.toClients("SetPlayers,"+connectedClients);
						serverParse("SetPlayers,"+connectedClients);

					}
				}
				
				

				switch(numConnectedClients){
				case 1:
					manager.getLobby().setPlayer2Status(name);
					break;
				case 2:
					manager.getLobby().setPlayer3Status(name);
					break;
				case 3:
					manager.getLobby().setPlayer4Status(name);
					break;
				}
				
				
				
				

			}
			else if(token.equals("SetPlayers")){
				Human one = new Human();
				Human two = new Human();
				Human three = new Human();
				Human four = new Human();
				
				String host = parser.nextToken();
				int hostID = Integer.parseInt(parser.nextToken());
				String player1 = parser.nextToken();
				int p1ID = Integer.parseInt(parser.nextToken());
				String player2 = parser.nextToken();
				int p2ID = Integer.parseInt(parser.nextToken());
				String player3 = parser.nextToken();
				int p3ID = Integer.parseInt(parser.nextToken());

				one.setName(host);
				one.setPlayerID(hostID);
				two.setName(player1);
				two.setPlayerID(p1ID);
				three.setName(player2);
				three.setPlayerID(p2ID);
				four.setName(player3);
				four.setPlayerID(p3ID);
				manager.setAllPlayers(one, two, three, four);
				
				if(debug){
					System.out.println("Player 1 name:" + manager.getp1().getName());
					System.out.println("Player 2 name:" + manager.getp2().getName());
					System.out.println("Player 3 name:" + manager.getp3().getName());
					System.out.println("Player 4 name:" + manager.getp4().getName());
				}

			}

			else if(token.equals("SendPlayerList"))
			{
				server.toClients("SetPlayers," + connectedClients);
			}
			else if(token.equals("SetNextPlayerTurn")){
				
				manager.setNextPlayerTurn();
				server.toClients("SetNextPlayerTurn");
				
			}
			else if(token.equals("SetPlayerTurn")){
				int id = Integer.parseInt(parser.nextToken());
			
				manager.setTurnPlayerID(id);
				server.toClients("SetPlayerTurn,"+id);
			}
			else if(token.equals("PickItUp")){
				manager.getGameBoard().pickItUp();
				server.toClients("PickItUp");
			}
			else if(token.equals("SetTrump")){
				char trump = parser.nextToken().charAt(0);
				manager.setTrump(trump);
				server.toClients("SetTrump,"+trump);
			}
			else if(token.equals("PlayCard")){
				String card = parser.nextToken();
				int playernum = Integer.parseInt(parser.nextToken());
				
				Card c = new Card(card.charAt(0), card.charAt(1));
				manager.getGameBoard().playCard(c, playernum);
				
				server.toClients("PlayCard,"+card+","+playernum);

			}
			
			
			else{
				
				if(debug)
					System.out.println("Undefined token: " + token);
			
			}



		}

	}

	/**
	 * FILL THIS IN
	 * @param input
	 */
	public void clientParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		
		if(debug)
			System.out.println("PARSING - " + input);
		
		while(parser.hasMoreTokens()){
			token = parser.nextToken();
			if(token.equals("SetPlayers")){
				Human one = new Human();
				Human two = new Human();
				Human three = new Human();
				Human four = new Human();




				String host = parser.nextToken();
				int hostID = Integer.parseInt(parser.nextToken());
				String player1 = parser.nextToken();
				int p1ID = Integer.parseInt(parser.nextToken());
				String player2 = parser.nextToken();
				int p2ID = Integer.parseInt(parser.nextToken());
				String player3 = parser.nextToken();
				int p3ID = Integer.parseInt(parser.nextToken());



				one.setName(host);
				one.setPlayerID(hostID);
				two.setName(player1);
				two.setPlayerID(p1ID);
				three.setName(player2);
				three.setPlayerID(p2ID);
				four.setName(player3);
				four.setPlayerID(p3ID);
				manager.setAllPlayers(one, two, three, four);

				
			}
			else if(token.equals("SetTeam")){
				int player = Integer.parseInt(parser.nextToken());
				int team = Integer.parseInt(parser.nextToken());
				manager.setTeam(player, team);
				if(debug)
					System.out.println("SetTeam("+player+","+team+")");
			}
			else if(token.equals("SpawnGameBoard")){
				Game.initializeGameBoard(manager.getGameBoard());
				manager.setTeamsComplete(true);
			}
			else if(token.equals("SetHand")){
				int playernum = Integer.parseInt(parser.nextToken());
	
				
				char cardvalue;
				char cardsuit;
				for(int i = 0;i < 5; i++)
				{
					String card = parser.nextToken();
					cardvalue = card.charAt(0);
					cardsuit = card.charAt(1);
					if(playernum == 1)
						manager.getPlayer1().setCard(i,cardvalue,cardsuit);
					if(playernum == 2)
						manager.getPlayer2().setCard(i,cardvalue,cardsuit);
					if(playernum == 3)
						manager.getPlayer3().setCard(i, cardvalue, cardsuit);
					if(playernum == 4)
						manager.getPlayer4().setCard(i, cardvalue, cardsuit);
				}
				
				Game.initializeGameBoard(manager.getGameBoard());
			}
			else if(token.equals("SetTurnedCard")){
				char cardvalue;
				char cardsuit;
				String card = parser.nextToken();
				cardvalue = card.charAt(0);
				cardsuit = card.charAt(1);
				manager.getGameBoard().setTurnedCard(new Card(cardvalue,cardsuit));
				Game.initializeGameBoard(manager.getGameBoard());
			}
			else if(token.equals("SetPlayerTurn")){
				int id = Integer.parseInt(parser.nextToken());
				
				manager.setTurnPlayerID(id);
			}
			else if(token.equals("SetNextPlayerTurn")){
					
				manager.setNextPlayerTurn();
				
			}
			else if(token.equals("SettingSuit")){
				manager.getGameBoard().settingSuit();
			}
			else if(token.equals("PickItUp"))
			{
				manager.getGameBoard().pickItUp();
			}
			else if(token.equals("SetTrump")){
				char trump = parser.nextToken().charAt(0);
				manager.setTrump(trump);
			}
			else if(token.equals("PlayCard")){
				String card = parser.nextToken();
				int playernum = Integer.parseInt(parser.nextToken());
				
				Card c = new Card(card.charAt(0), card.charAt(1));
				manager.getGameBoard().playCard(c, playernum);
			}



			else
				if(debug)
					System.out.println("Undefined token: " + token);
		}

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
