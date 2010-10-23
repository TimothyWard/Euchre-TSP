package euchre.gui;

import java.awt.*;

import javax.swing.JOptionPane;

import euchre.player.GameManager;

/**
 *
 * @author sdwilke
 * @author Neil MacBay(nmmacbay)
 */
public class HostGameSetup extends javax.swing.JFrame {

	int AIs = -1;
	GameManager myManager;
	
	/** Creates new form HostGameSetup */
	public HostGameSetup(GameManager inManager) {
		initComponents();
		centerScreen();
		myManager = inManager;
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        humanPlayerCount = new javax.swing.JComboBox();
        openLobbyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Host Game Setup");

        jLabel1.setText("Name:");

        jLabel2.setText("Number of Human Players");

        humanPlayerCount.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3" }));
        humanPlayerCount.setSelectedIndex(2);

        openLobbyButton.setText("Open Lobby");
        openLobbyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openLobby(evt);
            }
        });
        openLobbyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openLobbyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(humanPlayerCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(openLobbyButton)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(humanPlayerCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(openLobbyButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void openLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openLobbyButtonActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_openLobbyButtonActionPerformed

	private void openLobby(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openLobby
		if (nameInput.getText().isEmpty() || nameInput.getText().trim().isEmpty()){ //Invalid input (whitespace only)
			JOptionPane.showMessageDialog(null, "Name cannot be nothing or constist of entierly whitespace.");
		}else{ //Valid input
			new GameLobby(humanPlayerCount.getSelectedIndex()+1, nameInput.getText().trim(), myManager).setVisible(true);
			AIs = 3-(humanPlayerCount.getSelectedIndex()+1);
			System.out.println(AIs);
			this.setVisible(false);
		}
	}//GEN-LAST:event_openLobby

	public int getAIs(){
		return AIs;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox humanPlayerCount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nameInput;
    private javax.swing.JButton openLobbyButton;
    // End of variables declaration//GEN-END:variables

}
