package euchre.player;

public interface Player {
	
	Card[] hand = new Card[5];
	
	public void drawCard(Card c);
	
	public boolean orderUp(Card c);

	public char callSuit(Card turnedDown);
	
	public Card playCard();

	public void setTeam(int i);
	
	public int getTeam();

	public Card discard();
	
	public void setName(String n);
	
	public String getName();

	public int getNumber();
	
	public void setNumber(int i);
	
	public Card[] getHand();
	
	public char stickDealer(Card turnedDown);
	
	public boolean isHuman();
	
	public void setTurn(boolean b);
	
	public int getPlayerID();
	
	public void setPlayerID(int i);
	
	public void setCard(int number, char value, char suit);

}
