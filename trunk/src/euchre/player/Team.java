package euchre.player;


/**
 * @author Timothy Ward 
 * This class is simply a place holder for two players in a team object.
 */
public class Team {

	//properties
	private Player playerOne = null;
	private Player playerTwo = null;
	private int teamNumber = 0;
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
	
	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}
}