package euchre.game;

import euchre.player.Card;

/**
 * 
 * @author Timothy Ward
 * 
 * This class holds all information pertaining to a single hand. This is a self contained nested class of the Round 
 * class, and is used solely for the purpose of holding data pertaining to a hand.
 *
 */
public class Hand{
	
	public Hand(){
		
	}
	
	public Hand(char suitLed, Card[] cardsPlayed) {
		super();
		this.suitLed = suitLed;
		this.cardsPlayed = cardsPlayed;
	}
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
