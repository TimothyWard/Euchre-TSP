package euchre.player;

import euchre.game.*;
import euchre.gui.*;
import euchre.network.ClientNetworkManager;
import euchre.network.ServerNetworkManager;

/**
 * 
 * @author krkary
 *
 * The game manager acts as a sort of dealer. It asks each player if they want to order up a card, pick a suit, play a card, etc. in addition
 * to dealing the cards each hand. Also, the game manager passes important information on to the Round class, which in turn stores it to help 
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
	private char led;

	private ServerNetworkManager server;
	private ClientNetworkManager client;


	private Round round = null;
	

	//			public static void main(String[] args) {
	//				GameManager game = new GameManager();
	//				Round round = new Round();
	//				game.setRound(round);
	//	//			Player player = new Human();
	//	//			Player player2 = new Human();
	//	//			Player player3 = new Human();
	//	//			Player player4 = new Human();
	//				Player ai1 = new AI();
	//				Player ai2 = new AI();
	//				Player ai3 = new AI();
	//				Player ai4 = new AI();
	//	//			game.setPlayer(player);
	//	//			game.setPlayer(player2);
	//	//			game.setPlayer(player3);
	//	//			game.setPlayer(player4);
	//				
	//				game.setPlayer(ai1, true, true);
	//				game.setPlayer(ai2, true, false);
	//				game.setPlayer(ai3, true, false);
	//				game.setPlayer(ai4, true, false);
	//				
	//				game.setTeam(1, 2);
	//				game.setTeam(2, 2);
	//				game.setTeam(3, 1);
	//				game.setTeam(4, 1);
	//				game.setTrump();
	//				System.out.println(game.round.getTeamWhoOrdered()==game.getTeamTwo());
	//				System.out.println("Upcard: " + game.upCard.suit);
	//				System.out.println("Suit picked: " + game.round.getTrumpSuit());
	//			}


	public void setRound(Round round){
		this.round = round;
	}

	public GameManager() {

	}

	/**
	 * The actual control for playing through the game.
	 * While round has been initialized, deal cards, determine trump, and play a round of Euchre.
	 */
	public void playGame(){

		while(round != null){
			deal();
			setTrump();
			playRound();
		}
	}


	/**
	 * Deals five cards to each player, in groups of two and three.
	 * 
	 * Order of dealing is two, three, two, three, three, two, three, two
	 * 
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

	}


	/**
	 * Determines the trump suit for the round. First asks each player if they want the dealer to pick up the card.
	 * If none do, then it asks each player if they want to call suit. If none call suit, the dealer is forced to pick
	 * the trump suit.
	 */
	public void setTrump(){

		curPlayer = nextPlayer(dealer);									//The first player is the one after the dealer.

		//Check to see if any of the players 'order up' the card
		for(int i=0;i<3;i++){
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


			curPlayer=nextPlayer(dealer);
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

						round.setTrumpSuit(curPlayer.stickDealer());
						if(teamOne.getPlayerOne()==curPlayer || teamOne.getPlayerTwo()==curPlayer){
							round.setTeamWhoOrdered(teamOne);
						}
						else{
							round.setTeamWhoOrdered(teamTwo);
						}
					}
				}
			}
		}//End of picking suit

	}//End of setTrump

	/**
	 * Plays a round of Euchre, consisting of five hands.
	 * Determines the winner of each hand, and that player leads the next hand.
	 */
	public void playRound(){

		//Set the current player to the player to the left of the dealer
		curPlayer = nextPlayer(dealer);
		round.setRoundComplete(false);

		//Play five hands...
		for(int h=1;h<6;h++){
			Card[] played = new Card[4];

			//For each player, have them play a card
			for(int i=0;i<4;i++){
				curPlayer.setTurn(true);
				if(!curPlayer.isHuman()){
					((AI)curPlayer).setPlayed(played);
				}
				played[i] = curPlayer.playCard();
				curPlayer.setTurn(false);
				curPlayer=nextPlayer(curPlayer);
			}

			led=played[0].getSuit();

			round.setHand(h, played, led);

			//Sets the start player to the winner of the last trick.
			curPlayer = trickWinner(played);

		}

		round.setRoundComplete(true);
		dealer = nextPlayer(dealer);
	}

	/**
	 * Determines the winner of a trick. This isn't used for scoring, just to keep track of who
	 * leads each hand.
	 * @param cards An array of the cards played in the hand
	 * @return The player who won the hand/trick
	 */
	public Player trickWinner(Card[] cards){

		Card LA,LK,LQ,LJ,L10,L9,TRB,TLB,TA,TK,TQ,T10,T9;
		char trump = round.getTrumpSuit();

		LA = new Card('a', cards[0].getSuit());
		LK = new Card('k', cards[0].getSuit());
		LQ = new Card('q', cards[0].getSuit());
		LJ = new Card('j', cards[0].getSuit());
		L10 = new Card('0', cards[0].getSuit());
		L9 = new Card('9', cards[0].getSuit());
		TRB = new Card('j', trump);
		TLB = new Card('j', trump);
		TA = new Card('a', trump);
		TK = new Card('k', trump);
		TQ = new Card('q', trump);
		T10 = new Card('0', trump);
		T9 = new Card('9', trump);

		int[] cardValue = {0,0,0,0};

		for (int i=0; i<4; i++){
			Card card = cards[i];
			if (card.compareTo(TRB)==0) cardValue[i] = 13;
			if (card.compareTo(TLB)==0) cardValue[i] = 12;
			if (card.compareTo(TA)==0) cardValue[i] = 11;
			if (card.compareTo(TK)==0) cardValue[i] = 10;
			if (card.compareTo(TQ)==0) cardValue[i] = 9;
			if (card.compareTo(T10)==0) cardValue[i] = 8;
			if (card.compareTo(T9)==0) cardValue[i] = 7;
			if (card.compareTo(LA)==0) cardValue[i] = 6;
			if (card.compareTo(LK)==0) cardValue[i] = 5;
			if (card.compareTo(LQ)==0) cardValue[i] = 4;
			if (card.compareTo(LJ)==0) cardValue[i] = 3;
			if (card.compareTo(L10)==0) cardValue[i] = 2;
			if (card.compareTo(L9)==0) cardValue[i] = 1;
			else cardValue[i] = 0;
		}

		int maximum = cardValue[0];
		int maxIndex = 0;
		for (int i=1; i<cardValue.length; i++) {
			if (cardValue[i] > maximum) {
				maximum = cardValue[i]; 
				maxIndex = i;
			}
		}

		if(maxIndex==0){
			return player1;
		}
		else if(maxIndex==1){
			return player2;
		}
		else if(maxIndex==2){
			return player3;
		}
		else{
			return player4;
		}

	}


	/**
	 * Adds a host player.
	 * @param p The human player that is going to host the game. 
	 */
	public void setHostPlayer(Player p){
		if(p1==null){
			p1=p;
		}
	}
	
	/**
	 * Adds a client player. 
	 * 
	 * @param p The human player that is going to be a client in the game. 
	 */
	public void setClientPlayer(Player p){
		if(p1==null){
			p1=p;
		}
	}
	
	/**
	 * Adds all client and host players. 
	 * 
	 * @param host The human player that is going to be the host of the game. 
	 * @param client1 The first player that is going to be a client in the game.
	 * @param client2 The second player that is going to be a client in the game.
	 * @param client3 The third player that is going to be a client in the game.
	 */
	public void setAllPlayers(Player host, Player client1, Player client2, Player client3){
			p1=host;
			p2=client1;
			p3=client2;
			p4=client3;
	}
	
	/**
	 * Adds a host and three client players for a local only game. 
	 * 
	 * @param p The AI player that is going to be a client in the game. 
	 * @param p The AI player that is going to be a client in the game. 
	 * @param p The AI player that is going to be a client in the game. 
	 */
	public void setLocalPlayers(Player playerOne, Player playerTwo, Player playerThree){
		p1=playerOne;
		p2=playerTwo;
		p3=playerThree;
	}


	/**
	 * Returns a reference to team one
	 * @return Team one
	 */
	public Team getTeamOne(){
		return teamOne;
	}

	/**
	 * Returns a reference to team two
	 * @return Team two
	 */
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
	
	public Player getDealer(){
		return dealer;
	}

	public Player getPlayer1(){
		return player1;
	}

	public Player getPlayer2(){
		return player2;
	}

	public Player getPlayer3(){
		return player3;
	}

	public Player getPlayer4(){
		return player4;
	}
	
	public Player getp1(){
		return p1;
	}
	public Player getp2(){
		return p2;
	}
	public Player getp3(){
		return p3;
	}
	public Player getp4(){
		return p4;
	}
	

	/**
	 * Set reference to the network interface(server)
	 * 
	 * @param s
	 * @author mdhelgen
	 */
	public void setServerNetworkManager(ServerNetworkManager s){
		server = s;
	}

	/**
	 * Set reference to the network interface (client)
	 * 
	 * @param c
	 * @author mdhelgen
	 */
	public void setClientNetworkManager(ClientNetworkManager c){
		client = c;
	}

	/**
	 * Get reference to the network interface (server)
	 * 
	 * @return the ServerNetworkManager reference
	 * @author mdhelgen
	 */
	public ServerNetworkManager getServerNetworkManager(){
		return server;
	}

	/**
	 * Get reference to the network interface (client)
	 * 
	 * @return the ClientNetworkManager reference
	 * @author mdhelgen
	 */
	public ClientNetworkManager getClientNetworkManager(){
		return client;
	}

}