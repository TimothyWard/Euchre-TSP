package euchre.player;

import euchre.game.*;
import euchre.gui.*;

/**
 * 
 * @author krkary
 *
 * The game manager acts as a sort of dealer. It asks each player if they want to order up a card, pick a suit, play a card, etc. in addition
 * to dealing the cards each hand. Also, the game manager passes important information on to the Round class, which in turn passes it on to 
 * run the game.
 */
public class GameManager {

	private Player player1, player2, player3, player4;
	private Player dealer = player1;
	private Card upCard;
	private Deck deck = new Deck();
	private Player curPlayer;
	private int curRound = 1;
	
	private Round round = new Round();
	private HostGameSetup hostSetup = new HostGameSetup();

	//Test class
//	public static void main(String[] args) {
//		GameManager game = new GameManager();
//		Player player = new Human();
//		Player aiPlayer = new AI();
//		Player aiPlayer2 = new AI();
//		Player aiPlayer3 = new AI();
//		game.setPlayer(player);
//		game.setPlayer(aiPlayer);
//		game.setPlayer(aiPlayer2);
//		game.setPlayer(aiPlayer3);
//		game.deal();
//	}
	

	/**
	 * @return the hostSetup
	 */
	public HostGameSetup getHostSetup() {
		return hostSetup;
	}

	/**
	 * @param hostSetup the hostSetup to set
	 */
	public void setHostSetup(HostGameSetup hostSetup) {
		this.hostSetup = hostSetup;
	}

	public GameManager() {
	
	}
	
	public void setTrump(){

		deal();															//Start by dealing the cards...

		curPlayer = nextPlayer(dealer);									//The first player is the one after the dealer.

		//Check to see if any of the players 'order up' the card
		for(int i=0;i<4;i++){
			if(curPlayer.orderUp(upCard)){
				round.setPlayerWhoOrdered(curPlayer);
				round.setTeamWhoOrdered(curPlayer.getTeam());
				round.setTrumpSuit(upCard.getSuit());
				deck.disCardCard(dealer.drawCard(upCard));				//If a player orders it up, the dealer must pick up the card
				//FIX													//and discard a card
			}
			else{
				curPlayer=nextPlayer(curPlayer);
			}
		}

		//If no one has ordered up the upCard, ask them to pick a suit
		if(curPlayer==dealer){									
			deck.disCardCard(upCard);									//...and discard the upCard...
			
			for(int x=0;x<4;x++){										//...and check to see if any player picks a suit.
				if(curPlayer.callSuit() != 0){
					round.setPlayerWhoOrdered(curPlayer);
					round.setTeamWhoOrdered(curPlayer.getTeam());
					round.setTrumpSuit(curPlayer.callSuit());			//If a player calls suit, set trump equal to that suit
				}
				else{													//Otherwise, pass to the next person.
					curPlayer=nextPlayer(curPlayer);
					if(curPlayer==dealer){								//If it has returned to the dealer, force the dealer to pick a suit.
						//FIX
						while(curPlayer.callSuit()==0){
							round.setPlayerWhoOrdered(curPlayer);
							round.setTeamWhoOrdered(curPlayer.getTeam());
							round.setTrumpSuit(curPlayer.callSuit());
						}
					}
				}
			}
		}//End of calling suit
		
		
		for(curRound=1;curRound<6;curRound++){
			
			Card[] playedCards = round.getCardsPlayed();
			
			if(curRound==1){
				curPlayer = nextPlayer(dealer);
			}
			
			for(int i=0;i<4;i++){
				playedCards[i] = curPlayer.playCard();
				curPlayer=nextPlayer(curPlayer);
			}
			
			
			
			
			
			
			
			
			
			
			
		}

		
		
		dealer = nextPlayer(dealer);
		
	}//End of GameManager


	/**
	 * Adds a given player. If there is no host (player1), add it there first. Then, add any new players into the
	 * first open player slot.
	 * @param p The human player that is going to host the game. Host will also be first dealer.
	 */
	
	//public void setPlayer(Player p, int num, char team){}
	public void setPlayer(Player p){
		if(player1==null){
			player1=p;
			hostSetup.setVisible(true);
			player1.setTeam('1');
		}
		else if(player2==null){
			player2=p;
			player2.setTeam('2');
		}
		else if(player3==null){
			player3=p;
			player3.setTeam('1');
		}
		else if(player4==null){
			player4=p;
			player4.setTeam('2');
		}

	}


	/**
	 * Deals five cards to each player, in groups of two and three.
	 */
	public void deal(){

		deck.shuffle();										//Shuffle the deck of cards
		curPlayer = dealer;
													
		for(int a=0;a<2;a++){								//Deals to each player twice
			
			int draw=3;										//The number of cards to deal a player
			
			for(int i=0;i<4;i++){							//Deals to each player
				
				if(draw==2){								//If the previous player was dealt 2 cards,
					draw=3;									//deal the next player 3 cards, and vice versa
				}
				else{
					draw=2;
				}
				
				curPlayer=nextPlayer(curPlayer);
				for(int x=0;x<draw;x++){					//Deals the appropriate number of cards to each player
					curPlayer.drawCard(deck.drawCard());	
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
