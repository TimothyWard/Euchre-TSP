package euchre.game;
import euchre.gui.Welcome;
import euchre.player.*;



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
		System.out.println("Game Run");
		//declare new GUI window to ask if host or client
		Welcome GUI = new Welcome(this);
		GUI.setVisible(true);
		//if host, pass to network, if client pass to network with host object
		//declare a new round object and inform network to proceed
		//wait for input for each round, once a round has received all input...send to gameLogic for computation
		//once GameLogic has returned information regarding round winner and point information, store information
		//repeat with new round object if game has not resolved
		//once game winner is determined, inform network who won to update views.
	}
	
	Team we;
	Team they;
	
	/**
	 * @author Timothy Ward 
	 * This class is simply a place holder for two player in a team object.
	 */
	public class Team {
		protected Player playerOne;
		protected Player playerTwo;
		protected int score = 0;
		public Team(Player one, Player two){
			this.playerOne = one;
			this.playerTwo = two;
		}
	}

	/**
	 * This method compares the scores of the two teams and returns the winning team
	 * if there is one or null otherwise.
	 * 
	 * @return Team The winning team
	 */
	public Team gameWinner(){
		if(we.score == 10){
			return we;
		}
		else if(they.score == 10){
			return they;
		}
		else{
			return null;
		}
	}
}
