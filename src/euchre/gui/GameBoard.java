package euchre.gui;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

import euchre.game.GameLogic;
import euchre.game.Round;
import euchre.game.Team;
import euchre.game.Hand;
import euchre.gui.pictures.PictureManager;
import euchre.player.Card;
import euchre.player.CardEvaluator;
import euchre.player.GameManager;
import euchre.player.Human;
import euchre.player.Player;

/**
 * The GUI that displays the euchre game to the user and allows them to play it.
 * 
 * @author Neil MacBay (nmmacbay)
 * @author sdwilke
 */
public class GameBoard extends javax.swing.JFrame{
	private Player humanPlayer;
	private PictureManager picManager = new PictureManager();
	private static final long serialVersionUID = 1L;
	private GameManager GM;
	private Player topPlayer;
	private Player leftPlayer;
	private Player rightPlayer;
	private Card turnedCard = new Card('e', 'x');
	private boolean pickUpPassed = false;
	private boolean suitButtonsUsed = false;
	private boolean settingSuit = false;
	private boolean pickItUp = false;
	private boolean gameplay = false;
	char trump = 'e';
	int cardsPlayed = 0;
	int hand = 1;
	private int playerCards[] = {5, 5, 5, 5};
	int leftPlayed = 0;
	int rightPlayed = 0;
	int upperPlayed = 0;
	char suitLed = 'e';
	int oneTricks = 0;
	int twoTricks = 0;
	Card[] played = new Card[4];
	Hand playedHand = new Hand();
	//private Round round = null;
	GameLogic tabulator = new GameLogic();
	private Team teamWhoOrdered = null;
	private Player playerWhoLed = null;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel LCard1;
	private javax.swing.JLabel LCard2;
	private javax.swing.JLabel LCard3;
	private javax.swing.JLabel LCard4;
	private javax.swing.JLabel LCard5;
	private javax.swing.JLabel LPlayed;
	private javax.swing.JLabel RCard1;
	private javax.swing.JLabel RCard2;
	private javax.swing.JLabel RCard3;
	private javax.swing.JLabel RCard4;
	private javax.swing.JLabel RCard5;
	private javax.swing.JLabel RPlayed;
	private javax.swing.JLabel TurnedCard;
	private javax.swing.JLabel UCard1;
	private javax.swing.JLabel UCard2;
	private javax.swing.JLabel UCard3;
	private javax.swing.JLabel UCard4;
	private javax.swing.JLabel UCard5;
	private javax.swing.JLabel UPlayed;
	private javax.swing.JLabel YourPlayed;
	private javax.swing.JButton clubsButton;
	private javax.swing.JButton diamondsButton;
	private javax.swing.JButton heartsButton;
	private javax.swing.JButton jButtonPass;
	private javax.swing.JButton jButtonPickUp;
	private javax.swing.JButton jButtonYourCard1;
	private javax.swing.JButton jButtonYourCard2;
	private javax.swing.JButton jButtonYourCard3;
	private javax.swing.JButton jButtonYourCard4;
	private javax.swing.JButton jButtonYourCard5;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelDealer;
	private javax.swing.JLabel jLabelLPlayerName;
	private javax.swing.JLabel jLabelRPlayerName;
	private javax.swing.JLabel jLabelTurn;
	private javax.swing.JLabel jLabelUPlayerName;
	private javax.swing.JLabel jLabelYourName;
	private javax.swing.JButton spadesButton;
	private javax.swing.JButton suitPassButton;
	private javax.swing.JLabel theyLabel;
	private javax.swing.JLabel theyPointsLabel;
	private javax.swing.JLabel theyTeamNumberLabel;
	private javax.swing.JLabel theyTeamPointsLabel;
	private javax.swing.JLabel theyTeamTricksLabel;
	private javax.swing.JLabel theyTricksLabel;
	private javax.swing.JLabel trumpLabel;
	private javax.swing.JLabel weLabel;
	private javax.swing.JLabel wePointsLabel;
	private javax.swing.JLabel weTeamNumberLabel;
	private javax.swing.JLabel weTeamPointsLabel;
	private javax.swing.JLabel weTeamTricksLabel;
	private javax.swing.JLabel weTricksLabel;
	// End of variables declaration//GEN-END:variables
	private javax.swing.JButton[] handButtons = {jButtonYourCard1, jButtonYourCard2, jButtonYourCard3, jButtonYourCard4, jButtonYourCard5};
	/*
	private javax.swing.JLabel RCards[] = {RCard1, RCard2, RCard3, RCard4, RCard5};
	private javax.swing.JLabel LCards[] = {LCard1, LCard2, LCard3, LCard4, LCard5};
	private javax.swing.JLabel UCards[] = {UCard1, UCard2, UCard3, UCard4, UCard5};
	 */

	public GameBoard(){
		initComponents();
		centerScreen();
		hideSuitButtons();
		jLabelTurn.setVisible(false);
		handButtons[0] = jButtonYourCard1;
		handButtons[1] = jButtonYourCard2;
		handButtons[2] = jButtonYourCard3;
		handButtons[3] = jButtonYourCard4;
		handButtons[4] = jButtonYourCard5;
	}

	/**
	 * resets the board to start a new round
	 */
	public void newRound(){
		YourPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
		RPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
		LPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
		UPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
		UCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
		UCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
		UCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
		UCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
		UCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
		LCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		LCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		LCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		LCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		LCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		RCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		RCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		RCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		RCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		RCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
		showTrumpButtons();
		TurnedCard.setVisible(true);
		hideSuitButtons();

		jButtonYourCard1.setIcon(picManager.getPicture(humanPlayer.getHand()[0].getSuit(), humanPlayer.getHand()[0].getCardValue()));
		jButtonYourCard2.setIcon(picManager.getPicture(humanPlayer.getHand()[1].getSuit(), humanPlayer.getHand()[1].getCardValue()));
		jButtonYourCard3.setIcon(picManager.getPicture(humanPlayer.getHand()[2].getSuit(), humanPlayer.getHand()[2].getCardValue()));
		jButtonYourCard4.setIcon(picManager.getPicture(humanPlayer.getHand()[3].getSuit(), humanPlayer.getHand()[3].getCardValue()));
		jButtonYourCard5.setIcon(picManager.getPicture(humanPlayer.getHand()[4].getSuit(), humanPlayer.getHand()[4].getCardValue()));
		trumpLabel.setText("∅");
		trumpLabel.setForeground(new java.awt.Color(0, 0, 0));
		playerCards[0] = 5;
		playerCards[1] = 5;
		playerCards[2] = 5;
		playerCards[3] = 5;
		cardsPlayed = 0;
		suitLed = 'e';
		settingSuit=false;
		pickItUp=false;
		trump = 'e';
		gameplay=false;
		hand = 1;
		played = new Card[4];
		playedHand = new Hand();
		oneTricks = 0;
		twoTricks = 0;
		setWeTricks(0);
		setTheyTricks(0);
	}

	/**
	 * used to update the board to display cards etc. after the game manager and human player have been set
	 */
	public void updateBoard(){
		humanPlayer = GM.getPlayerIAm();
		setBottomPlayer(humanPlayer);

		switch(humanPlayer.getNumber()){
		case 1:{
			topPlayer = GM.getPlayer3();
			leftPlayer = GM.getPlayer2();
			rightPlayer = GM.getPlayer4();
			break;
		}
		case 2:{
			topPlayer = GM.getPlayer4();
			leftPlayer = GM.getPlayer3();
			rightPlayer = GM.getPlayer1();
			break;
		}
		case 3:{
			topPlayer = GM.getPlayer1();
			leftPlayer = GM.getPlayer4();
			rightPlayer = GM.getPlayer2();
			break;
		}
		case 4:{
			topPlayer = GM.getPlayer2();
			leftPlayer = GM.getPlayer1();
			rightPlayer = GM.getPlayer3();
			break;
		}
		}
		setTopPlayer(topPlayer);
		setLeftPlayer(leftPlayer);
		setRightPlayer(rightPlayer);
		setBottomPlayer(humanPlayer);

		if(GM.isMyTurn()){
			//JOptionPane.showMessageDialog(null, "Your Turn!  Play a card", "Your Turn", JOptionPane.INFORMATION_MESSAGE);
			jLabelTurn.setVisible(true);

			if(!settingSuit){
				jButtonPass.setVisible(true);
				jButtonPickUp.setVisible(true);
			}
			if(gameplay){
				jButtonPass.setVisible(false);
				jButtonPickUp.setVisible(false);
			}
			if(settingSuit)
				showSuitButtons();
		}
		else{
			jLabelTurn.setVisible(false);
			if(!settingSuit){
				jButtonPass.setVisible(false);
				jButtonPickUp.setVisible(false);
			}
			if(settingSuit)
				hideSuitButtons();
		}
		/*
		if(turnedCard.getSuit() != 'e' && !jButtonPickUp.isVisible() && pickUpPassed && !suitButtonsUsed){
			showSuitButtons();
		}
		else{
			hideSuitButtons();
		}*/
	}

	/**
	 * extracts card values from hand of player and displays them to buttons at bottom of board
	 * also displays player's name
	 * @param player the human player using the game board
	 */
	private void setBottomPlayer(Player player){
		jLabelYourName.setText(player.getName());
		setCard(player.getHand()[0], 0);
		setCard(player.getHand()[1], 1);
		setCard(player.getHand()[2], 2);
		setCard(player.getHand()[3], 3);
		setCard(player.getHand()[4], 4);
	}

	/**
	 * Moves the jFrame to the center of the screen
	 */
	private void centerScreen(){
		int xCenter = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
		int yCenter = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
		int xSize = this.getSize().width;
		int ySize = this.getSize().height;
		Point p = new Point();
		p.setLocation(xCenter - xSize / 2, yCenter - ySize / 2);
		this.setLocation(p);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jButtonYourCard1 = new javax.swing.JButton();
		jButtonYourCard2 = new javax.swing.JButton();
		jButtonYourCard4 = new javax.swing.JButton();
		jButtonYourCard5 = new javax.swing.JButton();
		jButtonYourCard3 = new javax.swing.JButton();
		jLabelUPlayerName = new javax.swing.JLabel();
		jLabelLPlayerName = new javax.swing.JLabel();
		jLabelRPlayerName = new javax.swing.JLabel();
		weTeamPointsLabel = new javax.swing.JLabel();
		weTricksLabel = new javax.swing.JLabel();
		weTeamTricksLabel = new javax.swing.JLabel();
		wePointsLabel = new javax.swing.JLabel();
		theyPointsLabel = new javax.swing.JLabel();
		theyTeamPointsLabel = new javax.swing.JLabel();
		theyTricksLabel = new javax.swing.JLabel();
		theyTeamTricksLabel = new javax.swing.JLabel();
		jButtonPass = new javax.swing.JButton();
		jButtonPickUp = new javax.swing.JButton();
		jLabelYourName = new javax.swing.JLabel();
		jLabelDealer = new javax.swing.JLabel();
		jLabelTurn = new javax.swing.JLabel();
		theyLabel = new javax.swing.JLabel();
		weLabel = new javax.swing.JLabel();
		theyTeamNumberLabel = new javax.swing.JLabel();
		weTeamNumberLabel = new javax.swing.JLabel();
		heartsButton = new javax.swing.JButton();
		clubsButton = new javax.swing.JButton();
		diamondsButton = new javax.swing.JButton();
		spadesButton = new javax.swing.JButton();
		suitPassButton = new javax.swing.JButton();
		UPlayed = new javax.swing.JLabel();
		LPlayed = new javax.swing.JLabel();
		YourPlayed = new javax.swing.JLabel();
		TurnedCard = new javax.swing.JLabel();
		RCard1 = new javax.swing.JLabel();
		RCard2 = new javax.swing.JLabel();
		RCard3 = new javax.swing.JLabel();
		RCard4 = new javax.swing.JLabel();
		RCard5 = new javax.swing.JLabel();
		LCard4 = new javax.swing.JLabel();
		LCard3 = new javax.swing.JLabel();
		LCard2 = new javax.swing.JLabel();
		LCard1 = new javax.swing.JLabel();
		LCard5 = new javax.swing.JLabel();
		UCard1 = new javax.swing.JLabel();
		UCard2 = new javax.swing.JLabel();
		UCard3 = new javax.swing.JLabel();
		UCard4 = new javax.swing.JLabel();
		UCard5 = new javax.swing.JLabel();
		RPlayed = new javax.swing.JLabel();
		trumpLabel = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Euchre Game Board");
		setBackground(java.awt.Color.lightGray);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setForeground(java.awt.Color.lightGray);
		setResizable(false);

		jButtonYourCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N
		jButtonYourCard1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jButtonYourCard1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				card1Clicked(evt);
			}
		});

		jButtonYourCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N
		jButtonYourCard2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				card2Clicked(evt);
			}
		});

		jButtonYourCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N
		jButtonYourCard4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				card4Clicked(evt);
			}
		});

		jButtonYourCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N
		jButtonYourCard5.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				card5Clicked(evt);
			}
		});

		jButtonYourCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N
		jButtonYourCard3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				card3Clicked(evt);
			}
		});

		jLabelUPlayerName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelUPlayerName.setText("Player name");

		jLabelLPlayerName.setText("Player name");

		jLabelRPlayerName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelRPlayerName.setText("Player name");

		weTeamPointsLabel.setText("0");

		weTricksLabel.setText("Tricks:");

		weTeamTricksLabel.setText("0");

		wePointsLabel.setText("Points:");

		theyPointsLabel.setText("Points:");

		theyTeamPointsLabel.setText("0");

		theyTricksLabel.setText("Tricks:");

		theyTeamTricksLabel.setText("0");

		jButtonPass.setText("Pass");
		jButtonPass.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				passButtonClicked(evt);
			}
		});

		jButtonPickUp.setText("Pick it up");
		jButtonPickUp.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				pickItUpButtonClicked(evt);
			}
		});

		jLabelYourName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelYourName.setText("Player name");

		jLabelDealer.setText("Your Deal:");

		jLabelTurn.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
		jLabelTurn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelTurn.setText("Your Turn!");

		theyLabel.setText("Team:");

		weLabel.setText("Team:");

		theyTeamNumberLabel.setText("1");

		weTeamNumberLabel.setText("2");

		heartsButton.setFont(new java.awt.Font("DejaVu Sans", 0, 14));
		heartsButton.setForeground(new java.awt.Color(255, 0, 0));
		heartsButton.setText("\u2665");
		heartsButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				heartsListener(evt);
			}
		});

		clubsButton.setFont(new java.awt.Font("DejaVu Sans", 0, 14));
		clubsButton.setText("\u2663");
		clubsButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				clubsListener(evt);
			}
		});

		diamondsButton.setFont(new java.awt.Font("DejaVu Sans", 0, 14));
		diamondsButton.setForeground(new java.awt.Color(255, 0, 0));
		diamondsButton.setText("\u2666");
		diamondsButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				diamondsListener(evt);
			}
		});

		spadesButton.setFont(new java.awt.Font("DejaVu Sans", 0, 14));
		spadesButton.setText("\u2660");
		spadesButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				spadesListener(evt);
			}
		});

		suitPassButton.setText("Pass");
		suitPassButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				suitPassListener(evt);
			}
		});

		UPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

		LPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

		YourPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

		TurnedCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

		RCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		RCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		RCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		RCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		RCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		LCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		LCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		LCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		LCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		LCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

		UCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

		UCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

		UCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

		UCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

		UCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

		RPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

		trumpLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
		trumpLabel.setText("\u2205");

		jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel1.setText("Trump Is: ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addGap(17, 17, 17)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(theyLabel)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(theyTricksLabel)
																.addComponent(theyPointsLabel)))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(theyTeamTricksLabel)
																		.addComponent(theyTeamPointsLabel)
																		.addComponent(theyTeamNumberLabel)))
																		.addGroup(layout.createSequentialGroup()
																				.addGap(41, 41, 41)
																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(LCard1)
																						.addComponent(LCard2)
																						.addComponent(LCard3)
																						.addComponent(LCard4)
																						.addComponent(LCard5)))
																						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																								.addContainerGap()
																								.addComponent(jLabelLPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
																								.addGroup(layout.createSequentialGroup()
																										.addGap(26, 26, 26)
																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																														.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(trumpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(20, 20, 20))
																														.addGroup(layout.createSequentialGroup()
																																.addGap(32, 32, 32)
																																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																																		.addComponent(suitPassButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																		.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
																																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																																						.addComponent(heartsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																						.addComponent(diamondsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																																								.addComponent(spadesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																								.addComponent(clubsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
																																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)))))
																																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																										.addGroup(layout.createSequentialGroup()
																																												.addGap(21, 21, 21)
																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																														.addGroup(layout.createSequentialGroup()
																																																.addComponent(jButtonYourCard1)
																																																.addGap(18, 18, 18)
																																																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																																																		.addGroup(layout.createSequentialGroup()
																																																				.addComponent(jButtonPass, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																																				.addComponent(jButtonPickUp, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
																																																				.addComponent(jLabelTurn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																																				.addGroup(layout.createSequentialGroup()
																																																						.addComponent(jButtonYourCard2)
																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																								.addComponent(jLabelYourName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																								.addGroup(layout.createSequentialGroup()
																																																										.addComponent(jButtonYourCard3)
																																																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																										.addComponent(jButtonYourCard4)))))
																																																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																										.addComponent(jButtonYourCard5))
																																																										.addGroup(layout.createSequentialGroup()
																																																												.addComponent(LPlayed)
																																																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																														.addComponent(YourPlayed)
																																																														.addComponent(UPlayed))
																																																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																														.addComponent(RPlayed)
																																																														.addGap(42, 42, 42)
																																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																																																.addComponent(jLabelDealer)
																																																																.addComponent(TurnedCard))
																																																																.addGap(66, 66, 66))))
																																																																.addGroup(layout.createSequentialGroup()
																																																																		.addGap(76, 76, 76)
																																																																		.addComponent(UCard1)
																																																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																		.addComponent(UCard2)
																																																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																				.addComponent(jLabelUPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																				.addGroup(layout.createSequentialGroup()
																																																																						.addComponent(UCard3)
																																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																						.addComponent(UCard4)
																																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																						.addComponent(UCard5)))))
																																																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																								.addGroup(layout.createSequentialGroup()
																																																																										.addGap(59, 59, 59)
																																																																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																																																																												.addComponent(weTricksLabel)
																																																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																														.addComponent(weLabel)
																																																																														.addComponent(wePointsLabel)))
																																																																														.addGap(18, 18, 18)
																																																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																																.addComponent(weTeamTricksLabel)
																																																																																.addComponent(weTeamPointsLabel)
																																																																																.addComponent(weTeamNumberLabel)))
																																																																																.addGroup(layout.createSequentialGroup()
																																																																																		.addGap(46, 46, 46)
																																																																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																																																				.addComponent(RCard1)
																																																																																				.addComponent(RCard2)
																																																																																				.addComponent(RCard3)
																																																																																				.addComponent(RCard4)
																																																																																				.addComponent(RCard5)
																																																																																				.addGroup(layout.createSequentialGroup()
																																																																																						.addGap(12, 12, 12)
																																																																																						.addComponent(jLabelRPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))))
																																																																																						.addGap(35, 35, 35))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap(72, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGap(145, 145, 145)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																								.addGroup(layout.createSequentialGroup()
																										.addComponent(UPlayed)
																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(YourPlayed))
																										.addGroup(layout.createSequentialGroup()
																												.addGap(52, 52, 52)
																												.addComponent(LPlayed)))
																												.addGap(108, 108, 108))
																												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																														.addComponent(jLabelDealer)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(TurnedCard)
																														.addGap(78, 78, 78)))
																														.addGap(30, 30, 30))
																														.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																.addComponent(RPlayed)
																																.addGap(196, 196, 196)))
																																.addComponent(jLabelTurn)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																		.addComponent(jButtonPass)
																																		.addComponent(jButtonPickUp))
																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
																																		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																				.addComponent(RCard1)
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																				.addComponent(RCard2)
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																				.addComponent(RCard3)
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																				.addComponent(RCard4)
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																				.addComponent(RCard5)
																																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																				.addComponent(jLabelRPlayerName)
																																				.addGap(48, 48, 48)))
																																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																																						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																										.addComponent(weLabel)
																																										.addComponent(weTeamNumberLabel))
																																										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																												.addComponent(weTeamPointsLabel)
																																												.addComponent(wePointsLabel))
																																												.addGap(18, 18, 18)
																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																														.addComponent(weTricksLabel)
																																														.addComponent(weTeamTricksLabel))
																																														.addGap(30, 30, 30))
																																														.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																.addComponent(jButtonYourCard5)
																																																.addComponent(jButtonYourCard4)
																																																.addComponent(jButtonYourCard3)
																																																.addComponent(jButtonYourCard2)
																																																.addComponent(jButtonYourCard1))))
																																																.addGroup(layout.createSequentialGroup()
																																																		.addGap(25, 25, 25)
																																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																				.addGroup(layout.createSequentialGroup()
																																																						.addComponent(jLabelUPlayerName)
																																																						.addGap(11, 11, 11)
																																																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																																								.addComponent(UCard1)
																																																								.addComponent(UCard2)
																																																								.addComponent(UCard3)
																																																								.addComponent(UCard4)
																																																								.addComponent(UCard5)))
																																																								.addGroup(layout.createSequentialGroup()
																																																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																												.addComponent(theyLabel)
																																																												.addComponent(theyTeamNumberLabel))
																																																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																														.addComponent(theyPointsLabel)
																																																														.addComponent(theyTeamPointsLabel))
																																																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																																.addComponent(theyTeamTricksLabel)
																																																																.addComponent(theyTricksLabel))
																																																																.addGap(16, 16, 16)
																																																																.addComponent(jLabelLPlayerName)
																																																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																.addComponent(LCard1)
																																																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																.addComponent(LCard2)
																																																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																.addComponent(LCard3)
																																																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																.addComponent(LCard4)
																																																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																																																.addComponent(LCard5)))
																																																																.addGap(27, 27, 27)
																																																																.addComponent(suitPassButton)
																																																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																																		.addComponent(heartsButton)
																																																																		.addComponent(clubsButton))
																																																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																																				.addComponent(diamondsButton)
																																																																				.addComponent(spadesButton))
																																																																				.addGap(18, 18, 18)
																																																																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																																																																						.addComponent(trumpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
																																																																						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
																																																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																						.addComponent(jLabelYourName)
																																																																						.addContainerGap())
		);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void card1Clicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_card1Clicked
		if(GM.isMyTurn() && GM.getPlayerIAm().getHand()[0].getSuit() != 'e'){
			if(pickItUp && GM.isDealer()){
				Card c = this.getTurnedCard();
				GM.getPlayerIAm().setCard(0, c.getCardValue(), c.getSuit());
				jButtonYourCard1.setIcon(picManager.getPicture(c.getSuit(),c.getCardValue()));
				TurnedCard.setVisible(false);
				jLabelDealer.setVisible(false);
				if(GM.isServer()){
					GM.getServerNetworkManager().toClients("SetTrump,"+c.getSuit());
					GM.setTrump(c.getSuit());
				}
				else{
					GM.getClientNetworkManager().toServer("SetTrump,"+c.getSuit());
				}
				pickItUp=false;
				gameplay=true;
				turnOver();
			}
			else if(gameplay){
				if(isValidMove(GM.getPlayerIAm().getHand()[0])){
					jButtonYourCard1.setIcon(picManager.getPicture('e','0'));
					if(GM.isServer()){	
						Card c = GM.getPlayerIAm().getHand()[0];
						playCard(c, GM.getPlayerIAm().getNumber());
						GM.getServerNetworkManager().toClients("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					else{
						Card c = GM.getPlayerIAm().getHand()[0];
						GM.getClientNetworkManager().toServer("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					GM.getPlayerIAm().setCard(0, '0', 'e');
					turnOver();
				}else{
					JOptionPane.showMessageDialog(null, "Please play on suit", "Invalid Play", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}//GEN-LAST:event_card1Clicked

	private void card2Clicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_card2Clicked
		if(GM.isMyTurn() && GM.getPlayerIAm().getHand()[1].getSuit() != 'e'){
			if(pickItUp && GM.isDealer()){
				Card c = this.getTurnedCard();
				GM.getPlayerIAm().setCard(1, c.getCardValue(), c.getSuit());
				jButtonYourCard2.setIcon(picManager.getPicture(c.getSuit(),c.getCardValue()));
				TurnedCard.setVisible(false);
				jLabelDealer.setVisible(false);
				if(GM.isServer()){
					GM.getServerNetworkManager().toClients("SetTrump,"+c.getSuit());
					GM.setTrump(c.getSuit());
				}
				else{
					GM.getClientNetworkManager().toServer("SetTrump,"+c.getSuit());
				}
				pickItUp=false;
				gameplay=true;
				turnOver();
			}
			else if(gameplay){
				if(isValidMove(GM.getPlayerIAm().getHand()[1])){
					jButtonYourCard2.setIcon(picManager.getPicture('e','0'));
					if(GM.isServer()){
						Card c = GM.getPlayerIAm().getHand()[1];
						playCard(c, GM.getPlayerIAm().getNumber());
						GM.getServerNetworkManager().toClients("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					else{
						Card c = GM.getPlayerIAm().getHand()[1];
						GM.getClientNetworkManager().toServer("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					GM.getPlayerIAm().setCard(1, '0', 'e');
					turnOver();
				}else{
					JOptionPane.showMessageDialog(null, "Please play on suit", "Invalid Play", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}//GEN-LAST:event_card2Clicked

	private void card3Clicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_card3Clicked
		if(GM.isMyTurn() && GM.getPlayerIAm().getHand()[2].getSuit() != 'e'){
			if(pickItUp && GM.isDealer()){
				Card c = this.getTurnedCard();
				GM.getPlayerIAm().setCard(2, c.getCardValue(), c.getSuit());
				jButtonYourCard3.setIcon(picManager.getPicture(c.getSuit(),c.getCardValue()));
				TurnedCard.setVisible(false);
				jLabelDealer.setVisible(false);
				if(GM.isServer()){
					GM.getServerNetworkManager().toClients("SetTrump,"+c.getSuit());
					GM.setTrump(c.getSuit());
				}
				else{
					GM.getClientNetworkManager().toServer("SetTrump,"+c.getSuit());
				}
				pickItUp=false;
				gameplay=true;
				turnOver();
			}
			else if(gameplay){
				if(isValidMove(GM.getPlayerIAm().getHand()[2])){
					jButtonYourCard3.setIcon(picManager.getPicture('e','0'));
					if(GM.isServer()){
						Card c = GM.getPlayerIAm().getHand()[2];
						playCard(c, GM.getPlayerIAm().getNumber());
						GM.getServerNetworkManager().toClients("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					else{
						Card c = GM.getPlayerIAm().getHand()[2];
						GM.getClientNetworkManager().toServer("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					GM.getPlayerIAm().setCard(2, '0', 'e');
					turnOver();
				}else{
					JOptionPane.showMessageDialog(null, "Please play on suit", "Invalid Play", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}//GEN-LAST:event_card3Clicked

	private void card4Clicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_card4Clicked
		if(GM.isMyTurn() && GM.getPlayerIAm().getHand()[3].getSuit() != 'e'){
			if(pickItUp && GM.isDealer()){
				Card c = this.getTurnedCard();
				GM.getPlayerIAm().setCard(3, c.getCardValue(), c.getSuit());
				jButtonYourCard4.setIcon(picManager.getPicture(c.getSuit(),c.getCardValue()));
				TurnedCard.setVisible(false);
				jLabelDealer.setVisible(false);
				if(GM.isServer()){
					GM.getServerNetworkManager().toClients("SetTrump,"+c.getSuit());
					GM.setTrump(c.getSuit());
				}
				else{
					GM.getClientNetworkManager().toServer("SetTrump,"+c.getSuit());
				}
				pickItUp=false;
				gameplay=true;
				turnOver();
			}
			else if(gameplay){
				if(isValidMove(GM.getPlayerIAm().getHand()[3])){
					jButtonYourCard4.setIcon(picManager.getPicture('e','0'));
					if(GM.isServer()){
						Card c = GM.getPlayerIAm().getHand()[3];
						playCard(c, GM.getPlayerIAm().getNumber());
						GM.getServerNetworkManager().toClients("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					else{
						Card c = GM.getPlayerIAm().getHand()[3];
						GM.getClientNetworkManager().toServer("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					GM.getPlayerIAm().setCard(3, '0', 'e');
					turnOver();
				}else{
					JOptionPane.showMessageDialog(null, "Please play on suit", "Invalid Play", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}//GEN-LAST:event_card4Clicked

	private void card5Clicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_card5Clicked
		if(GM.isMyTurn() && GM.getPlayerIAm().getHand()[4].getSuit() != 'e'){
			if(pickItUp && GM.isDealer()){
				Card c = this.getTurnedCard();
				GM.getPlayerIAm().setCard(4, c.getCardValue(), c.getSuit());
				jButtonYourCard5.setIcon(picManager.getPicture(c.getSuit(),c.getCardValue()));
				TurnedCard.setVisible(false);
				jLabelDealer.setVisible(false);
				if(GM.isServer()){
					GM.getServerNetworkManager().toClients("SetTrump,"+c.getSuit());
					GM.setTrump(c.getSuit());
				}
				else{
					GM.getClientNetworkManager().toServer("SetTrump,"+c.getSuit());
				}
				pickItUp=false;
				gameplay=true;
				turnOver();
			}
			else if(gameplay){
				if(isValidMove(GM.getPlayerIAm().getHand()[4])){
					jButtonYourCard5.setIcon(picManager.getPicture('e','0'));
					if(GM.isServer()){
						Card c = GM.getPlayerIAm().getHand()[4];
						playCard(c, GM.getPlayerIAm().getNumber());
						GM.getServerNetworkManager().toClients("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					else{
						Card c = GM.getPlayerIAm().getHand()[4];
						GM.getClientNetworkManager().toServer("PlayCard,"+c.getCardValue()+c.getSuit()+","+GM.getPlayerIAm().getNumber());
					}
					GM.getPlayerIAm().setCard(4, '0', 'e');
					turnOver();
				}else{
					JOptionPane.showMessageDialog(null, "Please play on suit", "Invalid Play", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}//GEN-LAST:event_card5Clicked

	/**
	 * checks if card is a valid play
	 * @param c card to check
	 * @return if move is valid
	 */
	private boolean isValidMove(Card c){
		Card hand[] = GM.getPlayerIAm().getHand();
		if(cardsPlayed == 0){
			return true;
		}
		if(isFollowingSuit(c,getTrump(),suitLed)){
			return true;
		}
		if(!isFollowingSuit(hand[0],getTrump(),suitLed) && !isFollowingSuit(hand[1],getTrump(),suitLed) && !isFollowingSuit(hand[2],getTrump(),suitLed) && !isFollowingSuit(hand[3],getTrump(),suitLed) && !isFollowingSuit(hand[4],getTrump(),suitLed)){
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the given card is following the lead suit.
	 * 
	 * @param c The given card.
	 * @param trump The trump suit.
	 * @param led The led suit.
	 * @return True if the given card is following the lead suit.
	 */
	private boolean isFollowingSuit (Card c, char trump, char led){
		if (led == trump){
			if (CardEvaluator.cardValue(trump, led, c) >= 7)
				return true;
			else
				return false;
		}else{
			if(CardEvaluator.cardValue(getTrump(), suitLed, c) > 0 && CardEvaluator.cardValue(getTrump(), suitLed, c) < 7)
				return true;
			else
				return false;
		}
	}

	private void passButtonClicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_passButtonClicked
		if(GM.isMyTurn() && !pickItUp){
			if(settingSuit==false){
				if(GM.isDealer()){
					GM.getServerNetworkManager().toClients("SettingSuit");
					settingSuit();
					jLabelDealer.setVisible(false);
					TurnedCard.setVisible(false);
				}
				turnOver();
			}
		}
	}//GEN-LAST:event_passButtonClicked

	private void pickItUpButtonClicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_pickItUpButtonClicked
		if(GM.isMyTurn() && !pickItUp){
			if(settingSuit == false){
				if(GM.isServer()){					
					GM.getServerNetworkManager().toClients("PickItUp");
					pickItUp();
				}
				else
					GM.getClientNetworkManager().toServer("PickItUp");
				setPlayerTurn(GM.getDealer().getPlayerID());
			}
			this.hideTrumpButtons();
		}
	}//GEN-LAST:event_pickItUpButtonClicked

	private void heartsListener(java.awt.event.MouseEvent evt){//GEN-FIRST:event_heartsListener
		if(GM.isMyTurn()){
			suitButtonsUsed = true;
			if(GM.isServer()){
				GM.getServerNetworkManager().toClients("SetTrump,h");
				GM.setTrump('h');
				trump = 'h';
			}
			else
				GM.getClientNetworkManager().toServer("SetTrump,h");
			hideSuitButtons();

		}
	}//GEN-LAST:event_heartsListener

	private void clubsListener(java.awt.event.MouseEvent evt){//GEN-FIRST:event_clubsListener
		if(GM.isMyTurn()){
			suitButtonsUsed = true;
			if(GM.isServer()){
				GM.getServerNetworkManager().toClients("SetTrump,c");
				GM.setTrump('c');
				trump = 'c';
			}
			else
				GM.getClientNetworkManager().toServer("SetTrump,c");
			hideSuitButtons();

		}
	}//GEN-LAST:event_clubsListener

	private void diamondsListener(java.awt.event.MouseEvent evt){//GEN-FIRST:event_diamondsListener
		if(GM.isMyTurn()){
			suitButtonsUsed = true;
			if(GM.isServer()){
				GM.getServerNetworkManager().toClients("SetTrump,d");
				GM.setTrump('d');
				trump = 'd';
			}
			else
				GM.getClientNetworkManager().toServer("SetTrump,d");
			hideSuitButtons();
		}
	}//GEN-LAST:event_diamondsListener

	private void spadesListener(java.awt.event.MouseEvent evt){//GEN-FIRST:event_spadesListener
		if(GM.isMyTurn()){
			suitButtonsUsed = true;
			if(GM.isServer()){
				GM.getServerNetworkManager().toClients("SetTrump,s");
				GM.setTrump('s');
				trump = 's';
			}
			else
				GM.getClientNetworkManager().toServer("SetTrump,s");
			hideSuitButtons();
		}
	}//GEN-LAST:event_spadesListener

	private void suitPassListener(java.awt.event.MouseEvent evt){//GEN-FIRST:event_suitPassListener
		if(GM.isMyTurn()){
			hideSuitButtons();
			turnOver();

			/*if(GM.getDealer() == humanPlayer){
				JOptionPane.showMessageDialog(null, "Please select a suit", "Stick the Dealer", JOptionPane.ERROR_MESSAGE);
				suitPassButton.setVisible(false);
			}
			else{
				humanPlayer.setCallSuit('x');
			} 	
			 */
			suitButtonsUsed = true;
		}
	}//GEN-LAST:event_suitPassListener

	/**
	 * This method is used to display the players hand to the GUI
	 * 
	 * @param card the card to be set to the specified button
	 * @param cardNumber the button to set to the specified card
	 */
	public void setCard(Card card, int cardNumber){
		handButtons[cardNumber].setIcon(picManager.getPicture(card.getSuit(), card.getCardValue()));
	}  

	/**
	 * hides the buttons used during trump selection
	 */
	private void hideTrumpButtons(){
		jButtonPass.setVisible(false);
		jButtonPickUp.setVisible(false);
		TurnedCard.setIcon(picManager.getPicture('e', '0'));
		jLabelTurn.setVisible(false);
		jLabelDealer.setVisible(false);
	}

	/**
	 * displays the buttons used during trump selection
	 */
	private void showTrumpButtons(){
		jButtonPass.setVisible(true);
		jButtonPickUp.setVisible(true);
		jLabelTurn.setVisible(true);
		jLabelDealer.setVisible(true);
	}

	/**
	 * hides the buttons used during suit selection
	 */
	private void hideSuitButtons(){
		heartsButton.setVisible(false);
		clubsButton.setVisible(false);
		diamondsButton.setVisible(false);
		spadesButton.setVisible(false);
		suitPassButton.setVisible(false);
	}

	/**
	 * displays the buttons used during suit selection
	 */
	private void showSuitButtons(){
		//System.out.println("showsuitbuttons");
		heartsButton.setVisible(true);
		clubsButton.setVisible(true);
		diamondsButton.setVisible(true);
		spadesButton.setVisible(true);
		suitPassButton.setVisible(true);
	}

	/**
	 * Sets the string above the deal to represent that the deal belongs to the given players name.
	 * 
	 * @param name The name of who the deal belongs to.
	 */
	public void setDealerName(String name){
		String possesive = name;
		if (name.compareTo(this.jLabelYourName.getText()) == 0){
			possesive = "Your";
		}else if(name.charAt(name.length()-1) == 's'){
			possesive += "'";
		}else{
			possesive += "'s";
		}
		jLabelDealer.setText(possesive+"  Deal:");
	}

	public void setWeTricks(int tricks){
		weTeamTricksLabel.setText("" + tricks);
	}

	public void setTheyTricks(int tricks){
		theyTeamTricksLabel.setText("" + tricks);
	}

	public void setWePoints(int points){
		weTeamPointsLabel.setText("" + points);
	}

	public void setTheyPoints(int points){
		theyTeamPointsLabel.setText("" + points);
	}

	/**
	 * displays an indicated card played by specified player at the center of the board
	 * 
	 * @param c card to be played
	 * @param playerNumber number of the player playing the card
	 */
	public void playCard(Card c, int playerNumber){

		played[cardsPlayed] = c;
		playedHand.setCardsPlayed(played);
		
		if(playerNumber != humanPlayer.getNumber())
			hideOpponentCard(playerNumber);
		
		if(rightPlayer.getNumber() == playerNumber){
			RPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
		}
		else if(leftPlayer.getNumber() == playerNumber){
			LPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
		}
		else if(topPlayer.getNumber() == playerNumber){
			UPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
		}
		else if(humanPlayer.getNumber() == playerNumber){
			YourPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
		}
		if(cardsPlayed == 0){
			int tmpCardVal = CardEvaluator.cardValue(getTrump(), c.getSuit(), c);
			if (tmpCardVal >=7)
				suitLed = getTrump();
			else
				suitLed = c.getSuit();
			playedHand.setSuitLed(c.getSuit());
			
			if(playerNumber==1) playerWhoLed=GM.getPlayer1();
			else if(playerNumber==2) playerWhoLed=GM.getPlayer2();
			else if(playerNumber==3) playerWhoLed=GM.getPlayer3();
			else if(playerNumber==4) playerWhoLed=GM.getPlayer4();
			
		}
		cardsPlayed++;
		
		if (cardsPlayed == 4){
			
			hand++;
			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			if(tabulator.interpretHand(trump, playedHand,GM.getTeamOne(),GM.getTeamTwo()) == GM.getTeamOne()) {
				System.out.println("Team One Wins the trick!");
				oneTricks++;
				this.setWeTricks(oneTricks);
				this.setTheyTricks(twoTricks);
			}
			else if(tabulator.interpretHand(trump, playedHand,GM.getTeamOne(),GM.getTeamTwo()) == GM.getTeamTwo()) {
				System.out.println("Team Two wins the trick!");
				twoTricks++;
				this.setWeTricks(oneTricks);
				this.setTheyTricks(twoTricks);
			}
			
			if(hand<=5){
			
				if(CardEvaluator.highestPlayed(played, trump, played[0].getSuit()).equals(played[0])) {
					this.setPlayerTurn(playerWhoLed.getPlayerID());
				}
				else if(CardEvaluator.highestPlayed(played, trump, played[0].getSuit()).equals(played[1])) {
					this.setPlayerTurn(GM.nextPlayer(playerWhoLed).getPlayerID());
				}
				else if(CardEvaluator.highestPlayed(played, trump, played[0].getSuit()).equals(played[2])) {
					this.setPlayerTurn(GM.nextPlayer(GM.nextPlayer(playerWhoLed)).getPlayerID());
				}
				else if(CardEvaluator.highestPlayed(played, trump, played[0].getSuit()).equals(played[3])) {
					this.setPlayerTurn(GM.nextPlayer(GM.nextPlayer(GM.nextPlayer(playerWhoLed))).getPlayerID());
				}
			
			}
			else{
				
				GM.interpretRound(oneTricks, twoTricks);				
				GM.setDealer(GM.nextPlayer(GM.getDealer()));
				GM.playRound();
				
			}
			
			
			
			RPlayed.setIcon(picManager.getPicture('e','0'));
			LPlayed.setIcon(picManager.getPicture('e','0'));
			UPlayed.setIcon(picManager.getPicture('e','0'));
			YourPlayed.setIcon(picManager.getPicture('e','0'));
			cardsPlayed = 0;
			
			
		}

	}

	public void hideOpponentCard(int playerNumber){
		if(playerNumber == leftPlayer.getNumber())
		{	
			switch(leftPlayed){
			case 0:
				LCard1.setIcon(picManager.getPicture('e','s'));
				break;
			case 1:
				LCard2.setIcon(picManager.getPicture('e','s'));

				break;
			case 2:
				LCard3.setIcon(picManager.getPicture('e','s'));
				break;
			case 3:
				LCard4.setIcon(picManager.getPicture('e','s'));

				break;
			case 4:
				LCard5.setIcon(picManager.getPicture('e','s'));
				break;
			}
			leftPlayed++;	
		}
		if(playerNumber == rightPlayer.getNumber())
		{	
			switch(rightPlayed){
			case 0:
				RCard1.setIcon(picManager.getPicture('e','s'));
				break;
			case 1:
				RCard2.setIcon(picManager.getPicture('e','s'));
				break;
			case 2:
				RCard3.setIcon(picManager.getPicture('e','s'));

				break;
			case 3:
				RCard4.setIcon(picManager.getPicture('e','s'));

				break;
			case 4:
				RCard5.setIcon(picManager.getPicture('e','s'));
				break;
			}
			rightPlayed++;		
		}
		if(playerNumber == topPlayer.getNumber())
		{
			switch(upperPlayed){
			case 0:
				UCard1.setIcon(picManager.getPicture('e','0'));
				break;
			case 1:
				UCard2.setIcon(picManager.getPicture('e','0'));
				break;
			case 2:
				UCard3.setIcon(picManager.getPicture('e','0'));
				break;
			case 3:
				UCard4.setIcon(picManager.getPicture('e','0'));
				break;
			case 4:
				UCard5.setIcon(picManager.getPicture('e','0'));
				break;
			}
			upperPlayed++;	
		}
	}

	public void setTurnedCard(Card c){
		TurnedCard.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
		turnedCard = c;
	}

	public void setGameManager(GameManager gm){
		GM = gm;
		//round = GM.getRound();
	}

	private void setTopPlayer(Player player){
		jLabelUPlayerName.setText(player.getName());
	}

	private void setLeftPlayer(Player player){
		jLabelLPlayerName.setText(player.getName());
	}

	private void setRightPlayer(Player player){
		jLabelRPlayerName.setText(player.getName());
	}

	public void setWeTeam(Team team){
		weTeamNumberLabel.setText("" + team.getTeamNumber());
	}

	public void setTheyTeam(Team team){
		theyTeamNumberLabel.setText("" + team.getTeamNumber());
	}

	public Card getTurnedCard(){
		return turnedCard;
	}

	/**
	 * Gets the suit that is shown as being trump by the Trump Label.
	 * 'c' - clubs
	 * 'd' - diamonds
	 * 's' - spades
	 * 'h' - hearts
	 * 'e' - empty-set label.
	 * 
	 * @return The suit that is shown as trump.
	 */
	private char getTrump(){
		switch (trumpLabel.getText().charAt(0)){
		case '\u2663':
			return 'c';
		case '\u2666':
			return 'd';
		case '\u2660':
			return 's';
		case '\u2665':
			return 'h';
		default:
			return 'e';
		}
	}

	/**
	 * Sets the trump label on the game board to the given suit.
	 * 
	 * @param suit The suit to show as trump.
	 */
	public void setTrumpLabel(char suit){
		switch(suit){
		case 'c':{
			trumpLabel.setText("\u2663");
			trumpLabel.setForeground(new java.awt.Color(0, 0, 0));
			break;
		}
		case 'd':{
			trumpLabel.setText("\u2666");
			trumpLabel.setForeground(new java.awt.Color(255, 0, 0));
			break;
		}
		case 's':{
			trumpLabel.setText("\u2660");
			trumpLabel.setForeground(new java.awt.Color(0, 0, 0));
			break;
		}
		case 'h':{
			trumpLabel.setText("\u2665");
			trumpLabel.setForeground(new java.awt.Color(255, 0, 0));
			break;
		}
		case 'e':{
			trumpLabel.setText("\u2205");
			trumpLabel.setForeground(new java.awt.Color(0, 0, 0));
			break;
		}
		default:{
			trumpLabel.setText("\u2205");
			trumpLabel.setForeground(new java.awt.Color(0, 0, 0));
		}
		}
		gameplay = true;
	}

	public void turnOver(){
		if(GM.getServerNetworkManager() != null){
			GM.getServerNetworkManager().toClients("SetNextPlayerTurn");
			GM.setNextPlayerTurn();
		}
		else{
			GM.getClientNetworkManager().toServer("SetNextPlayerTurn");
		} 
	}

	public void setPlayerTurn(int id){
		if(GM.isServer()){

			GM.setTurnPlayerID(id);
			GM.getServerNetworkManager().toClients("SetPlayerTurn,"+id);
		}
		else{
			GM.getClientNetworkManager().toServer("SetPlayerTurn,"+id);

		} 
	}

	public void settingSuit(){
		this.hideTrumpButtons();
		this.showSuitButtons();
		jLabelDealer.setVisible(false);
		TurnedCard.setVisible(false);
		settingSuit = true;
	}

	public void trumpSet(){
		if(settingSuit){
			hideSuitButtons();
			settingSuit = false;
			gameplay = true;
			setPlayerTurn(GM.nextPlayer(GM.getDealer()).getPlayerID());
		}
	}

	public void pickItUp(){

		if(GM.getPlayerIAm().getTeam()==1) 
			teamWhoOrdered=GM.getTeamOne();
		else 
			teamWhoOrdered=GM.getTeamTwo();

		trump = turnedCard.getSuit();

		if(!GM.isDealer()){
			TurnedCard.setVisible(false);
			jLabelDealer.setVisible(false);
		}
		jButtonPass.setVisible(false);
		jButtonPickUp.setVisible(false);
		pickItUp = true;
	}

	public GameLogic getTabulator(){
		return tabulator;
	}

	public void setRound(Round round){
		//this.round = round;
	}

	public int getTeamOneTricks(){
		return oneTricks;
	}

	public int getTeamTwoTricks(){
		return twoTricks;
	}

	public Team getTeamWhoOrdered(){
		return teamWhoOrdered;
	}

}
