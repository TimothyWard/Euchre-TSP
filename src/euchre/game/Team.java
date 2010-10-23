package euchre.game;

import euchre.player.Player;

/**
 * @author Timothy Ward 
 * This class is simply a place holder for two player in a team object.
 */
public class Team {

	private Player playerOne = null;
	private Player playerTwo = null;
	private int score = 0;
	
	public Team(Player one, Player two){
		this.playerOne = one;
		this.playerTwo = two;
	}
	
	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}