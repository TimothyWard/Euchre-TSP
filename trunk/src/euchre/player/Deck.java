package euchre.player;
import java.util.Arrays;
import java.util.Collections;


/**
 * stack object containing all cards for a proper euchre deck
 * @author sdwilke
 *
 */

public class Deck{
	private Card[] cardDeck = {new Card('9','h'), new Card('0','h'), new Card('a','h'), new Card('j','h'), new Card('k','h'), new Card('q','h'), 
								new Card('9','s'), new Card('0','s'), new Card('a','s'), new Card('j','s'), new Card('k','s'), new Card('q','s'), 
								new Card('9','c'), new Card('0','c'), new Card('a','c'), new Card('j','c'), new Card('k','c'), new Card('q','c'), 
								new Card('9','d'), new Card('0','d'), new Card('a','d'), new Card('j','d'), new Card('k','d'), new Card('q','d')};;
	private int cardsLeftInDeck;
	
	public Deck(){
		cardsLeftInDeck = 24;
	}
	
	/**
	 * takes all Cards currently in the Deck and randomizes their order
	 */
	
	public void shuffle(){
		Collections.shuffle(Arrays.asList(cardDeck)); 
	}
	
	/**
	 * removes one Card object from deck and returns it
	 * 
	 * @return Card removed from deck
	 */
	public Card drawCard(){
		cardsLeftInDeck--;
		return cardDeck[cardsLeftInDeck];
		
	}

	/**
	 * retrieves the number of Cards still available in the deck
	 *
	 * @return number of Cards left in the deck
	 */
	public int getNumberOfCards(){
		return cardsLeftInDeck;
	}

	/**
	 * takes a Card object and returns it to the bottom of the deck
	 *
	 * @param c the Card to be disCarded
	 */
	public void discardCard(Card c){
		for(int x = cardsLeftInDeck; x > 0; x--){
			cardDeck[x] = cardDeck[x-1];
		}
		cardDeck[0] = c;
	}
	
	
}