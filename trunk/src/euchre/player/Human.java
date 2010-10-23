package euchre.player;

public class Human implements Player{
	private String name = "";
	private Card[] hand = new Card[5];
	private int numCards = 0;
	
	public Human(){
		
	}

	/**
	 * Adds the given card to the player's hand.
	 * @param Card c The card to add to the player's  hand
	 * @return
	 */
	public void drawCard(Card c) {
		if(numCards>=5){
			discard();
		}
		hand[numCards] = c;
		numCards++;
	}

	/**
	 * Calls a suit to be trump.
	 * @return char The suit to be trump: 'S' for spades, 'H' for hearts, 'D' for diamonds, and 'C' for clubs.
	 *  Return 0 if not calling a suit
	 */
	public char callSuit() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * "Order up" the up-card
	 * @param Card c the Up-card
	 * @return True if ordered up, false otherwise
	 */
	public boolean orderUp(Card c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Card playCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTeam() {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTeam(int i) {
		// TODO Auto-generated method stub
		
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

}
