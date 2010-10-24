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
	private int playerNumber;
	private Card playCard;
	private ClientNetworkManager clientManager;
	private Card LA,LK,LQ,LJ,L10,L9,TRB,TLB,TA,TK,TQ,T10,T9;


//	public static void main(String[] args) {
//		AI aiPlayer = new AI();
//		Deck deck = new Deck();
//		deck.shuffle();
//		
//		aiPlayer.drawCard(deck.drawCard());
//		System.out.println(aiPlayer.hand[0].getSuit() + " " + aiPlayer.hand[0].getCardValue());
//		aiPlayer.drawCard(deck.drawCard());
//		System.out.println(aiPlayer.hand[1].getSuit() + " " + aiPlayer.hand[1].getCardValue());
//		aiPlayer.drawCard(deck.drawCard());
//		System.out.println(aiPlayer.hand[2].getSuit() + " " + aiPlayer.hand[2].getCardValue());
//		aiPlayer.drawCard(deck.drawCard());
//		System.out.println(aiPlayer.hand[3].getSuit() + " " + aiPlayer.hand[3].getCardValue());
//		aiPlayer.drawCard(deck.drawCard());
//		System.out.println(aiPlayer.hand[4].getSuit() + " " + aiPlayer.hand[4].getCardValue());
//		
//		System.out.println("----------------");
//		
//		System.out.println(aiPlayer.stickDealer());
//		aiPlayer.leadCard();
//		System.out.println(aiPlayer.playCard.getSuit()+ " " + aiPlayer.playCard.getCardValue());
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
			TRB = new Card('j', trump);
			TLB = new Card('j', trump);
			TA = new Card('a', trump);
			TK = new Card('k', trump);
			TQ = new Card('q', trump);
			T10 = new Card('0', trump);
			T9 = new Card('9', trump);
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
		//FIX
		//Highest and lowest cards need to use compareTo, not < or >

		if(highestCard(true)==TRB){
			playCard = highestCard(true);
			playCard();
		}
		else if(highestCard(true)==TLB){
			playCard = highestCard(true);
			playCard();
		}
		else if(highestCard(false).getSuit() != trump){
			playCard = highestCard(false);
			playCard();
		}
		else{
			playCard = lowestCard(true);
			playCard();
		}
		
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
				if(hand[i].compareTo(lowestCard)<0 && hand[i].getSuit()==trump){
					lowestCard = hand[i];
				}
			}
			if(lowestCard.getSuit()==trump){
				return lowestCard;
			}
			else{
				isTrump=false;
			}
		}
		//Finds the lowest off-suit card in the hand
		if(!isTrump){
			for(int i=0;i<hand.length;i++){
				if(hand[i].compareTo(lowestCard)<0 && hand[i].getSuit()!=trump){
					lowestCard = hand[i];
				}
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
			trump = 'h';
		}
		else if(numDiamond>=3){
			trump = 'd';
		}
		else if(numSpade>=3){
			trump = 's';
		}
		else if(numClub>=3){
			trump = 'c';
		}
		else{
			trump = 0;
			return trump;
		}
		
		TRB = new Card('j', trump);
		TLB = new Card('j', trump);
		TA = new Card('a', trump);
		TK = new Card('k', trump);
		TQ = new Card('q', trump);
		T10 = new Card('0', trump);
		T9 = new Card('9', trump);
		return trump;

	}
	
	/**
	 * Returns the player's team
	 */
	public int getTeam() {
		return team;
	}
	
	/**
	 * Sets the player's team
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * Returns the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * Returns the player's number.
	 */
	public int getNumber() {
		return playerNumber;
	}

	/**
	 * Sets the player's number.
	 */
	public void setNumber(int i) {
		playerNumber = i;
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

	@Override
	public char stickDealer() {
		//FIX
		trump = hand[2].getSuit();
		
		TRB = new Card('j', trump);
		TLB = new Card('j', trump);
		TA = new Card('a', trump);
		TK = new Card('k', trump);
		TQ = new Card('q', trump);
		T10 = new Card('0', trump);
		T9 = new Card('9', trump);
		return trump;
		
	}
	@Override
	public void setTurn(boolean turn) {
		// TODO Auto-generated method stub
		
	}

}