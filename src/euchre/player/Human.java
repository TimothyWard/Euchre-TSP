package euchre.player;

import euchre.network.ClientNetworkManager;
import euchre.network.EuchreProtocol;
import euchre.network.ServerNetworkManager;

public class Human implements Player{
	
	private String name = "";
	private Card[] hand = new Card[5];
	private Card activeCard = null;
	private int numCards = 0;
	private int team = 0;
	private boolean isTurn = false;
	private char orderSuit = 0;
	private boolean orderUp = false;
	private int orderedUp = 0;
	private int playerID = (int)(Math.random()*5000000);
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	ClientNetworkManager clientManager;
	ServerNetworkManager serverManager;
	boolean isHost = false;
	EuchreProtocol protocol = new EuchreProtocol();

	public Human(){

	}

	/**
	 * 
	 * 
	 * @param client Reference to the network interface as a client
	 */
	public Human(ClientNetworkManager client){
		clientManager = client;
	}

	/**
	 * 
	 * 
	 * @param server Reference to the network interface as a host
	 */
	public Human(ServerNetworkManager server){
		serverManager = server;
		isHost = true;
	}

	/**
	 * Adds the given card to the player's hand.
	 * @param Card c The card to add to the player's  hand
	 * @return
	 */
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
	 * Calls a suit to be trump.
	 * @return char The suit to be trump: 's' for spades, 'h' for hearts, 'd' for diamonds, and 'c' for clubs.
	 *  Return 0 if not calling a suit
	 */
	public char callSuit() {
		while (orderSuit==0){
			//Wait until the user selects a suit
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(orderSuit=='x'){
			orderSuit=0;
			return 0;
		}
		else{
			char temp = orderSuit;
			orderSuit=0;
			return temp;
		}

	}

	public void setCallSuit(char c){
		orderSuit=c;
	}

	/**
	 * "Order up" the up-card
	 * @param Card c the Up-card
	 * @return True if ordered up, false otherwise
	 */
	public boolean orderUp(Card c) {

		while (orderedUp == 0){
			//Wait until the user selects a suit
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		orderedUp = 0;
		return orderUp;

	}

	public void setOrderUp(boolean b){
		orderedUp = 1;
		orderUp = b;
	}

	/**
	 * Plays the user selected card;
	 * @return Card the card selected by the user
	 */
	@Override
	public Card playCard() {
		while (activeCard==null){
			//Wait until the user clicks a card...
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Card c = activeCard;
		activeCard = null;
		return c;
	}

	/**
	 * Returns the number of the team the player is on
	 * @return int The team number of the player
	 */
	public int getTeam() {

		return team;
	}

	/**
	 * Sets the player's team number
	 * @param i The team number of the player
	 */
	public void setTeam(int i) {
		team = i;

	}

	/**
	 * Waits for the user to select a card to discard. Should only happen
	 * if the user is the dealer and is picking up the trump card.
	 */
	public Card discard() {

		//Should prompt user to discard a card...

		while (activeCard==null){
			//Wait until the user clicks a card...
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Card c = activeCard;
		activeCard = null;
		numCards--;
		return c;
	}

	/**
	 * Sets the active card. The active card is either the card the player is going to play
	 * or the card that the player is going to discard.
	 * @param c The card to be the active card.
	 */
	public void setActiveCard(Card c) {
		activeCard = c;
	}

	/**
	 * Returns the player's hand of cards as an array of cards
	 * @return An array of the player's cards
	 */
	public Card[] getHand(){
		return hand;
	}

	/**
	 * Returns the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set's the player's name
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * Return's the player's number
	 */
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the player's number
	 */
	public void setNumber(int i) {
		// TODO Auto-generated method stub	
	}

	/**
	 * Returns whether it is the player's turn or not
	 * @return True if it is, false if it isn't
	 */
	public boolean isTurn(){
		return isTurn;
	}

	/**
	 * Sets the players turn as either true or false
	 * @param turn True if it is the player's turn, false if it isn't
	 */
	public void setTurn(boolean turn){
		isTurn = turn;
	}

	/**
	 * Sticks the dealer and forces them to pick a suit for trump. Dealer cannot pass.
	 */
	public char stickDealer() {
		while (orderSuit==0){
			//Wait until the user selects a suit
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		char temp = orderSuit;
		orderSuit=0;
		return temp;

	}

	/**
	 * Send a message across the network 
	 * 
	 * @param message The tokenized message to send across the network (formatting to be defined)
	 */
	public void sendNetworkMessage(String message){

		if(isHost){
			serverManager.toClients(message);
		}
		else{
			clientManager.toServer(message);
		}
	}

	@Override
	public boolean isHuman() {
		return true;
	}



}