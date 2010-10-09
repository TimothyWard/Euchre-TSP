package euchre.game;
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
public class Round{
	protected char trumpSuit = ' ';
	protected Player playerWhoOrdered;
	protected char teamWhoOrdered = ' ';
	protected char suitLed = ' ';
	protected boolean alone = false;
	protected Card[] cardsPlayed = new Card[20];
	
	
	public char getTrumpSuit() {
		return trumpSuit;
	}
	public void setTrumpSuit(char trumpSuit) {
		this.trumpSuit = trumpSuit;
	}
	public Player getPlayerWhoOrdered() {
		return playerWhoOrdered;
	}
	public void setPlayerWhoOrdered(Player playerWhoOrdered) {
		this.playerWhoOrdered = playerWhoOrdered;
	}
	public char getTeamWhoOrdered() {
		return teamWhoOrdered;
	}
	public void setTeamWhoOrdered(char teamWhoOrdered) {
		this.teamWhoOrdered = teamWhoOrdered;
	}
	public char getSuitLed() {
		return suitLed;
	}
	public void setSuitLed(char suitLed) {
		this.suitLed = suitLed;
	}
	public boolean isAlone() {
		return alone;
	}
	public void setAlone(boolean alone) {
		this.alone = alone;
	}
	public Card[] getCardsPlayed() {
		return cardsPlayed;
	}
	public void setCardsPlayed(Card[] cardsPlayed) {
		this.cardsPlayed = cardsPlayed;
	}
	
}
