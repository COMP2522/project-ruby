package org.project;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
//    JFrame window = new JFrame();
//    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    window.setResizable(false);
//    window.setTitle("Ruby Rush");
//
//    GamePanel gamePanel = new GamePanel();
//    window.add(gamePanel);
//    window.pack();
//
//    // the window from this class can display a 16 * 12 tiles, where each tile is (48 by 48 pixels itself).
//    window.setLocationRelativeTo(null);
//    window.setVisible(true);
//
//    gamePanel.setUpGame();
//    gamePanel.startGameThread();
    new GameLoader();
  }
}