package euchre.player;

public class GameManager {
	
	private Player player1, player2, player3, player4;
	private Player dealer = player1;
	
	private Deck deck;
	
	
	public static void main(String[] args) {
		
		
	}
	
	public void deal(){
		deck.shuffle();
		
		
		nextDealer(dealer);
	}

	private void nextDealer(Player p) {
		if(p==player1){
			dealer = player2;
		}
		else if(p==player2){
			dealer = player3;
		}
		else if(p==player3){
			dealer = player4;
		}
		else{
			dealer = player1;
		}
	}
	
	
}
