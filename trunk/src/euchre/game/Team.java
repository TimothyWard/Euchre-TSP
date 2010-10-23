package euchre.game;

import euchre.player.Player;

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