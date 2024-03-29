package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Datastate.SaveState;
import org.project.ui.GamePanel;

public class SaveStateHandlerTest {
  private GamePanel gamePanel;

  @BeforeEach
  public void setUp() {
    gamePanel = GamePanel.getGamePanel();
    gamePanel.setUpGame();
    gamePanel.startGameThread();
  }

  @Test
  public void testSaveStateHandler() {
    SaveState saveState = new SaveState();
    saveState.setSaveState(gamePanel);
  }
}
