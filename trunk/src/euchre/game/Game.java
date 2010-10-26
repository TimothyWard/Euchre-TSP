package euchre.game;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import euchre.gui.ClientGameSetup;
import euchre.gui.GameBoard;
import euchre.gui.HostGameSetup;
import euchre.gui.SetupLocal;
import euchre.gui.Welcome;
import euchre.player.*;
import euchre.network.*;



/**
 * @author Timothy Ward
 * This class is the highest order class of the Euchre program. It is responsible for the highest level interactions between componenets.
 */
public class Game {	

	/**
	 * This method is the first method called in the program. This method is 
	 * responsible for instantiating all objects and running the overall program.
	 * 
	 * @param args A String array.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	public static void main(String [] args) throws InterruptedException{
		//setup host and client objects, in a new game
		GameManager GM = new GameManager();

		//declare GUI welcome window to ask if host or client
		Welcome GUI = new Welcome();
		GUI.setVisible(true);

		//wait for the user to decide the game type
		while (GUI.isWinodwComplete()==false) Thread.sleep(500);

		//retreive the user's desired game choice and dispose of the welcome window
		char gameChoice = GUI.getGameChoice();
		GUI.setVisible(false);
		GUI.dispose();

		//create the users desired player type based on the game choice
		if (gameChoice == 'h') createHostPlayer(GM);
		else if(gameChoice == 'c') createClientPlayer(GM);
		else if(gameChoice == 'a') createLocalOnlyGame(GM);

		//wait for all players to join and GM's to sync
		while (GM.areTeamsComplete()==false) Thread.sleep(500);

		//set teams
		Team one = GM.getTeamOne();
		Team two = GM.getTeamTwo();

		//create a new tabulator.
		GameLogic tabulator = new GameLogic();

		//if the game has not been won, continue
		while (gameWinner(one, two) == null){

			//start the next round
			Round currentRound = new Round();
			GM.setRound(currentRound);

			//wait for the current round to be over
			while (currentRound.isRoundComplete()==false) Thread.sleep(1000);

			//score the recently completed round
			tabulator.interpret(currentRound, one, two);
		}

		//if the game is over, set the last round to null and display the winner
		GM.setRound(null);
		JOptionPane.showMessageDialog(null, "Team " + gameWinner(one, two).getTeamNumber() + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
		//once game winner is determined, inform network who won to update views.
	}

	/**
	 * This method will just create a host object, and will also create the appropriately
	 * specified number of AI and Human players to accompany.
	 * 
	 * @param GM The GameManager object for the network and to pass the new host to.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	public static void createHostPlayer(GameManager GM) throws InterruptedException{

		//create the new host, its game board and its server
		GM.setHostPlayer(new Human());
		GameBoard GB = new GameBoard();
		ServerNetworkManager server = createNewServer(GM);

		//open the window for the user to input the game data
		HostGameSetup hostSetup = new HostGameSetup(GM);
		hostSetup.setVisible(true);

		//wait untill the user has input name and number of additional human players	
		while (hostSetup.getGameLobby() == null || hostSetup.getGameLobby().isSetupComplete()==false) Thread.sleep(500);

		//initialize the hosts game board
		initializeGameBoard(GM,GB);

		//send team changes to network
		server.toClients("SpawnGameBoard");
		//tell clients to update game boards
		
		int aiNumber = hostSetup.getAIs();
		while (aiNumber!=0){
			//spawn a new copy of the software that is an AI
			aiNumber--;
		}
	}

	/**
	 * The method will create a local only game, it is for when a user chooses to play against
	 * three computers.
	 * @param GM The GameManager object for the network and to pass the new host and new AI's to.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	public static void createLocalOnlyGame(GameManager GM) throws InterruptedException{
		
		//set the new host to a new human
		GM.setHostPlayer(new Human());
		
		//create a window to ask for name and game info
		SetupLocal local = new SetupLocal();
		local.setVisible(true);
		
		//wait for info
		while (local.getSetupComplete() == false) Thread.sleep(500);
		
		//spawn a human and three new copies of the software as AI's
	}

	/**
	 * This method will create a client object.
	 * @param GM The GameManager object for the network and to pass the new client to.
	 * @param GUI The welcome window for user input.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	public static void createClientPlayer(GameManager GM) throws InterruptedException{
		
		//make a new human and pass it to the game manager
		Human human = new Human();
		GM.setClientPlayer(human);
		
		//make a new window to ask for user input
		ClientGameSetup clientSetup = new ClientGameSetup();
		clientSetup.setVisible(true);
		
		//wait for user to input data
		while(clientSetup.hasInput() == false) Thread.sleep(500);
		
		//create new client and its network from given ip address and name
		ClientNetworkManager client = createNewClient(GM, clientSetup);
		client.toServer("RegisterPlayer,"+clientSetup.getClientName().trim()+","+human.getPlayerID());

		//wait for everyone to join before continuing
		while(GM.areTeamsComplete() == false) Thread.sleep(500);
		clientSetup.dispose();

	}

	/**
	 * This method initializes the GameBoard.
	 * 
	 * @param GM The GameManager.
	 * @param GB The GameBoard.
	 */
	public static void initializeGameBoard(GameManager GM, GameBoard GB){
		GB.setGameManager(GM);
		GM.setGameBoard(GB);
		GB.updateBoard();
		GB.setVisible(true);
	}
	
	/**
	 * This method creates a new server, and passes all of the needed references regarding it.
	 * @param GM The GameManager that the server and it need a reference to and from.
	 */
	public static ServerNetworkManager createNewServer(GameManager GM){
		ServerNetworkManager network = new ServerNetworkManager();
		network.setGameManager(GM);
		network.start();
		GM.setServerNetworkManager(network);
		return network;
	}
	
	/**
	 * This method creates a new server, and passes all of the needed references regarding it.
	 * @param GM The GameManager that the server and it need a reference to and from.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	public static ClientNetworkManager createNewClient(GameManager GM, ClientGameSetup clientSetup) throws InterruptedException{
		ClientNetworkManager client = new ClientNetworkManager(clientSetup.getIP());
		clientSetup.setGameManager(GM);
		GM.setClientNetworkManager(client);
		client.setGameManager(GM);
		client.start();
		Thread.sleep(500);
		return client;
	}

	/**
	 * This method compares the scores of the two teams and returns the winning team
	 * if there is one or null otherwise.
	 * 
	 * @param one The first team.
	 * @param two The second team.
	 * @return Team The winning team
	 */
	public static Team gameWinner(Team one, Team two){
		if(one.getScore() >= 10){
			return one;
		}
		else if(two.getScore() >= 10){
			return two;
		}
		else{
			return null;
		}
	}

}
