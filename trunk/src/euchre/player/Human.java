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
		return 0;
	}

	/**
	 * "Order up" the up-card
	 * @param Card c the Up-card
	 * @return True if ordered up, false otherwise
	 */
	public boolean orderUp(Card c) {
		return false;
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
	
	public void setActiveCard(Card c) {
		activeCard = c;
	}
	
	public Card[] getHand(){
		return hand;
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
	
	public boolean isTurn(){
		return isTurn;
	}
	
	public void setTurn(boolean turn){
		isTurn = turn;
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
	public char stickDealer() {
		// TODO Auto-generated method stub
		return 0;
	}


}