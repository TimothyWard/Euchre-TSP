package euchre.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.JOptionPane;
import euchre.gui.*;
import euchre.player.*;
import euchre.network.*;
import java.io.BufferedOutputStream;



/**
 * @author Timothy Ward
 * 
 * This class is the highest order class of the Euchre program. It is responsible for the highest level interactions between components.
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

		//if this process is for a human
		if(args.length==0){

			//declare GUI welcome window to ask if host or client
			Welcome welcomeWindow = new Welcome();
			welcomeWindow.setVisible(true);

			//wait for the user to decide the game type
			while (welcomeWindow.isWinodwComplete()==false) Thread.sleep(500);

			//retrieve the user's desired game choice and dispose of the welcome window
			char gameChoice = welcomeWindow.getGameChoice();
			welcomeWindow.setVisible(false);
			welcomeWindow.dispose();

			//create the users desired player type based on the game choice
			if (gameChoice == 'h') createHostPlayer(GM);
			else if(gameChoice == 'c') createClientPlayer(GM);
			else if(gameChoice == 'a') createLocalOnlyGame(GM);
		}

		//if this process if for an AI
		else if (args.length >0){
			if (args[0].equals("-ai")){
				try {
					//redirect output
					System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/AIOutput"))));
					System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/AIErrorOutput"))));

				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//create the AI
				createAIPlayer(GM, args[1]);
			}
		}
		GM.playGame();
		//wait for the game to end
		while (GM.getGameBoard().getTabulator().gameWinner(GM.getTeamOne(), GM.getTeamTwo())==null) Thread.sleep(1000);
	}

	/**
	 * This method creates the specified number of AIs in separate instantiations of the software.
	 * 
	 * @param numberOfAIs The number of AI's to spawn.
	 */
	private static void spawnAIs(int numberOfAIs, char difficultyOfAIOne, char difficultyOfAITwo, char difficultyOfAIThree){
		//return if the number of AIs is zero
		if(numberOfAIs == 0) return;

		//if there are more than zero AIs, spawn up to three
		try {
			if (!(difficultyOfAIOne == 'x')){
				String[] cmdarray1 = {"java", "-jar", System.getProperty("user.dir") + "/Euchre.jar", "-ai", "" + difficultyOfAIOne};
				Runtime.getRuntime().exec(cmdarray1);
			}
			if (!(difficultyOfAITwo == 'x')){
				String[] cmdarray2 = {"java", "-jar", System.getProperty("user.dir") + "/Euchre.jar", "-ai", "" + difficultyOfAITwo};
				Runtime.getRuntime().exec(cmdarray2);
			}
			if (!(difficultyOfAIThree == 'x')){
				String[] cmdarray3 = {"java", "-jar", System.getProperty("user.dir") + "/Euchre.jar", "-ai", "" + difficultyOfAIThree};
				Runtime.getRuntime().exec(cmdarray3);
			}
		} 
		catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * This method will just create a host object, and will also create the appropriately
	 * specified number of AI and Human players to accompany.
	 * 
	 * @param GM The GameManager object for the network and to pass the new host to.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	private static void createHostPlayer(GameManager GM) throws InterruptedException{

		//create the new host and it's game board 
		GM.newPlayer(new Human());
		GameBoard GB = new GameBoard();
		GB.setGameManager(GM);
		GM.setGameBoard(GB);

		//create a new server for the host
		ServerNetworkManager server = createNewServer(GM);

		//open the window for the user to input the game data
		HostGameSetup hostSetup = new HostGameSetup(GM);
		hostSetup.setVisible(true);

		//make the specified number of AI's once the user specifies the correct number of AIs
		while (hostSetup.getAIs()==-1) Thread.sleep(500);
		spawnAIs(hostSetup.getAIs(), 'x' ,hostSetup.getGameLobby().getPlayer3Difficulty(), hostSetup.getGameLobby().getPlayer4Difficulty());

		//wait until the user has input name and number of additional human players	
		while (hostSetup.getGameLobby() == null || hostSetup.getGameLobby().isSetupComplete() == false) Thread.sleep(500);

		//initialize the host's game board
		initializeGameBoard(GB);

		//spawn client game boards
		server.toClients("SpawnGameBoard");
	}

	/**
	 * This method will create a client object.
	 * 
	 * @param GM The GameManager object for the network and to pass the new client to.
	 * @param GUI The welcome window for user input.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	private static void createClientPlayer(GameManager GM) throws InterruptedException{

		//make a new game board and a new human to pass to the game manager
		Human human = new Human();
		GameBoard GB = new GameBoard();
		GB.setGameManager(GM);
		GM.setGameBoard(GB);
		GM.newPlayer(human);

		//make a new window to ask for user input
		ClientGameSetup clientSetup = new ClientGameSetup();
		clientSetup.setVisible(true);

		//wait for user to input data
		while(clientSetup.hasInput() == false) Thread.sleep(500);

		//create new client and its network from given ip address and name
		ClientNetworkManager client = createNewClient(GM, clientSetup);
		client.toServer("RegisterPlayer,Human," + clientSetup.getClientName().trim() + "," + human.getPlayerID());

		//wait for everyone to join before continuing
		while(GM.areTeamsComplete() == false) Thread.sleep(500);
		clientSetup.dispose();

	}

	/**
	 * The method will create a local only game, it is for when a user chooses to play against
	 * three computers.
	 * 
	 * @param GM The GameManager object for the network and to pass the new host and new AI's to.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	private static void createLocalOnlyGame(GameManager GM) throws InterruptedException{

		//create the new host, its game board and its server
		GM.newPlayer(new Human());
		GameBoard GB = new GameBoard();
		GB.setGameManager(GM);
		GM.setGameBoard(GB);
		ServerNetworkManager server = createNewServer(GM);

		//create a window to ask for name and game info
		SetupLocal local = new SetupLocal();
		local.setVisible(true);

		//wait for ai difficulty information, then make the ai's
		while (local.getSetupComplete() == false) Thread.sleep(500);
		spawnAIs(3, local.getComputer1Difficulty(), local.getComputer2Difficulty(), local.getComputer3Difficulty());


		//wait for the AI's to finish spawning then initialize the host's game board
		Thread.sleep(2000);
		initializeGameBoard(GB);

		//wait half a second for the ai's to finish spawning, then spawn the client game boards
		server.toClients("SpawnGameBoard");
	}

	/**
	 * This method will create a client object.
	 * 
	 * @param GM The GameManager object for the network and to pass the new client to.
	 * @param GUI The welcome window for user input.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	private static void createAIPlayer(GameManager GM, String difficulty) throws InterruptedException{

		AI computer = null;
		//make a new game board and a new human to pass to the game manager
		if (difficulty == "e") computer = new EasyAI();
		else if (difficulty == "m") computer = new MediumAI();
		else if (difficulty == "h") computer = new HardAI();
		GameBoard GB = new GameBoard();
		GB.setGameManager(GM);
		GM.setGameBoard(GB);
		GM.newPlayer(computer);

		//create new client
		ClientNetworkManager client = new ClientNetworkManager();
		GM.setClientNetworkManager(client);
		client.setGameManager(GM);
		client.start();

		//join network game
		client.toServer("RegisterPlayer,AI,Computer One," + computer.getPlayerID());

		//wait for everyone to join before continuing
		while(GM.areTeamsComplete() == false) Thread.sleep(500);

	}

	/**
	 * This method creates a new server, and passes all of the needed references regarding it.
	 * 
	 * @param GM The GameManager that the server and it need a reference to and from.
	 */
	private static ServerNetworkManager createNewServer(GameManager GM){
		ServerNetworkManager network = new ServerNetworkManager();
		network.setGameManager(GM);
		network.start();
		GM.setServerNetworkManager(network);
		return network;
	}

	/**
	 * This method creates a new server, and passes all of the needed references regarding it.
	 * 
	 * @param GM The GameManager that the server and it need a reference to and from.
	 * @throws InterruptedException Not thrown, the program will wait for input forever because this is not thrown.
	 */
	private static ClientNetworkManager createNewClient(GameManager GM, ClientGameSetup clientSetup) throws InterruptedException{
		ClientNetworkManager client = new ClientNetworkManager(clientSetup.getIP());
		GM.setClientNetworkManager(client);
		client.setGameManager(GM);
		client.start();
		Thread.sleep(500);
		return client;
	}

	/**
	 * This method initializes the GameBoard.
	 * 
	 * @param GM The GameManager.
	 * @param GB The GameBoard.
	 */
	public static void initializeGameBoard(GameBoard GB){
		GB.setVisible(true);
		GB.updateBoard();
	}
}
