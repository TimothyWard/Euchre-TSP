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

	/**
	 * 
	 * @author Timothy Ward
	 * 
	 * This class holds all information pertaining to a single hand. This is a self contained nested class of the Round 
	 * class, and is used solely for the purpose of holding data pertaining to a hand.
	 *
	 */
	public class Hand{

		protected char suitLed = ' ';
		protected Card[] cardsPlayed = new Card[5];

		public Card[] getCardsPlayed() {
			return cardsPlayed;
		}
		public void setCardsPlayed(Card[] cardsPlayed) {
			this.cardsPlayed = cardsPlayed;
		}
		public char getSuitLed() {
			return suitLed;
		}
		public void setSuitLed(char suitLed) {
			this.suitLed = suitLed;
		}
		public void setCardPlayed(int card, Card cardsPlayed) {
			this.cardsPlayed[card] = cardsPlayed;
		}
		public Card getCardPlayed(int card) {
			return this.cardsPlayed[card];
		}
	}

	//properties
	private Hand hand1;
	public Hand getHand(int handNumber) {
		if (handNumber == 1){
			return hand1;
		}
		if (handNumber == 2){
			return hand2;
		}
		if (handNumber == 3){
			return hand3;
		}
		if (handNumber == 4){
			return hand4;
		}
		if (handNumber == 5){
			return hand5;
		}
		else{
			return null;
		}
	}
	
	private Hand hand2;
	private Hand hand3;
	private Hand hand4;
	private Hand hand5;
	private char trumpSuit = ' ';
	private Player playerWhoDealt = null;
	private Team teamWhoOrdered = null;
	private boolean roundComplete = false;
	private boolean alone = false;
	
	public void setHand(int hand, Card[] cardsPlayed, char suitLed){
		if (hand == 1){
			hand1.cardsPlayed = cardsPlayed;
			hand1.suitLed = suitLed;
		}
		if (hand == 2){
			hand2.cardsPlayed = cardsPlayed;
			hand2.suitLed = suitLed;
		}
		if (hand == 3){
			hand3.cardsPlayed = cardsPlayed;
			hand3.suitLed = suitLed;
		}
		if (hand == 4){
			hand4.cardsPlayed = cardsPlayed;
			hand4.suitLed = suitLed;
		}
		if (hand == 5){
			hand5.cardsPlayed = cardsPlayed;
			hand5.suitLed = suitLed;
		}
		else System.out.println("An invalid hand number was passed to add to this round");
	}
	public char getTrumpSuit() {
		return trumpSuit;
	}
	public void setTrumpSuit(char trumpSuit) {
		this.trumpSuit = trumpSuit;
	}
	public Player getPlayerWhoDealt() {
		return playerWhoDealt;
	}
	public void setPlayerWhoDealt(Player playerWhoDealt) {
		this.playerWhoDealt = playerWhoDealt;
	}
	public Team getTeamWhoOrdered() {
		return teamWhoOrdered;
	}
	public void setTeamWhoOrdered(Team teamWhoOrdered) {
		this.teamWhoOrdered = teamWhoOrdered;
	}
	public boolean isRoundComplete() {
		return roundComplete;
	}
	public void setRoundComplete(boolean roundComplete) {
		this.roundComplete = roundComplete;
	}
	public boolean alone() {
		return alone;
	}
	public void setAlone(boolean alone) {
		this.alone = alone;
	}
}
