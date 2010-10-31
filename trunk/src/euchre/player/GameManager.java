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
	private Player playerIAm;
	private Player player1, player2, player3, player4;
	private Player dealer = player1;
	private Card upCard;
	private Deck deck = new Deck();
	private Player curPlayer;
	private Team teamOne = new Team(null, null);
	private Team teamTwo = new Team(null, null);
	private char led;
	private boolean teamsComplete = false;
	private GameBoard board;
	private GameLobby lobby;
	private ServerNetworkManager server = null;
	private ClientNetworkManager client = null;
	private Round round = null;

	Card[] hand1 = new Card[5];
	Card[] hand2 = new Card[5];
	Card[] hand3 = new Card[5];
	Card[] hand4 = new Card[5];

	/**
	 * Main test method for the Game Manager class.
	 * @param args
	 */
//			public static void main(String[] args) {
//				GameManager game = new GameManager();
//				Round round = new Round();
//				game.setRound(round);
//				//			Player player1 = new Human();
//				//			Player player2 = new Human();
//				//			Player player3 = new Human();
//				//			Player player4 = new Human();
//				//			game.p1 = player1;
//				//			game.p2 = player2;
//				//			game.p3 = player3;
//				//			game.p4 = player4;
//				//			game.setAllPlayers(player1, player2, player3, player4);
//				Player ai1 = new AI();
//				Player ai2 = new AI();
//				Player ai3 = new AI();
//				Player ai4 = new AI();
//				game.p1 = ai1;
//				game.p2 = ai2;
//				game.p3 = ai3;
//				game.p4 = ai4;
//				game.setAllPlayers(ai1, ai2, ai3, ai4);
//		
//				game.setTeam(1, 2);
//				game.setTeam(2, 2);
//				game.setTeam(3, 1);
//				game.setTeam(4, 1);
//				
//				for(int i=0;i<5;i++){
//					System.out.println("Round " + (i+1));
//					game.playGame();
//				}
//		
//			}

	/**
	 * Default Constructor for the GameManager class.
	 * 
	 */
	public GameManager() {

	}

	/**
	 * The actual control for playing through the game.
	 * While round has been initialized, deal cards, determine trump, and play a round of Euchre.
	 */
	public void playGame(){

		if(server != null)
			deal();	
		setTrump();
		playRound();
	}


	/**
	 * Creates a new deck of cards, shuffles the cards, and then
	 * deals five cards to each player, in groups of two and three.
	 * 
	 * Order of dealing is two, three, two, three, three, two, three, two
	 */
	private void deal(){

		deck = new Deck();									//Create a brand new deck of cards
		deck.shuffle();										//Shuffle the deck of cards
		curPlayer = dealer;
		
		for(int i=0;i<5;i++){
			hand1[i]=deck.drawCard();
			hand2[i]=deck.drawCard();
			hand3[i]=deck.drawCard();
			hand4[i]=deck.drawCard();
		}

		
		for(int i=0;i<5;i++){
			player1.drawCard(hand1[i]);
			player2.drawCard(hand2[i]);
			player3.drawCard(hand3[i]);
			player4.drawCard(hand4[i]);
		}

		upCard = deck.drawCard();
		board.setTurnedCard(upCard);
		round.setTurnedCard(upCard);

		

		server.toClients("SetHand,1,"+player1.getHand()[0]+","+player1.getHand()[1]+","+player1.getHand()[2]+","+
							player1.getHand()[3]+","+player1.getHand()[4]);
		
		server.toClients("SetHand,2,"+player2.getHand()[0]+","+player2.getHand()[1]+","+player2.getHand()[2]+","+
				player2.getHand()[3]+","+player2.getHand()[4]);
		
		server.toClients("SetHand,3,"+player3.getHand()[0]+","+player3.getHand()[1]+","+player3.getHand()[2]+","+
				player3.getHand()[3]+","+player3.getHand()[4]);

		server.toClients("SetHand,4,"+player4.getHand()[0]+","+player4.getHand()[1]+","+player4.getHand()[2]+","+
				player4.getHand()[3]+","+player4.getHand()[4]);
		
		server.toClients("SetTurnedCard,"+upCard);
		
		
		
		Game.initializeGameBoard(board);
		



	}


	/**
	 * Determines the trump suit for the round. First asks each player if they want the dealer to pick up the card.
	 * If none do, then it asks each player if they want to call suit. If none call suit, the dealer is forced to pick
	 * the trump suit.
	 */
	private void setTrump(){

		curPlayer = nextPlayer(dealer);									//The first player is the one after the dealer.

		//Check to see if any of the players 'order up' the card
		for(int i=0;i<3;i++){
			curPlayer.setTurn(true);
			if(curPlayer.orderUp(upCard)){
				if(teamOne.getPlayerOne()==curPlayer || teamOne.getPlayerTwo()==curPlayer){
					round.setTeamWhoOrdered(teamOne);
				}
				else{
					round.setTeamWhoOrdered(teamTwo);
				}
				curPlayer.setTurn(false);
				System.out.println("UpCard3: " + round.getTurnedCard());
				round.setTrumpSuit(round.getTurnedCard().getSuit());
				dealer.setTurn(true);
				deck.discardCard(dealer.discard());						//If a player orders it up, the dealer must discard a card
				dealer.drawCard(upCard);								//and pick up the upCard
				dealer.setTurn(false);
			}
			else{
				curPlayer.setTurn(false);
				curPlayer=nextPlayer(curPlayer);
			}
			curPlayer.setTurn(false);
		}
		//If no one has ordered up the upCard, ask them to pick a suit
		if(curPlayer.equals(dealer)){
			deck.discardCard(upCard);									//...and discard the upCard...


			curPlayer=nextPlayer(dealer);
			//...and check to see if any player picks a suit.
			for(int x=0;x<4;x++) {
				curPlayer.setTurn(true);
				if(curPlayer.callSuit() != 0){
					if(teamOne.getPlayerOne()==curPlayer || teamOne.getPlayerTwo()==curPlayer){
						round.setTeamWhoOrdered(teamOne);
					}
					else{
						round.setTeamWhoOrdered(teamTwo);
					}
					round.setTrumpSuit(curPlayer.callSuit());			//If a player calls suit, set trump equal to that suit
					curPlayer.setTurn(false);
					break;
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
					curPlayer.setTurn(false);
					break;
				}
			}
		}//End of picking suit

		//Tells any AI what the current trump is...
		Player temp = curPlayer;
		for(int a=0;a<3;a++){
			if(temp.isHuman()==false){
				((AI)temp).setTrump(round.getTrumpSuit());
				temp = nextPlayer(temp);
			}
		}//...end of telling AI trump

	}//End of setTrump

	/**
	 * Plays a round of Euchre, consisting of five hands.
	 * Determines the winner of each hand, and that player leads the next hand.
	 */
	private void playRound(){

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

				board.playCard(played[i], curPlayer.getNumber());

				//System.out.println(curPlayer.getNumber() + " " + played[i]);

				curPlayer.setTurn(false);
				curPlayer=nextPlayer(curPlayer);
			}
			//System.out.println("------");

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
	private Player trickWinner(Card[] cards){

		Card LA,LK,LQ,LJ,L10,L9,TRB,TLB,TA,TK,TQ,T10,T9;
		char trump = round.getTrumpSuit();
		char ledSuit = cards[0].getSuit();
		char sameColor;

		if(trump == 's') sameColor = 'c';
		else if(trump == 'c') sameColor = 's';
		else if(trump == 'd') sameColor = 'h';
		else sameColor = 'd';

		LA = new Card('a', ledSuit);
		LK = new Card('k', ledSuit);
		LQ = new Card('q', ledSuit);
		LJ = new Card('j', ledSuit);
		L10 = new Card('0', ledSuit);
		L9 = new Card('9', ledSuit);
		TRB = new Card('j', trump);
		TLB = new Card('j', sameColor);
		TA = new Card('a', trump);
		TK = new Card('k', trump);
		TQ = new Card('q', trump);
		T10 = new Card('0', trump);
		T9 = new Card('9', trump);

		int[] cardValue = {0,0,0,0};
		Player[] players = new Player[4];

		players[0] = curPlayer;
		players[1] = nextPlayer(players[0]);
		players[2] = nextPlayer(players[1]);
		players[3] = nextPlayer(players[2]);

		for (int i=0; i<4; i++){
			Card card = cards[i];
			if (card.equals(TRB)) cardValue[i] = 13;
			else if (card.equals(TLB)) cardValue[i] = 12;
			else if (card.equals(TA)) cardValue[i] = 11;
			else if (card.equals(TK)) cardValue[i] = 10;
			else if (card.equals(TQ)) cardValue[i] = 9;
			else if (card.equals(T10)) cardValue[i] = 8;
			else if (card.equals(T9)) cardValue[i] = 7;
			else if (card.equals(LA)) cardValue[i] = 6;
			else if (card.equals(LK)) cardValue[i] = 5;
			else if (card.equals(LQ)) cardValue[i] = 4;
			else if (card.equals(LJ)) cardValue[i] = 3;
			else if (card.equals(L10)) cardValue[i] = 2;
			else if (card.equals(L9)) cardValue[i] = 1;
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
			return players[0];
		}
		else if(maxIndex==1){
			return players[1];
		}
		else if(maxIndex==2){
			return players[2];
		}
		else{
			return players[3];
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
		if (p1.getPlayerID()==host.getPlayerID()){
			playerIAm=host;
		}
		else if (p1.getPlayerID()==client1.getPlayerID()){
			playerIAm=client1;
		}
		else if (p1.getPlayerID()==client2.getPlayerID()){
			playerIAm=client2;
		}
		else if (p1.getPlayerID()==client3.getPlayerID()){
			playerIAm=client3;
		}
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
		dealer = player1;
		teamsComplete = true;
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



	public void newPlayer(Player p){
		p1=p;
	}

	public Player getDealer(){
		return dealer;
	}

	public Player getPlayerIAm(){
		return playerIAm;
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

	public Card[] getHand1() {
		return hand1;
	}

	public Card[] getHand2() {
		return hand2;
	}

	public Card[] getHand3() {
		return hand3;
	}

	public Card[] getHand4() {
		return hand4;
	}

	public void setRound(Round round){
		this.round = round;
	}

	public GameLobby getLobby(){
		return lobby;
	}

	public void setLobby(GameLobby gl){
		lobby = gl;
	}

	public void setGameBoard(GameBoard gb){
		board = gb;
	}

	public GameBoard getGameBoard(){
		return board;
	}

	public boolean areTeamsComplete(){
		return teamsComplete;
	}
	public void setTeamsComplete(boolean b){
		teamsComplete = b;
	}

	public void setServerNetworkManager(ServerNetworkManager s){
		server = s;
	}

	public void setClientNetworkManager(ClientNetworkManager c){
		client = c;
	}

	public ServerNetworkManager getServerNetworkManager(){
		return server;
	}

	public ClientNetworkManager getClientNetworkManager(){
		return client;
	}
	
	public Round getRound(){
		return round;
	}

}