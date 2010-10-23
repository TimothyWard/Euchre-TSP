package euchre.player;

import euchre.network.ClientNetworkManager;
import euchre.network.EuchreProtocol;
import euchre.network.ServerNetworkManager;

public class Human implements Player{
	private String name = "";
	private Card[] hand = new Card[5];
	private Card toPlay = null;
	private int numCards = 0;
	private int team = 0;
	ClientNetworkManager clientManager;
	ServerNetworkManager serverManager;
	boolean isHost = false;
	EuchreProtocol protocol = new EuchreProtocol();
	
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
	 * @return char The suit to be trump: 'S' for spades, 'H' for hearts, 'D' for diamonds, and 'C' for clubs.
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
		while (toPlay==null){
			//Wait until the user clicks a card...
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Card c = toPlay;
		toPlay = null;
		return c;
	}

	public void setPlayCard(Card c){
		toPlay = c;
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

	@Override
	public Card discard() {
		numCards--;
		return null;
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