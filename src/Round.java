import euchre.gameLogic.Card;
import euchre.player.*;
/**
 * 
 * @author Timothy Ward
 * 
 * This class holds all information pertaining to a single round. The only class in communication with this one is the
 * Game class. The game class writes information to this class as it becomes available from network. This object is then
 * passed to GameLogic, which interprets and passes back pertinent information to Game.
 *
 */
public class Round {
	
	private char trumpSuit = ' ';
	private Player playerWhoOrdered;
	private char teamWhoOrdered = ' ';
	private char suitLed = ' ';
	private boolean alone = false;
	private Card cardOne;
	private Card cardTwo;
	private Card cardThree;
	private Card cardFour;
	private Card cardFive;

	/**
	 * @return the trumpSuit
	 */
	public char getTrumpSuit() {
		return trumpSuit;
	}
	/**
	 * @param trumpSuit the trumpSuit to set
	 */
	public void setTrumpSuit(char trumpSuit) {
		this.trumpSuit = trumpSuit;
	}
	/**
	 * @return the playerWhoOrdered
	 */
	public Player getPlayerWhoOrdered() {
		return playerWhoOrdered;
	}
	/**
	 * @param playerWhoOrdered the playerWhoOrdered to set
	 */
	public void setPlayerWhoOrdered(Player playerWhoOrdered) {
		this.playerWhoOrdered = playerWhoOrdered;
	}
	/**
	 * @return the teamWhoOrdered
	 */
	public char getTeamWhoOrdered() {
		return teamWhoOrdered;
	}
	/**
	 * @param teamWhoOrdered the teamWhoOrdered to set
	 */
	public void setTeamWhoOrdered(char teamWhoOrdered) {
		this.teamWhoOrdered = teamWhoOrdered;
	}
	/**
	 * @return the suitLed
	 */
	public char getSuitLed() {
		return suitLed;
	}
	/**
	 * @param suitLed the suitLed to set
	 */
	public void setSuitLed(char suitLed) {
		this.suitLed = suitLed;
	}
	/**
	 * @return the alone
	 */
	public boolean isAlone() {
		return alone;
	}
	/**
	 * @param alone the alone to set
	 */
	public void setAlone(boolean alone) {
		this.alone = alone;
	}
	/**
	 * @return the cardOne
	 */
	public Card getCardOne() {
		return cardOne;
	}
	/**
	 * @param cardOne the cardOne to set
	 */
	public void setCardOne(Card cardOne) {
		this.cardOne = cardOne;
	}
	/**
	 * @return the cardTwo
	 */
	public Card getCardTwo() {
		return cardTwo;
	}
	/**
	 * @param cardTwo the cardTwo to set
	 */
	public void setCardTwo(Card cardTwo) {
		this.cardTwo = cardTwo;
	}
	/**
	 * @return the cardThree
	 */
	public Card getCardThree() {
		return cardThree;
	}
	/**
	 * @param cardThree the cardThree to set
	 */
	public void setCardThree(Card cardThree) {
		this.cardThree = cardThree;
	}
	/**
	 * @return the cardFour
	 */
	public Card getCardFour() {
		return cardFour;
	}
	/**
	 * @param cardFour the cardFour to set
	 */
	public void setCardFour(Card cardFour) {
		this.cardFour = cardFour;
	}
	/**
	 * @return the cardFive
	 */
	public Card getCardFive() {
		return cardFive;
	}
	/**
	 * @param cardFive the cardFive to set
	 */
	public void setCardFive(Card cardFive) {
		this.cardFive = cardFive;
	}
	
}
