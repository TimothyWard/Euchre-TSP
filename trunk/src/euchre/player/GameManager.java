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

	private Player p1,p2,p3,p4;
	private Player player1, player2, player3, player4;
	private Player dealer = player1;
	private Card upCard;
	private Deck deck = new Deck();
	private Player curPlayer;
	private Team teamOne = new Team(null, null);
	private Team teamTwo = new Team(null, null);
	private int curRound = 1;
	private char led;

	private Round round;
	private HostGameSetup hostSetup = new HostGameSetup(this);


	//Test class
//		public static void main(String[] args) {
//			GameManager game = new GameManager();
//			Player player = new Human();
//			Player player2 = new Human();
//			Player player3 = new Human();
//			Player player4 = new Human();
//			game.setPlayer(player);
//			game.setPlayer(player2);
//			game.setPlayer(player3);
//			game.setPlayer(player4);
//			game.setTeam(1, 2);
//			game.setTeam(2, 2);
//			game.setTeam(3, 1);
//			game.setTeam(4, 1);
//			game.deal();
//			game.playRound();
//	
//	//			System.out.println("Player 1's Hand:");
//	//			for(int i=0;i<5;i++){
//	//				System.out.println(((Human) player).getHand()[i]);
//	//			}
//	//			System.out.println("Player 2's Hand:");
//	//			for(int i=0;i<5;i++){
//	//				System.out.println(((Human) player).getHand()[i]);
//	//			}
//	//			System.out.println("Player 3's Hand:");
//	//			for(int i=0;i<5;i++){
//	//				System.out.println(((Human) player).getHand()[i]);
//	//			}
//	//			System.out.println("Player 4's Hand:");
//	//			for(int i=0;i<5;i++){
//	//				System.out.println(((Human) player).getHand()[i]);
//	//			}
//	
//		}


	public void setRound(Round round){
		this.round = round;
	}

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
				if(teamOne.getPlayerOne()==curPlayer || teamOne.getPlayerTwo()==curPlayer){
					round.setTeamWhoOrdered(teamOne);
				}
				else{
					round.setTeamWhoOrdered(teamTwo);
				}
				round.setTrumpSuit(upCard.getSuit());
				deck.disCardCard(dealer.discard());						//If a player orders it up, the dealer must discard a card
				dealer.drawCard(upCard);								//and pick up the upCard
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
					if(teamOne.getPlayerOne()==curPlayer || teamOne.getPlayerTwo()==curPlayer){
						round.setTeamWhoOrdered(teamOne);
					}
					else{
						round.setTeamWhoOrdered(teamTwo);
					}
					round.setTrumpSuit(curPlayer.callSuit());			//If a player calls suit, set trump equal to that suit
				}
				else{													//Otherwise, pass to the next person.
					curPlayer=nextPlayer(curPlayer);
					if(curPlayer==dealer){								//If it has returned to the dealer, force the dealer to pick a suit.
						//FIX
						while(curPlayer.callSuit()==0){
							if(teamOne.getPlayerOne()==curPlayer || teamOne.getPlayerTwo()==curPlayer){
								round.setTeamWhoOrdered(teamOne);
							}
							else{
								round.setTeamWhoOrdered(teamTwo);
							}
							round.setTrumpSuit(curPlayer.callSuit());
						}
					}
				}
			}
		}//End of picking suit

	}//End of setTrump

	/**
	 * Plays a round of Euchre, consisting of five hands.
	 * 
	 */
	public void playRound(){

		//If this is the first round, the current player is the player to the left of the dealer
		if(curRound==1){
				curPlayer = nextPlayer(dealer);
			}
		
		
		for(int h=1;h<6;h++){
			Card[] played = new Card[4];

			for(int i=0;i<4;i++){
				played[i] = curPlayer.playCard();
				curPlayer=nextPlayer(curPlayer);
			}
			led=played[0].getSuit();

			round.setHand(h, played, false, led);
		}




		dealer = nextPlayer(dealer);
	}


	/**
	 * Adds a given player. If there is no host (player1), add it there first. Then, add any new players into the
	 * first open player slot.
	 * @param p The human player that is going to host the game. Host will also be first dealer.
	 */

	//public void setPlayer(Player p, int num, char team){
	public void setPlayer(Player p){
		if(p1==null){
			p1=p;
			hostSetup.setVisible(true);
		}
		else if(p2==null){
			p2=p;
		}
		else if(p3==null){
			p3=p;
		}
		else if(p4==null){
			p4=p;
		}

	}

	public Team getTeamOne(){
		return teamOne;
	}

	public Team getTeamTwo(){
		return teamTwo;
	}

	/**
	 * Sets the given player on the given team
	 * @param player The number of the player
	 * @param team The team to put that player on
	 */
	public void setTeam(int player, int team){
		Player play;

		//Determines which player object is being referred to
		if(player==1){
			play = p1;
		}
		else if(player==2){
			play = p2;
		}
		else if(player==3){
			play = p3;
		}
		else{
			play = p4;
		}

		//Sets the given player on the given team by putting that player in the corresponding "seat"
		//and then setting all of the appropriate team references
		if(team==1 && player1==null){
			player1=play;
			player1.setTeam(1);
			player1.setNumber(1);
		}
		else if(team==1 && player1!=null){
			player3=play;
			player3.setTeam(1);
			player3.setNumber(3);
		}
		else if(team==2 && player2==null){
			player2=play;
			player2.setTeam(2);
			player2.setNumber(2);
		}
		else if(team==2 && player2!=null){
			player4=play;
			player4.setTeam(2);
			player4.setNumber(4);
		}
		
		teamOne = new Team(player1,player3);
		teamTwo = new Team(player2,player4);

	}


	/**
	 * Deals five cards to each player, in groups of two and three.
	 */
	public void deal(){

		deck.shuffle();										//Shuffle the deck of cards
		curPlayer = dealer;

		int draw=3;											//The number of cards to deal a player

		for(int i=0;i<4;i++){								//Deals to each player

			if(draw==2){									//If the previous player was dealt 2 cards,
				draw=3;										//deal the next player 3 cards, and vice versa
			}
			else{
				draw=2;
			}

			curPlayer=nextPlayer(curPlayer);
			for(int x=0;x<draw;x++){						//Deals the appropriate number of cards to each player
				curPlayer.drawCard(deck.drawCard());	
			}

		}

		draw = 2;

		for(int i=0;i<4;i++){								//Deals to each player

			if(draw==2){									//If the previous player was dealt 2 cards,
				draw=3;										//deal the next player 3 cards, and vice versa
			}
			else{
				draw=2;
			}

			curPlayer=nextPlayer(curPlayer);
			for(int x=0;x<draw;x++){						//Deals the appropriate number of cards to each player
				curPlayer.drawCard(deck.drawCard());	
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
