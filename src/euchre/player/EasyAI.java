package euchre.player;

import euchre.gui.GameBoard;
import euchre.network.ClientNetworkManager;

/**
 * 
 * @author Neil MacBay(nmmacbay)
 * @author Kyle Kary
 *
 */
public class EasyAI implements AI{
	private String name = "Easy AI";
	private Card emptyCard = new Card('a', 's');
	private Card[] hand = {emptyCard,emptyCard,emptyCard,emptyCard,emptyCard};
	private Card[] played = new Card[3];
	private char trump;					//Trump suit: s=spades, h=hearts, d=diamonds, c=clubs
	private int team;
	private int numCards = 0;
	private int playerNumber;
	private Card playCard;
	private ClientNetworkManager clientManager;
	private Card led = null;
	private int playerID = (int)(Math.random()*5000000);


	public EasyAI(ClientNetworkManager client){
		clientManager = client;
	}

	public EasyAI(ClientNetworkManager client, String name2) {
		name = name2;
		clientManager = client;
	}

	/**
	 * The AI performs the appropriate actions for his turn.
	 */
	public void makeTurn(){
		GameBoard game = clientManager.getGameManager().getGameBoard();
		String action = game.whatToDo();
		played = new Card[3];
		Card []tmpPlayed = game.getPlayed();
		if (tmpPlayed[0]!=null)
			played[0] = tmpPlayed[0];
		if (tmpPlayed[0]!=null)
			played[1] = tmpPlayed[1];
		if (tmpPlayed[0]!=null)
			played[2] = tmpPlayed[2];
		if (action.equals("Nothing")){
			System.out.println("Something went horribly wrong, " + clientManager.getGameManager().getPlayerIAm().getName() + " died");
		}
		if (action.equals("Play Card")){
			Card toPlay = playCard();
			int cardNum = 0;
			for (int i=0; i<5; i++){
				if (toPlay.equals(hand[i])){
					cardNum = i+1;
				}
			}
			switch(cardNum){
			case 1:
				game.card1Clicked(null);
				break;
			case 2:
				game.card2Clicked(null);
				break;
			case 3:
				game.card3Clicked(null);
				break;
			case 4:
				game.card4Clicked(null);
				break;
			default: //case 5:
				game.card5Clicked(null);
				break;
			}
		}else if (action.equals("Pick Up")){
			int cardNum = 1 + pickUp(game.getTurnedCard());
			switch(cardNum){
			case 1:
				game.card1Clicked(null);
				break;
			case 2:
				game.card2Clicked(null);
				break;
			case 3:
				game.card3Clicked(null);
				break;
			case 4:
				game.card4Clicked(null);
				break;
			default: //case 5:
				game.card5Clicked(null);
				break;
			}
		}else if (action.equals("Call Suit")){
			char suit = callSuit(game.getTurnedCard());
			switch (suit){
			case 'c':
				game.clubsListener(null);
				break;
			case 'd':
				game.diamondsListener(null);
				break;
			case 's':
				game.spadesListener(null);
				break;
			case 'h':
				game.heartsListener(null);
				break;
			default: //pass
				game.suitPassListener(null);
				break;
			}
		}else if (action.equals("Call Order Up")){
			boolean pickUp = orderUp(game.getTurnedCard());
			if (pickUp){
				game.pickItUpButtonClicked(null);
			}else{
				game.passButtonClicked(null);
			}
		}else if (action.equals("Stuck Dealer")){
			char suit = stickDealer(game.getTurnedCard());
			switch (suit){
			case 'c':
				game.clubsListener(null);
				break;
			case 'd':
				game.diamondsListener(null);
				break;
			case 's':
				game.spadesListener(null);
				break;
			default: //case 'h':
				game.heartsListener(null);
				break;
			}
		}
	}

	/**
	 * Returns the index of the card to replace with the turned up card.
	 * 
	 * @param toPick The card to pick up.
	 * 
	 * @return The index of the card to replace with the turned up card.
	 */
	public int pickUp(Card toPick){
		Card theCard = CardEvaluator.lowestCardInHand(toPick.getSuit(), toPick.getSuit(), hand);
		int cardNum = 0;
		for (int i=0; i<5; i++){
			if (theCard.equals(hand[i])){
				cardNum = i;
			}
		}
		return cardNum;
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
	private void leadCard(){

		char sameColor;
		if(trump == 's') sameColor = 'c';
		else if(trump == 'c') sameColor = 's';
		else if(trump == 'd') sameColor = 'h';
		else sameColor = 'd';

		//If the AI has the Right Bower, play it
		if(CardEvaluator.highestCardInHand(trump, trump, hand).getSuit()==trump && CardEvaluator.highestCardInHand(trump, trump, hand).getCardValue()=='j'){
			playCard = CardEvaluator.highestCardInHand(trump, trump, hand);
		}
		//Or if the AI has the Left Bower, play it...
		else if(CardEvaluator.highestCardInHand(trump, trump, hand).getCardValue()=='j' && CardEvaluator.highestCardInHand(trump, trump, hand).getSuit()==sameColor){
			playCard = CardEvaluator.highestCardInHand(trump, trump, hand);
		}
		//Or if the AI has at least 3 trump, play the lowest trump card...
		else if(CardEvaluator.numberOfTrump(trump, hand)>=3){
			playCard = CardEvaluator.lowestTrumpInHand(trump, trump, hand);
		}
		//Otherwise, play the lowest card in the hand.
		else if(CardEvaluator.numberOfTrump(trump, hand)==0){
			playCard = CardEvaluator.highestCardInHand(trump, trump, hand);
		}
		else{
			playCard = CardEvaluator.lowestCardInHand(trump, led.getSuit(), hand);
		}

		//		if hand contains right bower, play right bower
		//		else if hand contains left bower, play left bower
		//		else if hand contains off-suit, play highest off-suit
		//		else play lowest trump
	}

	/**
	 * Determines the best card to follow with and plays it.
	 */
	private void followCard(){

		if(played[2]==null && played[1]==null){
			led = played[0];
		}
		else if(played[2]==null){
			led = played[1];
		}
		else{
			led = played[0];
		}


		//If the AI has suit, they must follow suit...
		if(CardEvaluator.hasLead(led.getSuit(), hand)){
			//Play highest same-suit!
			playCard = CardEvaluator.highestLeadInHand(trump, led.getSuit(), hand);
		}
		//If the AI does not have suit...
		else{
			//Play the lowest trump card in the AI's hand, or throw lowest off-suit.
			if(CardEvaluator.hasTrump(trump, hand)){
				playCard = CardEvaluator.lowestTrumpInHand(trump, led.getSuit(), hand);
			}
			else{
				playCard = CardEvaluator.lowestCardInHand(trump, led.getSuit(), hand);
			}

		}
		//If the card to be played (playCard) will not beat the current winning card, play the lowest card in hand
		if(CardEvaluator.cardValue(trump, led.getSuit(), playCard) < CardEvaluator.cardValue(trump, led.getSuit(), CardEvaluator.highestCardInHand(trump, led.getSuit(), played))){
			playCard = CardEvaluator.lowestCardInHand(trump, led.getSuit(), hand);
		}
		//Eventually will need something to recognize if it is possible to beat other players. If not possible, then AI
		//should play the lowest card.
	}

	/**
	 * Plays a card. Determines if it is leading or following, and acts accordingly.
	 * @param c The card to be played by the AI.
	 */
	public Card playCard(){

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
	public char callSuit(Card turnedDown) {

		int numHeart = 0;
		int numDiamond = 0;
		int numSpade = 0;
		int numClub = 0;

		numHeart = CardEvaluator.numberOfSuit('h', hand);
		numDiamond = CardEvaluator.numberOfSuit('d', hand);
		numSpade = CardEvaluator.numberOfSuit('s', hand);
		numClub = CardEvaluator.numberOfSuit('c', hand);
		switch(turnedDown.getSuit()){
		case 'h':
			numHeart = 0;
			break;
		case 'd':
			numDiamond = 0;
			break;
		case 's':
			numSpade = 0;
			break;
		default: //case 'c':
			numClub = 0;
			break;
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
		}

		return trump;

	}

	/**
	 * Send a message across the network 
	 * 
	 * @param message The tokenized message to send across the network (formatting to be defined)
	 */
	public void sendNetworkMessage(String message){

		clientManager.toServer(message);
	}

	/**
	 * Forces the AI to pick a suit of cards. The AI will pick the best possible suit and return it.
	 * @return The suit that the AI picked for trump
	 */
	@Override
	public char stickDealer(Card turnedDown) {

		int numHeart = 0;
		int numDiamond = 0;
		int numSpade = 0;
		int numClub = 0;

		numHeart = CardEvaluator.numberOfSuit('h', hand);
		numDiamond = CardEvaluator.numberOfSuit('d', hand);
		numSpade = CardEvaluator.numberOfSuit('s', hand);
		numClub = CardEvaluator.numberOfSuit('c', hand);
		switch(turnedDown.getSuit()){
		case 'h':
			numHeart = 0;
			break;
		case 'd':
			numDiamond = 0;
			break;
		case 's':
			numSpade = 0;
			break;
		default: //case 'c':
			numClub = 0;
			break;
		}

		int max = Math.max(numHeart, numDiamond);
		if(max==numHeart) trump='h';
		else trump = 'd';
		max = Math.max(numSpade, max);
		if(max==numSpade) trump = 's';
		max = Math.max(numClub, max);
		if(max==numClub) trump = 'c';

		return trump;

	}

	public boolean isHuman() {
		return false;
	}

	/**
	 * Sets the AI's turn to true
	 */
	@Override
	public void setTurn(boolean turn) {	
	
	}

	/**
	 * Tells the AI what cards have been played so far this hand.
	 * @param cards
	 */
	public void setPlayed(Card[] cards){
		int x = 0;
		for(int i=0;i<3;i++){
			if(cards[2-i] != null){
				played[x]=cards[2-i];
				x++;
			}
	
		}
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void setTrump(char tr){
		trump = tr;
	}

	/**
	 * Returns the AI's hand
	 */
	@Override
	public Card[] getHand() {
		return hand;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public int getNumber() {
		return playerNumber;
	}

	public void setNumber(int i) {
		playerNumber = i;
	}

	public void setCard(int number, char value, char suit){
		hand[number] = new Card(value, suit);
	}

}
