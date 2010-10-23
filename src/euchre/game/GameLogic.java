package euchre.game;

import euchre.game.Round.*;
import euchre.player.Card;

/**
 * 
 * @author Timothy Ward
 * 
 * This class is solely responsible for game operations that pertain to the logic of the game.
 *
 */
public class GameLogic{

	Team we;
	Team they;
	Card TRB;
	Card TLB;
	Card TA;
	Card TK;
	Card TQ;
	Card T10;
	Card T9;
	Card LA;
	Card LK;
	Card LQ;
	Card LJ;
	Card L10;
	Card L9;

	public GameLogic(Team we, Team they){
		this.we = we;
		this.they = they;
	}

	/**
	 * This method accepts a round object and interprets it relative to the game.
	 * @param roundSequence The round object containing all information for a specific round.
	 */
	public void interpret(Round round){
		TRB = new Card('j', round.getTrumpSuit());
		TLB = new Card('j', round.getTrumpSuit());
		TA = new Card('a', round.getTrumpSuit());
		TK = new Card('k', round.getTrumpSuit());
		TQ = new Card('q', round.getTrumpSuit());
		T10 = new Card('0', round.getTrumpSuit());
		T9 = new Card('9', round.getTrumpSuit());

		// do stuff to change score based on this round's new information
		// interpret hand one and increment team one and two trick count
		// interpret hand two and increment team one and two trick count
		// interpret hand three and increment team one and two trick count
		// interpret hand four and increment team one and two trick count
		// interpret hand five and increment team one and two trick count

	}

	/**
	 * This method accepts a hand containing five cards and other information pertaining to 
	 * the hand. It then interprets what happened in the hand, and increments the score accordingly.
	 * @param hand The object holding the five cards played in the round, and some other information.
	 */
	public Team interpretHand(Hand hand){
		LA = new Card('a', hand.getCardPlayed(0).getSuit());
		LK = new Card('k', hand.getCardPlayed(0).getSuit());
		LQ = new Card('q', hand.getCardPlayed(0).getSuit());
		LJ = new Card('j', hand.getCardPlayed(0).getSuit());
		L10 = new Card('0', hand.getCardPlayed(0).getSuit());
		L9 = new Card('9', hand.getCardPlayed(0).getSuit());
		int[] cardValue = {0,0,0,0};
		for (int i=0; i<4; i++){
			Card card = hand.getCardPlayed(i);
			if (card.compareTo(TRB)==0) cardValue[i] = 13;
			if (card.compareTo(TLB)==0) cardValue[i] = 12;
			if (card.compareTo(TA)==0) cardValue[i] = 11;
			if (card.compareTo(TK)==0) cardValue[i] = 10;
			if (card.compareTo(TQ)==0) cardValue[i] = 9;
			if (card.compareTo(T10)==0) cardValue[i] = 8;
			if (card.compareTo(T9)==0) cardValue[i] = 7;
			if (card.compareTo(LA)==0) cardValue[i] = 6;
			if (card.compareTo(LK)==0) cardValue[i] = 5;
			if (card.compareTo(LQ)==0) cardValue[i] = 4;
			if (card.compareTo(LJ)==0) cardValue[i] = 3;
			if (card.compareTo(L10)==0) cardValue[i] = 2;
			if (card.compareTo(L9)==0) cardValue[i] = 1;
			else cardValue[i] = 0;
		}
		if (maxIndex(cardValue)){
			
		}
	}

	/**
	 * This method computes the max of a given array.
	 * 
	 * @param array The array to find the max of.
	 * @return The index of the maximum value of the array.
	 */
	public static int maxIndex(int[] array) {
	    int maximum = array[0];
	    int maxIndex = 0;
	    for (int i=1; i<array.length; i++) {
	        if (array[i] > maximum) {
	            maximum = array[i]; 
	            maxIndex = i;
	        }
	    }
	    return maxIndex;
	}//end method max

	/**
	 * This method increments the score of the specified team the amount specified.
	 * @param team The team whose score is to be changed
	 * @param points The number of points to be added to the specified team's score
	 */
	public void incrementScore(Team team, int points){
		team.setScore(team.getScore() + points);	
	}

}
