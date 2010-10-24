package euchre.game;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import euchre.gui.ClientGameSetup;
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
	 */
	public static void main(String [] args){
		//setup host and client objects, in a new game
		GameManager GM = new GameManager();
		
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

		
		if (choice == 'h') createHost(GM, GUI);
		else if(choice == 'c') createClient(GM, GUI);
		else if(choice == 'a') createLocalOnlyGame(GM);
		GUI.dispose();

		//set teams
		while (GM.getTeamOne().getPlayerOne() == null 
				|| GM.getTeamOne().getPlayerTwo() == null 
				|| GM.getTeamTwo().getPlayerOne() == null 
				|| GM.getTeamTwo().getPlayerTwo() == null){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Team one = GM.getTeamOne();
		Team two = GM.getTeamTwo();

		//create a new tabulator and tell it which teams it is tabulating.
		GameLogic tabulator = new GameLogic();


		while (gameWinner(one, two) == null){
			Round currentRound = new Round();
			while (currentRound.isRoundComplete()==false){
				//Do nothing, the round is not over
				try {
					Thread.sleep(500);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			tabulator.interpret(currentRound, one, two);
		}
		
		JOptionPane.showMessageDialog(null, "Team " + gameWinner(one, two).getTeamNumber() + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
		//once game winner is determined, inform network who won to update views.
	}

	/**
	 * This method will just create a host object, and will also create the appropriately
	 * specified number of AI and Human players to accompany.
	 * 
	 * @param GM The GameManager object for the network and to pass the new host to.
	 * @param GUI The welcome window for user input.
	 */
	public static void createHost(GameManager GM, Welcome GUI){

		//start network connection
		ServerNetworkManager network = new ServerNetworkManager();
		network.setGameManager(GM);
		network.start();

		HostGameSetup hostSetup = new HostGameSetup(GM);
		hostSetup.setVisible(true);
		GM.setHostPlayer(new Human());
		while (hostSetup.getAIs() ==-1){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// get info from GUI and give to GM and Network
		int aiNumber = hostSetup.getAIs();
		// create specified number of AI's
		while (aiNumber!=0){
			ClientNetworkManager AI = new ClientNetworkManager();
			AI.setGameManager(GM);
			GM.setClientPlayer(new AI());
			AI.toServer("Registerplayer," + "Computer " + aiNumber);
			AI.start();
			aiNumber--;
		}
	}

	/**
	 * The method will create a local only game, it is for when a user chooses to play against
	 * three computers.
	 * @param GM The GameManager object for the network and to pass the new host and new AI's to.
	 */
	public static void createLocalOnlyGame(GameManager GM){
		System.out.println("local game");
		ServerNetworkManager network = new ServerNetworkManager();
		network.start();
		network.setGameManager(GM);
		Human human = new Human();
		GM.setHostPlayer(human);
		SetupLocal local = new SetupLocal(human);
		local.setVisible(true);
		while (local.getSetupComplete() == false){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//get info out of GUI here and give to Network or GM
		ClientNetworkManager AI1 = new ClientNetworkManager();
		AI1.setGameManager(GM);
		AI1.toServer("Registerplayer," + "Computer One");
		AI1.start();
		
		ClientNetworkManager AI2 = new ClientNetworkManager();
		AI2.setGameManager(GM);
		AI2.toServer("Registerplayer," + "Computer Two");
		AI2.start();
		
		ClientNetworkManager AI3 = new ClientNetworkManager();
		AI3.setGameManager(GM);
		AI3.toServer("Registerplayer," + "Computer Three");
		AI3.start();
		
		GM.setLocalPlayers(new AI(), new AI(), new AI());
	}

	/**
	 * This method will create a client object.
	 * @param GM The GameManager object for the network and to pass the new client to.
	 * @param GUI The welcome window for user input.
	 */
	public static void createClient(GameManager GM, Welcome GUI){
		// add URL String argument to ClientNetworkManager to change host location
		Human human = new Human();
		GM.setClientPlayer(human);
		ClientGameSetup clientSetup = new ClientGameSetup(human);
		clientSetup.setVisible(true);
		ClientNetworkManager client = new ClientNetworkManager();
		client.setGameManager(GM);
		client.toServer("Registerplayer,"+ clientSetup.getClientName());
		client.start();

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
