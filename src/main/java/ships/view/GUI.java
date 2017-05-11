/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import ships.exception.OutsideOfMapPlacementException;

/**
 *
 * @author r4pt0r
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author r4pt0r
 */
public class GUI extends javax.swing.JFrame {

    private BattleshipsMap playerMap,opponentMap;

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();

        //create BattleshipsMap instance filling whole panel
        playerMap = new BattleshipsMap();
        playerMap.setBounds(0, 0, thisPlayerMap.getWidth(), thisPlayerMap.getHeight());
        thisPlayerMap.add(playerMap, BorderLayout.WEST);
        opponentMap = new BattleshipsMap();
        opponentMap.setBounds(0, 0, anotherPlayerMap.getWidth(), anotherPlayerMap.getHeight());
        anotherPlayerMap.add(opponentMap, BorderLayout.WEST);
        try {
            playerMap.fillField(1, 2, Color.yellow);
        } catch (OutsideOfMapPlacementException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        playerMap.addFieldClickObserver(new MapClickObserver());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        thisPlayerMap = new java.awt.Panel();
        anotherPlayerMap = new java.awt.Panel();
        fourPicker = new javax.swing.JButton();
        threePicker = new javax.swing.JButton();
        twoPicker = new javax.swing.JButton();
        onePicker = new javax.swing.JButton();
        playerScore = new javax.swing.JLabel();
        playerScore1 = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout thisPlayerMapLayout = new javax.swing.GroupLayout(thisPlayerMap);
        thisPlayerMap.setLayout(thisPlayerMapLayout);
        thisPlayerMapLayout.setHorizontalGroup(
                thisPlayerMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 314, Short.MAX_VALUE)
        );
        thisPlayerMapLayout.setVerticalGroup(
                thisPlayerMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 280, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout anotherPlayerMapLayout = new javax.swing.GroupLayout(anotherPlayerMap);
        anotherPlayerMap.setLayout(anotherPlayerMapLayout);
        anotherPlayerMapLayout.setHorizontalGroup(
                anotherPlayerMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 280, Short.MAX_VALUE)
        );
        anotherPlayerMapLayout.setVerticalGroup(
                anotherPlayerMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 280, Short.MAX_VALUE)
        );

        fourPicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/four.png"))); // NOI18N
        fourPicker.setText("1");
        fourPicker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        threePicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/three.png"))); // NOI18N
        threePicker.setText("2");
        threePicker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        twoPicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/two.png"))); // NOI18N
        twoPicker.setText("3");
        twoPicker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        onePicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/one.png"))); // NOI18N
        onePicker.setText("4");
        onePicker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        playerScore.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        playerScore.setText("0");

        playerScore1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        playerScore1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        playerScore1.setText("0");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(":");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(thisPlayerMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(playerScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(anotherPlayerMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(onePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                                        .addComponent(threePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(fourPicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(twoPicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(playerScore, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(fourPicker)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(threePicker)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(twoPicker)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(onePicker))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(anotherPlayerMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(thisPlayerMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(playerScore1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(playerScore, javax.swing.GroupLayout.Alignment.TRAILING)))
                                        .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Panel anotherPlayerMap;
    private javax.swing.JButton fourPicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton onePicker;
    private java.awt.Panel panel1;
    private javax.swing.JLabel playerScore;
    private javax.swing.JLabel playerScore1;
    private java.awt.Panel thisPlayerMap;
    private javax.swing.JButton threePicker;
    private javax.swing.JButton twoPicker;
    // End of variables declaration//GEN-END:variables

    public class MapClickObserver implements BattleshipMapClickObserver {
        @Override
        public void fieldClickedEvent(FieldClickEvent fce, BattleshipsMap bm) {
            try {
                if (bm.isFieldFilled(fce.getRow(), fce.getCol())) {
                    bm.clearField(fce.getRow(), fce.getCol());
                }
                else {
                    bm.fillField(fce.getRow(), fce.getCol(), Color.yellow);
                }
            }
            catch(OutsideOfMapPlacementException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}