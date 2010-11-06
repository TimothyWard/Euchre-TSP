package euchre.player;

import euchre.network.ClientNetworkManager;

/**
 * 
 * @author Kyle Kary
 * @author Neil MacBay(nmmacbay)
 */

public interface AI extends Player{

	/**
	 * Determines if the AI will order up the suit or pass on the trump suit, 
	 * and acts accordingly. Should only be called once per hand.
	 * @return True if the player orders up the suit, false if they pass
	 */
	public boolean orderUp(Card c);

	/**
	 * Plays a card. Determines if it is leading or following, and acts accordingly.
	 * @param c The card to be played by the AI.
	 */
	public Card playCard();

	/**
	 * Adds a given card to the AI's hand. If the hand is full, then it discards a card first and then draws the card.
	 * @param c The card to add
	 */
	public void drawCard(Card c);

	/**
	 * Discards the given card.
	 * @param c
	 */
	public Card discard();

	/**
	 * Asks the AI to call a suit. If the AI isn't happy with its hand, it will pass.
	 */
	public char callSuit(Card turnedDown);

	/**
	 * Returns the AI's hand
	 */
	public Card[] getHand();

	/**
	 * Send a message across the network 
	 * 
	 * @param message The tokenized message to send across the network (formatting to be defined)
	 */
	public void sendNetworkMessage(String message);

	/**
	 * Forces the AI to pick a suit of cards. The AI will pick the best possible suit and return it.
	 * @return The suit that the AI picked for trump
	 */
	public char stickDealer();

	/**
	 * Sets the AI's turn to true
	 */
	public void setTurn(boolean turn);

	/**
	 * Tells the AI what cards have been played so far this hand.
	 * @param cards
	 */
	public void setPlayed(Card[] cards);

	public int getPlayerID();

	public void setPlayerID(int playerID);

	public void setTrump(char tr);

	public boolean isHuman();

	public int getTeam();

	public void setTeam(int team);

	public String getName();

	public void setName(String n);

	public int getNumber();

	public void setNumber(int i);
	
	public void setCard(int number, char value, char suit);


}