package euchre.player;

import euchre.network.ClientNetworkManager;

/**
 * 
 * @author Kyle Kary
 *
 */




public class AI implements Player{

	private String name = "";
	private Card[] hand = new Card[5];
	private Card[] played = new Card[3];
	private char trump;					//Trump suit: s=spades, h=hearts, d=diamonds, c=clubs
	private int team;
	private int numCards = 0;
	private int playerNumber;
	private Card playCard;
	private ClientNetworkManager clientManager;
	private Card LA,LK,LQ,LJ,L10,L9,TRB,TLB,TA,TK,TQ,T10,T9;
	private Card led = null;

	
	CardEvaluator calc = new CardEvaluator();
	
////Test for AI
//	public static void main(String[] args) {
//		AI aiPlayer = new AI();
//		Deck deck = new Deck();
//		deck.shuffle();
//		
//		Card[] played = new Card[4];
//		
//		aiPlayer.drawCard(deck.drawCard());
//		System.out.println("Hand:");
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
//	
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
			trump = c.getSuit();
			return true;
		}
		else{
			trump = 0;
			return false;
		}

	}

	/**
	 * Determines the best card to lead and plays it.
	 */
	public void leadCard(){
		//FIX
		//Highest and lowest cards need to use compareTo, not < or >

		if(calc.highestCardInHand(trump, trump, hand).equals(TRB)){
			playCard = calc.highestCardInHand(trump, trump, hand);
		}
		else if(calc.highestCardInHand(trump, trump, hand).equals(TLB)){
			playCard = calc.highestCardInHand(trump, trump, hand);
		}
//		else if(calc.){
//			playCard = highestCard(false);
//		}
		else{
			playCard = calc.lowestTrumpInHand(trump, trump, hand);
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

		if(played[0]==null && played[1]==null){
			led = played[2];
		}
		else if(played[0]==null){
			led = played[1];
		}
		else{
			led = played[0];
		}
		
		//If the AI has suit, they must follow suit...
		if(calc.hasLead(led.getSuit(), hand)){
			//If the AI's partner is winning this trick, play a lower card than them.
			if(played[2] != null && (played[1].compareTo(played[0])>0) && (played[1].compareTo(played[2])>0)){
				//FIX
				//Play lowest same-suit!
				playCard = lowestCard(false);
			}
			//If the AI's partner is not winning the trick, play a higher card than them.
			else{
				//FIX
				//Play highest same-suit!
				playCard = highestCard(true);
			}
		}
		//If the AI does not have suit...
		else{
			//If the AI's partner is winning the trick, play the lowest off suit card in the AI's hand
			if(played[2] != null && (played[1].compareTo(played[0])>0) && (played[1].compareTo(played[2])>0)){
				playCard = lowestCard(false);
			}
			//If the AI's partner is not winning the trick, play the lowest trump card in the AI's hand
			else{
				playCard = lowestCard(true);
			}
		}
	}

	/**
	 * Plays a card. Determines if it is leading or following, and acts accordingly.
	 * @param c The card to be played by the AI.
	 */
	public Card playCard(){
		
		TRB = new Card('j', trump);
		TLB = new Card('j', trump);
		TA = new Card('a', trump);
		TK = new Card('k', trump);
		TQ = new Card('q', trump);
		T10 = new Card('0', trump);
		T9 = new Card('9', trump);
		
		//FIX
		//Remove card from AI's hand after playing it.
		if(played[0]==null){
			leadCard();
		}
		else{
			followCard();
		}

		for(int i=0;i<numCards;i++){
			if(hand[i]==playCard){
				Card c = hand[numCards-1];
				hand[numCards-1]=hand[i];
				hand[i]=c;
				hand[numCards-1]=null;
				numCards--;
				return playCard;
			}
		}
		
		
		
		return playCard;

	}

	/**
	 * Determines if the AI has to follow suit with the lead card.
	 * @return True if the AI has suit, false if it does not.
	 */
	public boolean hasSuit(char c){
		
		
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
			for(int i=0;i<numCards;i++){
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
			for(int i=0;i<numCards;i++){
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
			for(int i=0;i<numCards;i++){
				if(hand[i].getCardValue()>highestCard.getCardValue() && hand[i].getSuit()==trump){
					highestCard = hand[i];
				}
			}
		}
		else{
			for(int i=0;i<numCards;i++){
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
	
	public void setPlayed(Card[] cards){
		for(int i=0;i<3;i++){
			played[2-i]=cards[i];
		}
	}
	
	public void setTrump(char tr){
		trump = tr;
	}
	@Override
	public boolean isHuman() {
		return false;
	}

}