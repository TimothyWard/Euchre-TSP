package euchre.player;

public class GameManager {

	private Player player1, player2, player3, player4;
	private Player dealer = player1;
	private Card upCard;
	private Deck deck;
	private Player curPlayer;


	public void main(String[] args) {

		deal();												//Start by dealing the cards
		curPlayer = nextPlayer(dealer);						//The first player is the one after the dealer
		
		for(int i=0;i<4;i++){								//Check to see if any of the players 'order up' the card
			if(curPlayer.orderUp()){
				dealer.drawCard(upCard);					//If a player orders it up, the dealer must pick up the card
				//discard needed							//and discard a card
			}
			else{
				curPlayer=nextPlayer(curPlayer);
			}
		}
	}

	/**
	 * Deals five cards to each player, in groups of two and three.
	 */
	public void deal(){

		deck.shuffle();										//Shuffle the deck of cards
		curPlayer = dealer;

		for(int a=0;a<2;a++){								//Deals to each player twice
			int draw=2;										//The number of cards to deal a player
			for(int i=0;i<4;i++){							//Deals to each player
				curPlayer=nextPlayer(curPlayer);
				for(int x=0;x<draw;x++){					//Deals the appropriate number of cards to each player
					curPlayer.drawCard(deck.drawCard());	
				}

				if(draw==2){								//If the previous player was dealt 2 cards,
					draw=3;									//deal the next player 3 cards, and vice versa
				}
				else{
					draw=2;
				}

			}
		}

		upCard = deck.drawCard();

		dealer=nextPlayer(dealer);
	}


	/**
	 * Finds the next player after a given player
	 * @param p Given player
	 * @return The player after the given player
	 */
	public Player nextPlayer(Player p) {
		if(p==player1){
			return player2;
		}
		else if(p==player2){
			return player3;
		}
		else if(p==player3){
			return player4;
		}
		else{
			return player1;
		}
	}


}
