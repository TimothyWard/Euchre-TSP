package euchre.game;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		GM.getHostSetup().dispose();

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
	 * @param GM
	 * @param GUI
	 */
	public static void createHost(GameManager GM, Welcome GUI){

		//start network connection
		ServerNetworkManager network = new ServerNetworkManager();
		network.start();

		GM.setPlayer(new Human(), false, false);
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
			GM.setPlayer(new AI(), false, true);
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
		ServerNetworkManager network = new ServerNetworkManager();
		network.start();
		GM.setPlayer(new Human(), true, false);
		while (GM.getLocalSetup().getSetupComplete() == false){
			//Do nothing, user is deciding game type.
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientNetworkManager AI1 = new ClientNetworkManager();
		AI1.start();
		GM.setPlayer(new AI(), true, false);
		ClientNetworkManager AI2 = new ClientNetworkManager();
		AI2.start();
		GM.setPlayer(new AI(), true, false);
		ClientNetworkManager AI3 = new ClientNetworkManager();
		AI3.start();
		GM.setPlayer(new AI(), true, false);
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
		GM.setPlayer(new Human(), false, true);
	}

	/**
	 * This method compares the scores of the two teams and returns the winning team
	 * if there is one or null otherwise.
	 * 
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
