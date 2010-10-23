package euchre.player;

import euchre.network.ClientNetworkManager;


/**
 * 
 * @author Kyle Kary
 *
 */


/**
 * Imports?
 */


public class AI implements Player{

	private String name = "";
	private Card[] hand = new Card[5];
	private Card card1, card2, card3; 	//Card played by player to the AI's left, AI's partner, and AI's right, respectively
	private char trump;					//Trump suit: s=spades, h=hearts, d=diamonds, c=clubs
	private int round;   			 	//Which round of the hand it currently is (1,2,3,4,5)
	private int tricks;  				//Number of tricks won by the AI and its partner
	private int team;
	private int numCards = 0;
	private ClientNetworkManager clientManager;


	//	public static void main(String[] args) {
	//
	//	}

	public AI(){

	}
	/**
	 * 
	 * 
	 * @param client Reference to the network interface
	 */
	public AI(ClientNetworkManager client){
		clientManager = client;
	}

	/**
	 * Determines if the AI will order up the suit or pass on the trump suit, 
	 * and acts accordingly. Should only be called once per hand.
	 * @return True if the player orders up the suit, false if they pass
	 */
	public boolean orderUp(Card c){

		int numTrump = 0;
		trump = c.getSuit();

		for(int i=0;i<numCards;i++){
			if(hand[i].getSuit()==trump){
				numTrump++;
			}
		}

		if(numTrump>=3){
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
	}

	/**
	 * Plays a card. Determines if it is leading or following, and acts accordingly.
	 * @param c The card to be played by the AI.
	 */
	public Card playCard(){

		return null;

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
	 * Finds the lowest card in the AI hand, or the lowest trump card in the hand.
	 * @return the lowest card in the AI hand.
	 * @param trump True to check for lowest trump, false to check for lowest off-suit
	 */
	public Card lowestCard(boolean isTrump){
		Card lowestCard = hand[0];

		//Finds the lowest trump card in the hand
		if(isTrump){
			for(int i=0;i<hand.length;i++){
				if(hand[i].getCardValue()<lowestCard.getCardValue() && hand[i].getSuit()==trump){
					lowestCard = hand[i];
				}
			}
		}
		//Finds the lowest off-suit card in the hand
		else{
			for(int i=0;i<hand.length;i++){
				if(hand[i].getCardValue()<lowestCard.getCardValue() && hand[i].getSuit()!=trump){
					lowestCard = hand[i];
				}
			}
			//If there are no off-suit cards in the hand, return the lowest trump card.
			if(lowestCard==null){
				lowestCard = lowestCard(isTrump);
			}
		}
		
		return lowestCard;
	}



	/**
	 * Finds the highest card in the AI hand.
	 * @return the highest card in the AI hand.
	 * @param trump True to check for highest trump, false to check for highest off-suit
	 */
	public Card highestCard(boolean isTrump){
		Card highestCard = hand[0];

		if(isTrump){
			for(int i=0;i<hand.length;i++){
				if(hand[i].getCardValue()>highestCard.getCardValue() && hand[i].getSuit()==trump){
					highestCard = hand[i];
				}
			}
		}
		else{
			for(int i=0;i<hand.length;i++){
				if(hand[i].getCardValue()>highestCard.getCardValue() && hand[i].getSuit()!=trump){
					highestCard = hand[i];
				}
			}
		}
		
		return highestCard;
	}

	/**
	 * Adds a given card to the AI's hand. If the hand is full, then it discards a card first and then draws the card.
	 * @param c The card to add
	 */
	@Override
	public void drawCard(Card c) {
		if(numCards == 0){
			hand[0] = c;
		}
		else if(numCards > 0 && numCards < 5){
			hand[numCards] = c;
		}
		else if(numCards == 5){
			discard();
			hand[numCards] = c;
		}
		
		numCards++;

	}

	/**
	 * Discards the given card.
	 * @param c
	 */
	public Card discard() {
		numCards--;
		return null;
		
	}

	/**
	 * Asks the AI to call a suit. If the AI isn't happy with its hand, it will pass.
	 * If the AI is the dealer and is "stuck", it will pick the best suit possible.
	 */
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

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String n) {
		name = n;
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNumber(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card[] getHand() {
		return hand;
	}
	
	/**
	 * Send a message across the network 
	 * 
	 * @param message The tokenized message to send across the network (formatting to be defined)
	 */
	public void sendNetworkMessage(String message){
		
			clientManager.toServer(message);
	}

}
