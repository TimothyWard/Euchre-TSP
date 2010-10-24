package euchre.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import euchre.game.Team;
import euchre.gui.pictures.PictureManager;
import euchre.player.Card;
import euchre.player.GameManager;
import euchre.player.Human;
import euchre.player.Player;

/**
 * The GUI that displays the euchre game to the user and allows them to play it.
 * 
 * @author Neil MacBay (nmmacbay)
 * @author sdwilke
 */
public class GameBoard extends javax.swing.JFrame {
	private Card[] hand = new Card[5];
	private Human humanPlayer;
	private PictureManager picManager = new PictureManager();
	private static final long serialVersionUID = 1L;
	private GameManager GM;
	private Player topPlayer;
	private Player leftPlayer;
	private Player rightPlayer;
	/** 
	 * Creates new form GameBoard
	 * @param player the human player object being controlled from the game board
	 */
    public GameBoard(Player player){
    	humanPlayer = (Human) player;
        initComponents();
        centerScreen();
        setBottomPlayer(humanPlayer);
        this.setBackground(Color.GREEN);
        heartsButton.setVisible(false);
        clubsButton.setVisible(false);
        diamondsButton.setVisible(false);
        spadesButton.setVisible(false);
        suitPassButton.setVisible(false);
    }
    
    
    /**
     * resets the board to start a new round
     */
    public void newRound(){
    	jButtonYourPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
        jButtonRPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
        jButtonLPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
        jButtonUPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png")));
        jButtonUCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
        jButtonUCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
        jButtonUCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
        jButtonUCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
        jButtonUCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png")));
        jButtonLCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonLCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonLCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonLCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonLCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonRCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonRCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonRCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonRCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonRCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png")));
        jButtonPass.setVisible(true);
		jButtonPickUp.setVisible(true);
		jButtonTurnedCard.setVisible(true);
		jLabelDealer.setVisible(true);
		jButtonYourCard1.setIcon(picManager.getPicture(humanPlayer.getHand()[0].getSuit(), humanPlayer.getHand()[0].getCardValue()));
        jButtonYourCard2.setIcon(picManager.getPicture(humanPlayer.getHand()[1].getSuit(), humanPlayer.getHand()[1].getCardValue()));
        jButtonYourCard3.setIcon(picManager.getPicture(humanPlayer.getHand()[2].getSuit(), humanPlayer.getHand()[2].getCardValue()));
        jButtonYourCard4.setIcon(picManager.getPicture(humanPlayer.getHand()[3].getSuit(), humanPlayer.getHand()[3].getCardValue()));
        jButtonYourCard5.setIcon(picManager.getPicture(humanPlayer.getHand()[4].getSuit(), humanPlayer.getHand()[4].getCardValue()));
    }
    
    /**
     * sets the game manager so gameboard can have access to the players
     * 
     * @param gm the game manager
     */
    public void setGameManager(GameManager gm){
    	GM = gm;
    	if(humanPlayer.getTeam() == 1){
    		if(gm.getTeamOne().getPlayerOne().getNumber() != humanPlayer.getNumber()){
    			topPlayer = gm.getTeamOne().getPlayerOne();
    		}else{
    			topPlayer = gm.getTeamOne().getPlayerTwo();
    		}
    		leftPlayer = gm.getTeamTwo().getPlayerOne();
    		rightPlayer = gm.getTeamTwo().getPlayerTwo();
    		setWeTeam(gm.getTeamOne());
    		setTheyTeam(gm.getTeamTwo());
    	}else{
    		if(gm.getTeamTwo().getPlayerOne().getNumber() != humanPlayer.getNumber()){
    			topPlayer = gm.getTeamTwo().getPlayerOne();
    		}else{
    			topPlayer = gm.getTeamTwo().getPlayerTwo();
    		}
    		leftPlayer = gm.getTeamOne().getPlayerOne();
    		rightPlayer = gm.getTeamOne().getPlayerTwo();
    		setWeTeam(gm.getTeamTwo());
    		setTheyTeam(gm.getTeamOne());
    	}
    	setTopPlayer(topPlayer);
    	setLeftPlayer(leftPlayer);
    	setRightPlayer(rightPlayer);
    }
    
    private void setTopPlayer(Player player){
    	jLabelUPlayerName.setText(player.getName());
    }
    
	private void setBottomPlayer(Player player){
		jLabelYourName.setText(player.getName());
		setCard(player.getHand()[0], 0);
		setCard(player.getHand()[1], 1);
		setCard(player.getHand()[2], 2);
		setCard(player.getHand()[3], 3);
		setCard(player.getHand()[4], 4);
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
	
    /**
     * Moves the jFrame to the center of the screen
     */
    private void centerScreen(){
        int xCenter = Toolkit.getDefaultToolkit().getScreenSize().width/2;
        int yCenter = Toolkit.getDefaultToolkit().getScreenSize().height/2;
        int xSize = this.getSize().width;
        int ySize = this.getSize().height;
        Point p = new Point();
        p.setLocation(xCenter - xSize/2, yCenter - ySize/2);
        this.setLocation(p);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonYourCard1 = new javax.swing.JButton();
        jButtonYourCard2 = new javax.swing.JButton();
        jButtonYourCard4 = new javax.swing.JButton();
        jButtonYourCard5 = new javax.swing.JButton();
        jButtonYourCard3 = new javax.swing.JButton();
        jButtonYourPlayed = new javax.swing.JButton();
        jButtonRPlayed = new javax.swing.JButton();
        jButtonLPlayed = new javax.swing.JButton();
        jButtonUPlayed = new javax.swing.JButton();
        jButtonUCard2 = new javax.swing.JButton();
        jButtonUCard3 = new javax.swing.JButton();
        jButtonUCard1 = new javax.swing.JButton();
        jButtonUCard4 = new javax.swing.JButton();
        jButtonUCard5 = new javax.swing.JButton();
        jButtonLCard3 = new javax.swing.JButton();
        jButtonLCard4 = new javax.swing.JButton();
        jButtonLCard5 = new javax.swing.JButton();
        jButtonLCard2 = new javax.swing.JButton();
        jButtonLCard1 = new javax.swing.JButton();
        jButtonRCard5 = new javax.swing.JButton();
        jButtonRCard4 = new javax.swing.JButton();
        jButtonRCard3 = new javax.swing.JButton();
        jButtonRCard2 = new javax.swing.JButton();
        jButtonRCard1 = new javax.swing.JButton();
        jLabelUPlayerName = new javax.swing.JLabel();
        jLabelLPlayerName = new javax.swing.JLabel();
        jLabelRPlayerName = new javax.swing.JLabel();
        jLabelYTeamPoints = new javax.swing.JLabel();
        weTricksLabel = new javax.swing.JLabel();
        jLabelYTeamTricks = new javax.swing.JLabel();
        wePointsLabel = new javax.swing.JLabel();
        theyPointsLabel = new javax.swing.JLabel();
        jLabelOTeamPoints = new javax.swing.JLabel();
        theyTricksLabel = new javax.swing.JLabel();
        jLabelOTeamTricks = new javax.swing.JLabel();
        jButtonTurnedCard = new javax.swing.JButton();
        jButtonPass = new javax.swing.JButton();
        jButtonPickUp = new javax.swing.JButton();
        jLabelYourName = new javax.swing.JLabel();
        jLabelDealer = new javax.swing.JLabel();
        jLabelPassInfo = new javax.swing.JLabel();
        theyLabel = new javax.swing.JLabel();
        weLabel = new javax.swing.JLabel();
        theyTeamNumberLabel = new javax.swing.JLabel();
        weTeamNumberLabel = new javax.swing.JLabel();
        heartsButton = new javax.swing.JButton();
        clubsButton = new javax.swing.JButton();
        diamondsButton = new javax.swing.JButton();
        spadesButton = new javax.swing.JButton();
        suitPassButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Euchre Game Board");
        setBackground(java.awt.Color.lightGray);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.lightGray);
        setResizable(false);

        jButtonYourCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N
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

        jButtonYourPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

        jButtonRPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

        jButtonLPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

        jButtonUPlayed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

        jButtonUCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

        jButtonUCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

        jButtonUCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

        jButtonUCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

        jButtonUCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"))); // NOI18N

        jButtonLCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonLCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonLCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonLCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonLCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonRCard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonRCard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonRCard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonRCard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jButtonRCard1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"))); // NOI18N

        jLabelUPlayerName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelUPlayerName.setText("Player name");

        jLabelLPlayerName.setText("Player name");

        jLabelRPlayerName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelRPlayerName.setText("Player name");

        jLabelYTeamPoints.setText("0");

        weTricksLabel.setText("Tricks:");

        jLabelYTeamTricks.setText("0");

        wePointsLabel.setText("Points:");

        theyPointsLabel.setText("Points:");

        jLabelOTeamPoints.setText("0");

        theyTricksLabel.setText("Tricks:");

        jLabelOTeamTricks.setText("0");

        jButtonTurnedCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"))); // NOI18N

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

        jLabelPassInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPassInfo.setText("(Player) passed, Your call");

        theyLabel.setText("Team:");

        weLabel.setText("Team:");

        theyTeamNumberLabel.setText("1");

        weTeamNumberLabel.setText("2");

        heartsButton.setText("Hearts");
        heartsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                heartsListener(evt);
            }
        });

        clubsButton.setText("Clubs");
        clubsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clubsListener(evt);
            }
        });

        diamondsButton.setText("Diamonds");
        diamondsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                diamondsListener(evt);
            }
        });

        spadesButton.setText("Spades");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelLPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 508, Short.MAX_VALUE)
                        .addComponent(jLabelRPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonLCard3)
                                    .addComponent(jButtonLCard2)
                                    .addComponent(jButtonLCard1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(theyTricksLabel)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(theyLabel)
                                                .addComponent(theyPointsLabel)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelOTeamTricks)
                                            .addComponent(jLabelOTeamPoints)
                                            .addComponent(theyTeamNumberLabel))))
                                .addGap(138, 138, 138)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(99, 99, 99)
                                                .addComponent(jButtonLPlayed)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jButtonYourPlayed)
                                                    .addComponent(jButtonUPlayed)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(heartsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(diamondsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(clubsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(spadesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                                .addGap(134, 134, 134)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButtonRPlayed)
                                                .addGap(93, 93, 93))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabelDealer)
                                                    .addComponent(jButtonTurnedCard))
                                                .addGap(46, 46, 46))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButtonUCard1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonUCard2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonUCard3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonUCard4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonUCard5))
                                    .addComponent(jLabelUPlayerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButtonLCard4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLCard5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabelYourName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButtonYourCard1)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(9, 9, 9)
                                            .addComponent(suitPassButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButtonPass, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButtonPickUp, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButtonYourCard2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButtonYourCard3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButtonYourCard4))
                                        .addComponent(jLabelPassInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonYourCard5))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButtonRCard3)
                                        .addComponent(jButtonRCard1)
                                        .addComponent(jButtonRCard4)
                                        .addComponent(jButtonRCard5))
                                    .addComponent(jButtonRCard2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(weTricksLabel)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(weLabel)
                                        .addComponent(wePointsLabel)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelYTeamTricks)
                                    .addComponent(jLabelYTeamPoints)
                                    .addComponent(weTeamNumberLabel))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jButtonRCard1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonRCard2)
                        .addGap(12, 12, 12)
                        .addComponent(jButtonRCard3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonRCard4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonRCard5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelRPlayerName)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(weLabel)
                            .addComponent(weTeamNumberLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelYTeamPoints)
                            .addComponent(wePointsLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(weTricksLabel)
                            .addComponent(jLabelYTeamTricks)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(theyLabel)
                                    .addComponent(theyTeamNumberLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(theyPointsLabel)
                                    .addComponent(jLabelOTeamPoints))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(theyTricksLabel)
                                    .addComponent(jLabelOTeamTricks))
                                .addGap(45, 45, 45)
                                .addComponent(jButtonLCard1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonUCard5)
                                    .addComponent(jButtonUCard3)
                                    .addComponent(jButtonUCard2)
                                    .addComponent(jButtonUCard1)
                                    .addComponent(jButtonUCard4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelUPlayerName)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonLCard2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonLCard3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonLCard4))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jButtonUPlayed)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButtonYourPlayed))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(72, 72, 72)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButtonRPlayed)
                                        .addComponent(jButtonLPlayed))
                                    .addGap(24, 24, 24)
                                    .addComponent(jLabelDealer)
                                    .addGap(18, 18, 18))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonLCard5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelLPlayerName))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(heartsButton)
                                    .addComponent(clubsButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(diamondsButton)
                                    .addComponent(spadesButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelPassInfo)
                                    .addComponent(suitPassButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonPass)
                                    .addComponent(jButtonPickUp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonYourCard5)
                                    .addComponent(jButtonYourCard4)
                                    .addComponent(jButtonYourCard3)
                                    .addComponent(jButtonYourCard2)
                                    .addComponent(jButtonYourCard1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelYourName)))))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(471, Short.MAX_VALUE)
                .addComponent(jButtonTurnedCard)
                .addGap(215, 215, 215))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setBottomPlayedCard(Card c){
    	jButtonYourPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
    }
    
    public void setUpperPlayedCard(Card c){
    	jButtonUPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
    }

    public void setLeftPlayedCard(Card c){
    	jButtonLPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
    }

    public void setRightPlayedCard(Card c){
    	jButtonRPlayed.setIcon(picManager.getPicture(c.getSuit(), c.getCardValue()));
    }
    
    public void setTurnedCard(Card c){
    	jButtonTurnedCard.setIcon(picManager.getPicture(c.getSuit(), c.getSuit()));
    }
    
    
    private void card1Clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card1Clicked
    	if(humanPlayer.isTurn()){
    		humanPlayer.setActiveCard(hand[0]);
    		jButtonYourCard1.setIcon(picManager.getPicture('e', 'a'));
    	}
    	
    }//GEN-LAST:event_card1Clicked

    private void card2Clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card2Clicked
    	if(humanPlayer.isTurn()){
    		humanPlayer.setActiveCard(hand[1]);
    		jButtonYourCard2.setIcon(picManager.getPicture('e', 'a'));
    	}
    	
    }//GEN-LAST:event_card2Clicked

    private void card3Clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card3Clicked
    	if(humanPlayer.isTurn()){
    		humanPlayer.setActiveCard(hand[2]);
    		jButtonYourCard3.setIcon(picManager.getPicture('e', 'a'));
    	}
    	
    }//GEN-LAST:event_card3Clicked

    private void card4Clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card4Clicked
    	if(humanPlayer.isTurn()){
    		humanPlayer.setActiveCard(hand[3]);
    		jButtonYourCard4.setIcon(picManager.getPicture('e', 'a'));
    	}

    }//GEN-LAST:event_card4Clicked
    
    private void card5Clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card5Clicked
    	if(humanPlayer.isTurn()){
    		humanPlayer.setActiveCard(hand[4]);
    		jButtonYourCard5.setIcon(picManager.getPicture('e', 'a'));
    	}

    }//GEN-LAST:event_card5Clicked

    private void passButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passButtonClicked
    	if(humanPlayer.isTurn()){
    		humanPlayer.setOrderUp(false);
    		
    	}
    }//GEN-LAST:event_passButtonClicked

    private void pickItUpButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pickItUpButtonClicked
    	if(humanPlayer.isTurn()){
    		jButtonPass.setVisible(false);
    		jButtonPickUp.setVisible(false);
    		jButtonTurnedCard.setVisible(false);
    		jLabelDealer.setVisible(false);
    		heartsButton.setVisible(true);
            clubsButton.setVisible(true);
            diamondsButton.setVisible(true);
            spadesButton.setVisible(true);
            suitPassButton.setVisible(true);
    		humanPlayer.setOrderUp(true);
    	}

    }//GEN-LAST:event_pickItUpButtonClicked

    private void heartsListener(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_heartsListener
    	if(humanPlayer.isTurn()){
    		 humanPlayer.setCallSuit('h');
    	}
    }//GEN-LAST:event_heartsListener

    private void clubsListener(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clubsListener
    	if(humanPlayer.isTurn()){
    		humanPlayer.setCallSuit('c');
    	}
    }//GEN-LAST:event_clubsListener

    private void diamondsListener(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diamondsListener
    	if(humanPlayer.isTurn()){
    		humanPlayer.setCallSuit('d');
    	}
    }//GEN-LAST:event_diamondsListener

    private void spadesListener(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spadesListener
    	if(humanPlayer.isTurn()){
    		humanPlayer.setCallSuit('s');
    	}
    }//GEN-LAST:event_spadesListener

    private void suitPassListener(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suitPassListener
    	if(humanPlayer.isTurn()){
    		humanPlayer.setCallSuit('x');
    	}
    }//GEN-LAST:event_suitPassListener
    
    /**
     * hides the buttons used during trump selection
     */
    public void clearTrumpButtons(){
    	jButtonPass.setVisible(false);
    	jButtonPickUp.setVisible(false);
    	jButtonTurnedCard.setVisible(false);
    	jLabelDealer.setVisible(false);
    }
    
    public void setWeTricks(int tricks){
    	jLabelYTeamTricks.setText("" + tricks);
    }
    
    public void setTheyTricks(int tricks){
    	jLabelOTeamTricks.setText("" + tricks);
    }
    
    public void setWePoints(int points){
    	jLabelYTeamPoints.setText("" + points);
    }
    
    public void setTheyPoints(int points){
    	jLabelOTeamPoints.setText("" + points);
    }
    
    /**
     * used to display the players hand to the GUI
     * 
     * @param card the card to be set to the specified button
     * @param cardNumber the button to set to the specified card
     */
    public void setCard(Card card, int cardNumber){
    	hand[cardNumber] = card;
    	handButtons[cardNumber].setIcon(picManager.getPicture(card.getSuit(), card.getCardValue()));
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clubsButton;
    private javax.swing.JButton diamondsButton;
    private javax.swing.JButton heartsButton;
    private javax.swing.JButton jButtonLCard1;
    private javax.swing.JButton jButtonLCard2;
    private javax.swing.JButton jButtonLCard3;
    private javax.swing.JButton jButtonLCard4;
    private javax.swing.JButton jButtonLCard5;
    private javax.swing.JButton jButtonLPlayed;
    private javax.swing.JButton jButtonPass;
    private javax.swing.JButton jButtonPickUp;
    private javax.swing.JButton jButtonRCard1;
    private javax.swing.JButton jButtonRCard2;
    private javax.swing.JButton jButtonRCard3;
    private javax.swing.JButton jButtonRCard4;
    private javax.swing.JButton jButtonRCard5;
    private javax.swing.JButton jButtonRPlayed;
    private javax.swing.JButton jButtonTurnedCard;
    private javax.swing.JButton jButtonUCard1;
    private javax.swing.JButton jButtonUCard2;
    private javax.swing.JButton jButtonUCard3;
    private javax.swing.JButton jButtonUCard4;
    private javax.swing.JButton jButtonUCard5;
    private javax.swing.JButton jButtonUPlayed;
    private javax.swing.JButton jButtonYourCard1;
    private javax.swing.JButton jButtonYourCard2;
    private javax.swing.JButton jButtonYourCard3;
    private javax.swing.JButton jButtonYourCard4;
    private javax.swing.JButton jButtonYourCard5;
    private javax.swing.JButton jButtonYourPlayed;
    private javax.swing.JLabel jLabelDealer;
    private javax.swing.JLabel jLabelLPlayerName;
    private javax.swing.JLabel jLabelOTeamPoints;
    private javax.swing.JLabel jLabelOTeamTricks;
    private javax.swing.JLabel jLabelPassInfo;
    private javax.swing.JLabel jLabelRPlayerName;
    private javax.swing.JLabel jLabelUPlayerName;
    private javax.swing.JLabel jLabelYTeamPoints;
    private javax.swing.JLabel jLabelYTeamTricks;
    private javax.swing.JLabel jLabelYourName;
    private javax.swing.JButton spadesButton;
    private javax.swing.JButton suitPassButton;
    private javax.swing.JLabel theyLabel;
    private javax.swing.JLabel theyPointsLabel;
    private javax.swing.JLabel theyTeamNumberLabel;
    private javax.swing.JLabel theyTricksLabel;
    private javax.swing.JLabel weLabel;
    private javax.swing.JLabel wePointsLabel;
    private javax.swing.JLabel weTeamNumberLabel;
    private javax.swing.JLabel weTricksLabel;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JButton[] handButtons = {jButtonYourCard1, jButtonYourCard2, jButtonYourCard3, jButtonYourCard4, jButtonYourCard5};

}
