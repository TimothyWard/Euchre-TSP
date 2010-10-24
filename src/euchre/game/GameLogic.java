package euchre.game;

import euchre.game.Round.*;
import euchre.player.Card;
import euchre.player.Human;

/**
 * 
 * @author Timothy Ward
 * 
 * This class is solely responsible for game operations that pertain to the logic of the game.
 *
 */
public class GameLogic{

	/**
	//BEGIN TESTING CODE FOR THIS CLASS
	static Team ONE = new Team(new Human(), new Human());
	static Team TWO = new Team(new Human(), new Human());
	static Round test = new Round();

	public static void main(String[] args){
		Card[] hand1 = {new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s')};
		Card[] hand2 = {new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s')};
		Card[] hand3 = {new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s')};
		Card[] hand4 = {new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s')};
		Card[] hand5 = {new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s'),new Card('a','s')};
		test.setHand(1, hand1, 's');
		test.setHand(2, hand2, 's');
		test.setHand(3, hand3, 's');
		test.setHand(4, hand4, 's');
		test.setHand(5, hand5, 's');
		test.setAlone(false);
		test.setTeamWhoOrdered(ONE);
		test.setTrumpSuit('d');
		test.setRoundComplete(true);
		interpret(test, ONE, TWO);
	}
	//END TESTING CODE FOR THIS CLASS
	//This class must have all static methods for this test code to run.
	 **/

	/**
	 * 
	 * This method accepts a round object and interprets it relative to the game.
	 * 
	 * @param round The round object containing all information for a specific round.
	 * @param one The first team to be interpreted.
	 * @param two The second team to be interpreted.
	 */
	public void interpret(Round round, Team one, Team two){
		int teamOneTricks = 0;
		int teamTwoTricks = 0;
		for (int x=1; x<=5; x++){
			if (interpretHand(round, round.getHand(x), one, two) == one){
				teamOneTricks++;
			}
			else if (interpretHand(round, round.getHand(x), one, two) == two){
				teamTwoTricks++;
			}
		}

		//if the round winner took 5 tricks
		if ((teamOneTricks==5 && round.getTeamWhoOrdered()==one)||(teamTwoTricks==5 && round.getTeamWhoOrdered()==two)){
			//if the round winner took all five tricks alone 
			if (round.alone()){
				if (round.getTeamWhoOrdered()==one){
					incrementScore(one, 4);
				}
				else if (round.getTeamWhoOrdered()==two){
					incrementScore(two, 4);
				}
			}
			//if the round winner took all five tricks as a team 
			else{
				if (round.getTeamWhoOrdered()==one){
					incrementScore(one, 2);
				}
				else if (round.getTeamWhoOrdered()==two){
					incrementScore(two, 2);
				}
			}
		}
		//if the round winner took 3 or more tricks
		else if ((teamOneTricks>=3 && round.getTeamWhoOrdered()==one)||(teamTwoTricks>=3 && round.getTeamWhoOrdered()==two)){
			if (round.getTeamWhoOrdered()==one){
				incrementScore(one, 1);
			}
			else if (round.getTeamWhoOrdered()==two){
				incrementScore(two, 1);
			}
		}
		//if the round winner took less than 3 tricks
		else if ((teamOneTricks<3 && round.getTeamWhoOrdered()==one)||(teamTwoTricks<3 && round.getTeamWhoOrdered()==two)){
			if (round.getTeamWhoOrdered()==one){
				incrementScore(two, 2);
			}
			else if (round.getTeamWhoOrdered()==two){
				incrementScore(one, 2);
			}
		}

	}

	/**
	 * This method accepts a hand containing five cards and other information pertaining to 
	 * the hand. It then interprets what happened in the hand, and increments the score accordingly.
	 * 
	 * @param round The object holding all five hands as well as other round information.
	 * @param hand The object holding the five cards played in the round, and some other information.
	 * @param one The first team to be tabulated.
	 * @param two The second team to be tabulated.
	 * 
	 * @return The team that took the trick for this hand.
	 */
	public Team interpretHand(Round round, Hand hand, Team one, Team two){
		char trump = round.getTrumpSuit();
		Card TRB = new Card('j', trump);
		Card TLB = new Card('j', trump);
		Card TA = new Card('a', trump);
		Card TK = new Card('k', trump);
		Card TQ = new Card('q', trump);
		Card T10 = new Card('0', trump);
		Card T9 = new Card('9', trump);
		Card LA = new Card('a', hand.getCardPlayed(0).getSuit());
		Card LK = new Card('k', hand.getCardPlayed(0).getSuit());
		Card LQ = new Card('q', hand.getCardPlayed(0).getSuit());
		Card LJ = new Card('j', hand.getCardPlayed(0).getSuit());
		Card L10 = new Card('0', hand.getCardPlayed(0).getSuit());
		Card L9 = new Card('9', hand.getCardPlayed(0).getSuit());

		int[] cardValue = {0,0,0,0};

		for (int i=0; i<4; i++){
			Card card = hand.getCardPlayed(i);
			if (card.compareTo(TRB)==0) cardValue[i] = 13;
			else if (card.compareTo(TLB)==0) cardValue[i] = 12;
			else if (card.compareTo(TA)==0) cardValue[i] = 11;
			else if (card.compareTo(TK)==0) cardValue[i] = 10;
			else if (card.compareTo(TQ)==0) cardValue[i] = 9;
			else if (card.compareTo(T10)==0) cardValue[i] = 8;
			else if (card.compareTo(T9)==0) cardValue[i] = 7;
			else if (card.compareTo(LA)==0) cardValue[i] = 6;
			else if (card.compareTo(LK)==0) cardValue[i] = 5;
			else if (card.compareTo(LQ)==0) cardValue[i] = 4;
			else if (card.compareTo(LJ)==0) cardValue[i] = 3;
			else if (card.compareTo(L10)==0) cardValue[i] = 2;
			else if (card.compareTo(L9)==0) cardValue[i] = 1;
			else cardValue[i] = 0;
		}
		if (maxIndex(cardValue)==0 || maxIndex(cardValue)==2){
			return one;
		}
		else{
			return two;
		}
	}

	/**
	 * This method computes the max of a given array.
	 * 
	 * @param array The array to find the max of.
	 * @return The index of the maximum value of the array.
	 */
	public int maxIndex(int[] array) {
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
		System.out.println("Team " + team.getTeamNumber() + " earned " + points + " points!!!");
	}

}
