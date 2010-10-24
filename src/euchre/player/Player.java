package euchre.player;

public interface Player {
	Card[] hand = new Card[5];
	
	
	public void drawCard(Card c);
	
	public boolean orderUp(Card c);

	public char callSuit();
	
	public Card playCard();

	public void setTeam(int i);
	
	public int getTeam();

	public Card discard();
	
	public void setName(String n);
	
	public String getName();

	public int getNumber();
	
	public void setNumber(int i);
	
	public Card[] getHand();
	
	public char stickDealer();
	
	/**
	 * Set's the player's turn to either true or false
	 * @param b Whether or not it is the player's turn
	 */
	public void setTurn(boolean b);
}
