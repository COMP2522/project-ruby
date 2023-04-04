package org.project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class GameLoader {
  private JFrame window;
  private JPanel menuPanel;
  private GamePanel gamePanel;
  private SaveStateHandler saveStateHandler;

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

  public void switchToGamePanel() {
    this.window.remove(menuPanel);

    gamePanel.setUpGame();
    gamePanel.startGameThread();
    gamePanel.setEnabled(true);
//    gamePanel.requestFocusInWindow();
//    gamePanel.requestFocus();

    this.window.add(gamePanel);
    gamePanel.requestFocus();
    window.revalidate();
    window.repaint();
  }

  public void switchToMenuPanel() {
    this.window.remove(gamePanel);
    this.window.add(menuPanel);
    window.revalidate();
    window.repaint();
  }
}
