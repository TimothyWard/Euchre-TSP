package euchre.player;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.JOptionPane;

import euchre.gui.GameBoard;
import euchre.network.ClientNetworkManager;

/**
 * 
 * @author Neil MacBay(nmmacbay)
 * @author Kyle Kary
 *
 */
public class HardAI implements AI{
	private String name = "Hard AI";
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


	public HardAI(ClientNetworkManager client){
		clientManager = client;
	}

	public HardAI(ClientNetworkManager client, String name2) {
		name = name2;
		clientManager = client;
	}

	/**
	 * The AI performs the appropriate actions for his turn.
	 */
	public void makeTurn(){
		GameBoard game = clientManager.getGameManager().getGameBoard();
		String action = game.whatToDo();
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
		int numHeart = 0;
		int numDiamond = 0;
		int numSpade = 0;
		int numClub = 0;
		char tmpLed;

		numHeart = CardEvaluator.numberOfSuit('h', hand);
		numDiamond = CardEvaluator.numberOfSuit('d', hand);
		numSpade = CardEvaluator.numberOfSuit('s', hand);
		numClub = CardEvaluator.numberOfSuit('c', hand);
		switch(toPick.getSuit()){
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
		if(max==numHeart) tmpLed='h';
		else tmpLed = 'd';
		max = Math.max(numSpade, max);
		if(max==numSpade) tmpLed = 's';
		max = Math.max(numClub, max);
		if(max==numClub) tmpLed = 'c';

		Card theCard = CardEvaluator.lowestCardInHand(toPick.getSuit(), tmpLed, hand);
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
		//Implements MetalHead's point system strategy for calling
		trump = c.getSuit();

		if (metalHeadPoints(hand, trump, true) >= 4){
			//insert going alone code.
			return true;
		}
		if (metalHeadPoints(hand, trump, false) >= 3){
			return true;
		}
		return false;
	}

	/**
	 * A point system for calling cards, developed by the alias MetalHead and submitted on website for public use.
	 * Reference: http://webspace.webring.com/people/nm/metalheadtlh/Stratagies.html
	 * 
	 * @param hand The hand the player has.
	 * @param trump The current trump.
	 * @return The amount of points for that given hand.
	 */
	public double metalHeadPoints(Card[] hand, char trump, boolean alone){
		double currentPoints = 0;
		char leftSuit;
		char otherSuit1;
		char otherSuit2;
		switch (trump){ //To find the suit of the left Bower.
		case 's':
			leftSuit = 'c';
			otherSuit1 = 'd';
			otherSuit2 = 'h';
			break;
		case 'c':
			leftSuit = 's';
			otherSuit1 = 'd';
			otherSuit2 = 'h';
			break;
		case 'd':
			leftSuit = 'h';
			otherSuit1 = 's';
			otherSuit2 = 'c';
			break;
		default: //case 'h':
			leftSuit = 'd';
			otherSuit1 = 's';
			otherSuit2 = 'c';
			break;
		}
		//Evaluations for trump cards.
		if (CardEvaluator.hasCardInHand('j', trump, hand)){
			currentPoints++;
		}
		if (CardEvaluator.hasCardInHand('j', leftSuit, hand)){
			currentPoints++;
			if (currentPoints == 1){//left without right.
				currentPoints = 0.5;
			}
		}
		if (CardEvaluator.hasCardInHand('a', trump, hand)){
			currentPoints++;
		}
		if (CardEvaluator.hasCardInHand('k', trump, hand)){
			currentPoints += 0.5;
		}
		if (CardEvaluator.hasCardInHand('q', trump, hand)){
			currentPoints += 0.5;
		}
		if (CardEvaluator.hasCardInHand('0', trump, hand)){
			currentPoints += 0.25;
		}
		if (CardEvaluator.hasCardInHand('9', trump, hand)){
			currentPoints += 0.25;
		}
		//Evaluations for numbers of a kind.
		if ((CardEvaluator.numberOfSuit(leftSuit, hand) >= 4 && CardEvaluator.hasCardInHand('j', leftSuit, hand))
				|| (CardEvaluator.numberOfSuit(leftSuit, hand) >= 3 && !CardEvaluator.hasCardInHand('j', leftSuit, hand))){
			currentPoints += 0.25;
		}
		if (CardEvaluator.numberOfSuit(otherSuit1, hand) >= 3){
			currentPoints += 0.25;
		}
		if (CardEvaluator.numberOfSuit(otherSuit2, hand) >= 3){
			currentPoints += 0.25;
		}
		//Evaluations for off-suit ace cards.
		if (CardEvaluator.hasCardInHand('a', leftSuit, hand)){
			currentPoints += 0.5;
		}
		if (CardEvaluator.hasCardInHand('a', otherSuit1, hand)){
			currentPoints += 0.5;
		}
		if (CardEvaluator.hasCardInHand('a', otherSuit2, hand)){
			currentPoints += 0.5;
		}
		//Counting on partner for one trick.
		if (!alone){
			currentPoints += 0.25;
		}
		//Evaluations for who's deal it is and the turned card. Some modifications.		
		int turnedVal = CardEvaluator.cardValue(trump, leftSuit, clientManager.getGameManager().getGameBoard().getTurnedCard());//Value of turned card.
		boolean me = clientManager.getGameManager().getDealer().equals(clientManager.getGameManager().getPlayerIAm()); //This AI is the dealer.
		boolean isTeam = clientManager.getGameManager().getDealer().getTeam() == clientManager.getGameManager().getPlayerIAm().getTeam(); //The dealer is on AI's Team.
		if (turnedVal == 13 || turnedVal == 11){//Point Values of 1
			if (me){
				currentPoints++;
			}else if (isTeam && !alone){
				currentPoints++;
			}else if (!isTeam){
				currentPoints--;
			}
		}
		if (turnedVal == 12 || turnedVal == 10 || turnedVal == 9){//Point Values of 0.5
			if (me){
				currentPoints += 0.5;
			}else if (isTeam && !alone){
				currentPoints += 0.5;
			}else if (!isTeam){
				currentPoints -= 0.5;
			}
		}
		if (turnedVal == 8 || turnedVal == 7){//Point Values of 0.25
			if (me){
				currentPoints += 0.25;
			}else if (isTeam && !alone){
				currentPoints += 0.25;
			}else if (!isTeam){
				currentPoints -= 0.25;
			}
		}
		System.out.println("Metal Head Points: " + currentPoints);
		return currentPoints;
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
			//If the AI's partner is winning this trick, play a lower card than them.
			if(played[2] != null && (played[1].compareTo(played[0])>0) && (played[1].compareTo(played[2])>0)){
				//Play lowest same-suit
				playCard = CardEvaluator.lowestLeadInHand(trump, led.getSuit(), hand);
			}
			//If the AI's partner is not winning the trick, play a higher card than them.
			else{
				//Play highest same-suit!
				playCard = CardEvaluator.highestLeadInHand(trump, led.getSuit(), hand);
			}
		}
		//If the AI does not have suit...
		else{
			//If the AI's partner is winning the trick, play the lowest off suit card in the AI's hand
			if(played[2] != null && (played[1].compareTo(played[0])>0) && (played[1].compareTo(played[2])>0)){
				playCard = CardEvaluator.lowestCardInHand(trump, led.getSuit(), hand);
			}
			//If the AI's partner is not winning the trick, play the lowest trump card in the AI's hand
			else{
				if(CardEvaluator.hasTrump(trump, hand)){
					playCard = CardEvaluator.lowestTrumpInHand(trump, led.getSuit(), hand);
				}
				else{
					playCard = CardEvaluator.lowestCardInHand(trump, led.getSuit(), hand);
				}

				//If the card to be played (playCard) will not beat the current winning card, play the lowest card in hand
				if(CardEvaluator.cardValue(trump, led.getSuit(), playCard) < CardEvaluator.cardValue(trump, led.getSuit(), CardEvaluator.highestCardInHand(trump, led.getSuit(), played))){
					playCard = CardEvaluator.lowestCardInHand(trump, led.getSuit(), hand);
				}

				//Eventually will need something to recognize if it is possible to beat other players. If not possible, then AI
				//should play the lowest card.
			}
		}
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

		double pHeart = 0;
		double pDiamond = 0;
		double pSpade = 0;
		double pClub = 0;

		if (turnedDown.getSuit() != 'h'){
			pHeart = metalHeadPoints(hand, 'h', false);
		}
		if (turnedDown.getSuit() != 'd'){
			pDiamond = metalHeadPoints(hand, 'd', false);
		}
		if (turnedDown.getSuit() != 's'){
			pSpade = metalHeadPoints(hand, 's', false);
		}
		if (turnedDown.getSuit() != 'c'){
			pClub = metalHeadPoints(hand, 'c', false);
		}

		double[] tmpArr = {pHeart, pDiamond, pSpade, pClub};
		Arrays.sort(tmpArr);

		if (tmpArr[3] == pHeart){
			if (metalHeadPoints(hand, 'h', true) >= 4){
				//insert going alone code.
				return 'h';
			}
			if (pHeart >= 3){
				return 'h';
			}else{
				return 0;
			}
		}
		if (tmpArr[3] == pDiamond){
			if (metalHeadPoints(hand, 'd', true) >= 4){
				//insert going alone code.
				return 'd';
			}
			if (pHeart >= 3){
				return 'd';
			}else{
				return 0;
			}
		}
		if (tmpArr[3] == pSpade){
			if (metalHeadPoints(hand, 's', true) >= 4){
				//insert going alone code.
				return 's';
			}
			if (pHeart >= 3){
				return 's';
			}else{
				return 0;
			}
		}
		if (tmpArr[3] == pClub){
			if (metalHeadPoints(hand, 'c', true) >= 4){
				//insert going alone code.
				return 'c';
			}
			if (pHeart >= 3){
				return 'c';
			}else{
				return 0;
			}
		}

		return trump;
	}

	/**
	 * Returns the AI's hand
	 */
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

	/**
	 * Forces the AI to pick a suit of cards. The AI will pick the best possible suit and return it.
	 * @return The suit that the AI picked for trump
	 */
	@Override
	public char stickDealer(Card turnedDown) {

		double pHeart = 0;
		double pDiamond = 0;
		double pSpade = 0;
		double pClub = 0;

		if (turnedDown.getSuit() != 'h'){
			pHeart = metalHeadPoints(hand, 'h', false);
		}
		if (turnedDown.getSuit() != 'd'){
			pDiamond = metalHeadPoints(hand, 'd', false);
		}
		if (turnedDown.getSuit() != 's'){
			pSpade = metalHeadPoints(hand, 's', false);
		}
		if (turnedDown.getSuit() != 'c'){
			pClub = metalHeadPoints(hand, 'c', false);
		}

		double[] tmpArr = {pHeart, pDiamond, pSpade, pClub};
		Arrays.sort(tmpArr);

		if (tmpArr[3] == pHeart){
			if (metalHeadPoints(hand, 'h', true) >= 4){
				//insert going alone code.
			}
			return 'h';
		}
		if (tmpArr[3] == pDiamond){
			if (metalHeadPoints(hand, 'd', true) >= 4){
				//insert going alone code.
			}
			return 'd';
		}
		if (tmpArr[3] == pSpade){
			if (metalHeadPoints(hand, 's', true) >= 4){
				//insert going alone code.
			}
			return 's';
		}
		if (tmpArr[3] == pClub){
			if (metalHeadPoints(hand, 'c', true) >= 4){
				//insert going alone code.
			}
			return 'c';
		}

		return trump;

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

	public boolean isHuman() {
		return false;
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
