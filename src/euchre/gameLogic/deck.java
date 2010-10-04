package euchre.gameLogic;
import java.util.Arrays;
import java.util.Collections;

/**
 * 
 * @author sdwilke
 *
 */

public class Deck extends GameLogic{
	private card[] cardDeck = {new card('9','h'), new card('0','h'), new card('a','h'), new card('j','h'), new card('k','h'), new card('q','h'), 
								new card('9','s'), new card('0','s'), new card('a','s'), new card('j','s'), new card('k','s'), new card('q','s'), 
								new card('9','c'), new card('0','c'), new card('a','c'), new card('j','c'), new card('k','c'), new card('q','c'), 
								new card('9','d'), new card('0','d'), new card('a','d'), new card('j','d'), new card('k','d'), new card('q','d')};;
	private int cardsLeftInDeck;
	
	/**
	 * Constructor to create deck object.  Initializes a deck of card objects representing
	 * a proper Euchre deck and performs an initial shuffle of the deck
	 *
	 */
	
	public Deck(){
		cardsLeftInDeck = 24;
	}
	
	/**
	 * takes all cards currently in the Deck and randomizes their order
	 */
	
	public void shuffle(){
		Collections.shuffle(Arrays.asList(cardDeck)); 
	}
	
	/**
	 * removes one card object from deck and returns it
	 * 
	 * @return card removed from deck
	 */
	public card drawCard(){
		return cardDeck[cardsLeftInDeck - 1];
	}

	/**
	 * retrieves the number of cards still available in the deck
	 *
	 * @return number of cards left in the deck
	 */
	public int numberOfCards(){
		return cardsLeftInDeck;
	}

	/**
	 * takes a card object and returns it to the bottom of the deck
	 *
	 * @param c the card to be discarded
	 */
	public void discardCard(card c){
		for(int x = cardsLeftInDeck; x > 0; x--){
			cardDeck[x] = cardDeck[x-1];
		}
		cardDeck[0] = c;
	}
	
	
}