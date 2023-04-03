package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class SaveStateHandlerTest {
  private GamePanel gamePanel;

  @BeforeEach
  public void setUp() {
    gamePanel = new GamePanel();
    gamePanel.setUpGame();
    gamePanel.startGameThread();
  }

  @Test
  public void testSaveStateHandler() {
    SaveState saveState = SaveState.getInstance();
    saveState.setSaveState(gamePanel);

  }
}
