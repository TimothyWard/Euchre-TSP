package euchre.player;


/**
 * 
 * @author sdwilke
 * 
 */

public class Card{
	char suit;		//h = hearts, s = spades, c = clubs, d = diamonds
	char value;		// 9, 0 (0 = 10), a = ace, j = jack, q = queen, k = king
	
	/**
	 * creates a card object with the value and suit indicated
	 * 
	 * @param cardSuit	suit of the card
	 * @param cardValue	value on the card
	 */
	
	public Card(char cardValue, char cardSuit){
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
	
	/**
	 * 
	 * @param c
	 * @return 1 if this card is greater than the given card, 0 if they are equal, -1 if this card is less than the given card
	 */
	public int compareTo(Card c){
		int tempVal;
		int compVal;
		
		if(value=='9'){
			tempVal=9;
		}
		else if(value=='0'){
			tempVal=10;
		}
		else if(value=='j'){
			tempVal=11;
		}
		else if(value=='q'){
			tempVal=12;
		}
		else if(value=='k'){
			tempVal=13;
		}
		else{
			tempVal=14;
		}
		
		if(c.getCardValue()=='9'){
			compVal=9;
		}
		else if(c.getCardValue()=='0'){
			compVal=10;
		}
		else if(c.getCardValue()=='j'){
			compVal=11;
		}
		else if(c.getCardValue()=='q'){
			compVal=12;
		}
		else if(c.getCardValue()=='k'){
			compVal=13;
		}
		else{
			compVal=14;
		}
		
		if(suit==c.getSuit()){
			
			if(tempVal>compVal){
				return 1;
			}
			else if(tempVal<compVal){
				return -1;
			}
			else{
				return 0;
			}
			
		}
		return 0;
	}
}