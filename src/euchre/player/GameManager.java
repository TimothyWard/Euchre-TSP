package euchre.player;

public class GameManager {

	private Player player1, player2, player3, player4;
	private Player dealer = player1;

	private Deck deck;


	public static void main(String[] args) {


	}

	public void deal(){
		
		deck.shuffle();									//Shuffle the deck of cards
		int draw=2;										//The number of cards to deal a player
		Player curPlayer = dealer;

		for(int i=0;i<8;i++){							//Deals to each of the players twice
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

		dealer=nextPlayer(dealer);
	}

	/**
	 * Finds the next player after a given player
	 * @param p Given player
	 * @return The player after the given player
	 */
	private Player nextPlayer(Player p) {
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
