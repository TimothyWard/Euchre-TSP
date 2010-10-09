package euchre.player;


/**
 * 
 * @author Kyle Kary
 *
 */


/**
 * Imports?
 */


public class AI implements Player{
	
	
	private Card[] hand;
	private Card card1, card2, card3; 	//Card played by player to the AI's left, AI's partner, and AI's right, respectively
	private boolean isTurn = false;		//True if it is the AI's turn, false otherwise
	private char trump;					//Trump suit: s=spades, h=hearts, d=diamonds, c=clubs
	private int round;   			 	//Which round of the hand it currently is (1,2,3,4,5)
	private int tricks;  				//Number of tricks won by the AI and its partner

	/**
	 * The main method. Will probably end up waiting here for its turn, then 
	 * calling other methods to complete the turn.
	 * @param args
	 */
	public static void main(String[] args) {
		
//		while(isTurn==true){
//			if(round==1 && trump != null){
//				orderUp();
//			}
//			else{
//				
//			}
//
//		}

	}
	
	/**
	 * Determines if the AI will order up the suit or pass on the trump suit, 
	 * and acts accordingly. Should only be called once per hand.
	 * @return 
	 */
	public boolean orderUp(Card c){
//		if AI has a set amount of trump, order up the card
//		else pass
		
//		if trump is still null, and the trump card has been turned down
//			if the AI has a set amount of one suit, order up that suit
//			if the AI is dealer and cannot pass (stuck), pick suit with the highest total cards
//			else pass
		
		int numTrump = 0;
		trump = c.getSuit();
		
		for(int i=0;i<hand.length;i++){
			if(hand[i].getSuit()==trump){
				numTrump++;
			}
		}
		
		if(numTrump>=4){
			return true;
		}
		else{
			return false;
		}
		
		
	}
	
	/**
	 * Determines the best card to lead and plays it.
	 */
	public void leadCard(){
		
//		if hand contains right bower, play right bower
//		else if hand contains left bower, play left bower
//		else if hand contains off-suit, play highest off-suit
//		else play lowest trump
		isTurn=false;
	}
	
	/**
	 * Determines the best card to follow with and plays it.
	 */
	public void followCard(){
		
//		if hasSuit, check if partner has trick
//			if partnerHasTrick, play lowest same-suit
//			if !partnerHasTrick, play highest same suit
//		if !hasSuit, check if partner has trick
//			if !partnerHasTrick && AI has trump, play lowest trump
//			else play lowest off-suit

		isTurn=false;
	}
	
	/**
	 * Plays the given card.
	 * @param c The card to be played by the AI.
	 */
	public void playCard(Card c){
		
	}
	
	/**
	 * Determines if the AI has to follow suit with the lead card.
	 * @return True if the AI has suit, false if it does not.
	 */
	public boolean hasSuit(){
		return false;
		
	}
	
	/**
	 * Determines if the AI's partner is going to take the trick
	 * @return True if the partner is going to take it, false otherwise
	 */
	public boolean partnerHasTrick(){
		return false;
	}
	
	/**
	 * Finds the lowest card in the AI hand.
	 * @return the lowest card in the AI hand.
	 * @param trump True to check for lowest trump, false to check for lowest off-suit
	 */
	public Card lowestCard(boolean trump){
		return null;
	}
	

	
	/**
	 * Finds the highest card in the AI hand.
	 * @return the highest card in the AI hand.
	 * @param trump True to check for highest trump, false to check for highest off-suit
	 */
	public Card highestCard(boolean trump){
		return null;
	}

	@Override
	public void drawCard(Card c) {
		hand[0] = c;
		
	}

	@Override
	public void setTurn() {
		isTurn = true;
		
	}

	@Override
	public char callSuit() {
		
		int numHeart = 0;
		int numDiamond = 0;
		int numSpade = 0;
		int numClub = 0;
		
		for(int i=0;i<hand.length;i++){
			if(hand[i].getSuit()=='h'){
				numHeart++;
			}
			else if(hand[i].getSuit()=='d'){
				numDiamond++;
			}
			else if(hand[i].getSuit()=='s'){
				numSpade++;
			}
			else{
				numClub++;
			}
		}
		
		if(numHeart>=3){
			return 'h';
		}
		else if(numDiamond>=3){
			return 'd';
		}
		else if(numSpade>=3){
			return 's';
		}
		else if(numClub>=3){
			return 'c';
		}
		else{
			return 0;
		}
		
	}
	
	
	

}
