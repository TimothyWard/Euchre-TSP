package euchre.player;

public interface Player {
	Card[] hand = new Card[5];
	
	public Card drawCard(Card c);
	
	public boolean orderUp(Card c);

	public char callSuit();
	
	public Card playCard();

	public void setTeam(char c);
	
	public char getTeam();

	public Card discard();
}
