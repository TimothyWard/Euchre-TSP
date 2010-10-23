package euchre.game;
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
	 */
	public static void main(String [] args){
		
		//declare GUI welcome window and ask if host or client
		Welcome GUI = new Welcome();
		GUI.setVisible(true);
		while (GUI.getChoice() == 'x'){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		char choice = GUI.getChoice();
		GUI.setVisible(false);
		
		//setup host and client objects, in a new game
		GameManager GM = new GameManager();
		if (choice == 'h') createHost(GM, GUI);
		else if(choice == 'c') createClient(GM, GUI);
		else if(choice == 'a') createLocalOnlyGame(GM);
		GUI.dispose();
		GM.getHostSetup().dispose();
		
		//set teams
		while (GM.getOne().getPlayerOne() == null 
				|| GM.getOne().getPlayerTwo() == null 
				|| GM.getTwo().getPlayerOne() == null 
				|| GM.getTwo().getPlayerTwo() == null){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Team one = GM.getOne();
		Team they = GM.getTwo();
		
		//create a new tabulator and tell it which teams it is tabulating.
		GameLogic tabulator = new GameLogic(we, they);
		
		//wait for input for each round, once a round has received all input...send to gameLogic for computation
		//once GameLogic has returned information regarding round winner and point information, store information
		//repeat with new round object if game has not resolved
		//once game winner is determined, inform network who won to update views.
	}

	/**
	 * This method will just create a host object, and will also create the appropriately
	 * specified number of AI and Human players to accompany.
	 * 
	 * @param GM
	 * @param GUI
	 */
	public static void createHost(GameManager GM, Welcome GUI){

		//start network connection
		ServerNetworkManager network = new ServerNetworkManager();
		network.start();
		
		GM.setPlayer(new Human());
		while (GM.getHostSetup().getAIs() ==-1){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int aiNumber = GM.getHostSetup().getAIs();
		// create specified number of AI's
		while (aiNumber!=0){
			ClientNetworkManager AI = new ClientNetworkManager();
			AI.start();
			aiNumber--;
		}
	}
	
	/**
	 * The method will create a local only game, it is for when a user chooses to play against
	 * three computers.
	 * @param GM
	 */
	public static void createLocalOnlyGame(GameManager GM){
		GM.setPlayer(new Human());
		ClientNetworkManager AI1 = new ClientNetworkManager();
		AI1.start();
		GM.setPlayer(new AI());
		ClientNetworkManager AI2 = new ClientNetworkManager();
		AI2.start();
		GM.setPlayer(new AI());
		ClientNetworkManager AI3 = new ClientNetworkManager();
		AI3.start();
		GM.setPlayer(new AI());
	}

	/**
	 * This method will create a client object.
	 * @param GM
	 * @param GUI
	 */
	public static void createClient(GameManager GM, Welcome GUI){
		// add URL String argument to ClientNetworkManager to change host location
		ClientNetworkManager client = new ClientNetworkManager();
		client.start();
		GM.setPlayer(new Human());
	}

	/**
	 * This method compares the scores of the two teams and returns the winning team
	 * if there is one or null otherwise.
	 * 
	 * @return Team The winning team
	 */
	public Team gameWinner(Team one, Team two){
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
