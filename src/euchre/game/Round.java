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
	
	//properties
	private Hand hand1 = new Hand();
	private Hand hand2 = new Hand();
	private Hand hand3 = new Hand();
	private Hand hand4 = new Hand();
	private Hand hand5 = new Hand();
	private char trumpSuit = ' ';
	private Team teamWhoOrdered = null;
	private boolean roundComplete = false;
	private boolean alone = false;
	private Card turnedCard;
	

	public void setHand(int hand, Card[] cardsPlayed, char suitLed){
		if (hand == 1){
			hand1 = new Hand(suitLed, cardsPlayed);
		}else if (hand == 2){
			hand2 = new Hand(suitLed, cardsPlayed);
		}else if (hand == 3){
			hand3 = new Hand(suitLed, cardsPlayed);
		}else if (hand == 4){
			hand4 = new Hand(suitLed, cardsPlayed);
		}else if (hand == 5){
			hand5 = new Hand(suitLed, cardsPlayed);
			roundComplete = true; //Assumes the hands were filled out in order.
		}else{
			System.out.println("An invalid hand number was passed to add to this round");
		}
	}
	public Hand getHand(int handNumber) {
		if (handNumber == 1) return hand1;
		if (handNumber == 2) return hand2;
		if (handNumber == 3) return hand3;
		if (handNumber == 4) return hand4;
		if (handNumber == 5) return hand5;
		else return null;
	}
	public char getTrumpSuit() {
		return trumpSuit;
	}
	public void setTrumpSuit(char trumpSuit) {
		this.trumpSuit = trumpSuit;
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
	public Card getTurnedCard() {
		return turnedCard;
	}
	public void setTurnedCard(Card turnedCard) {
		this.turnedCard = turnedCard;
	}
}
