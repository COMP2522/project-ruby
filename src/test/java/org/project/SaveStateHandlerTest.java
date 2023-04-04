package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class SaveStateHandlerTest {
  private GamePanel gamePanel;

  @BeforeEach
  public void setUp() {
    // from Main
//    JFrame window = new JFrame();
//    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    window.setResizable(false);
//    window.setTitle("Ruby Rush");

    gamePanel = new GamePanel();
//    window.add(gamePanel);
//    window.pack();

    // the window from this class can display a 16 * 12 tiles, where each tile is (48 by 48 pixels itself).
//    window.setLocationRelativeTo(null);
//    window.setVisible(true);

    gamePanel.setUpGame();
    gamePanel.startGameThread();
  }

  @Test
  public void testSaveStateHandler() {
//    SaveState saveState = new SaveState();
//    saveState.setSaveState(gamePanel.player, gamePanel);

  }
}
