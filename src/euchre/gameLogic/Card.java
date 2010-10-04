package euchre.gameLogic;
/**
 * 
 * @author sdwilke
 * 
 */

public class Card extends Deck{
	char suit;		//h = hearts, s = spades, c = clubs, d = diamonds
	char value;		// 9, 0 (0 = 10), a = ace, j = jack, q = queen, k = king
	
	/**
	 * creates a card object with the value and suit indicated
	 * 
	 * @param cardSuit	suit of the card
	 * @param cardValue	value on the card
	 */
	
	public card(char cardValue, char cardSuit){
		suit = cardSuit;
		value = cardValue;
	}
	
	/**
	 * retrieves suit of card (ie. h, s, c, d)
	 * @return character representing suit of card
	 */
	
	public char getSuit(){
		return suit;
	}
	
	/**
	 * retrieves value on card (ie. a, 9, 0, j, q, k)
	 * @return character representing value on card
	 */
	
	public char getCardValue(){
		return value;
	}
}