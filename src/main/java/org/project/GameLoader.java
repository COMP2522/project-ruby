package org.project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

/**
 * GameLoader is the entry point for this game. It initializes
 * SaveStateHandler, Window, game and menu panels and manages switching
 * between them.
 */
public class GameLoader {
  private JFrame window;
  private JPanel menuPanel;
  private GamePanel gamePanel;
  private SaveStateHandler saveStateHandler;

  /**
   * Constructs GameLoader. Instantiating GameLoader starts the game in JFrame,
   * and initializes SaveStateHandler.
   */
  public GameLoader() {
    this.saveStateHandler = new SaveStateHandler();

    this.window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Ruby Rush");
    window.requestFocus();

    this.gamePanel = new GamePanel();

    Menu menu = new Menu(this, window, gamePanel, saveStateHandler);
    this.menuPanel = menu.createMenu();
    this.window.add(menuPanel);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }

  /**
   * Switches window's panel from Menu to GamePanel.
   */
  public void switchToGamePanel() {
    this.window.remove(menuPanel);

    gamePanel.setUpGame();
    gamePanel.startGameThread();
    gamePanel.setEnabled(true);

    this.window.add(gamePanel);
    gamePanel.requestFocus();
    window.revalidate();
    window.repaint();
  }

  /**
   * Switches window's panel from GamePanel to Menu.
   */
  public void switchToMenuPanel() {
    this.window.remove(gamePanel);
    this.window.add(menuPanel);
    menuPanel.requestFocus();
    window.revalidate();
    window.repaint();
  }
}
