package euchre.game;

import euchre.game.Round.*;

/**
 * 
 * @author Timothy Ward
 * 
 * This class is solely responsible for game operations that pertain to the logic of the game.
 *
 */
public class GameLogic{
	
	Team we;
	Team they;
	public GameLogic(Team we, Team they){
		this.we = we;
		this.they = they;
	}

	/**
	 * This method accepts a round object and interprets it relative to the game.
	 * @param roundSequence The round object containing all information for a specific round.
	 */
	public void interpret(Round roundSequence){
		// do stuff to change score based on this round's new information
		// interpret hand one and increment team one and two trick count
		// interpret hand two and increment team one and two trick count
		// interpret hand three and increment team one and two trick count
		// interpret hand four and increment team one and two trick count
		// interpret hand five and increment team one and two trick count

	}
	
	/**
	 * This method accepts a hand containing five cards and other information pertaining to 
	 * the hand. It then interprets what happened in the hand, and increments the score accordingly.
	 * @param hand The object holding the five cards played in the round, and some other information.
	 */
	public Team readHand(Hand hand){
		//lots of logic here
		return we;
	}
	
	/**
	 * This method increments the score of the specified team the amount specified.
	 * @param team The team whose score is to be changed
	 * @param points The number of points to be added to the specified team's score
	 */
	public void incrementScore(Team team, int points){
		team.setScore(team.getScore() + points);	
	}

}
