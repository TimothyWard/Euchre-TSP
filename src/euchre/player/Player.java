package euchre.player;

public interface Player {
	Card[] hand = new Card[5];
	public void drawCard(Card c);
	
	public boolean orderUp(Card c);

	public char callSuit();
}
