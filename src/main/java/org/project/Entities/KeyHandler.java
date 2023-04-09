package org.project.Entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Manages all key input from the player
 * @author Nathan Bartyuk
 * @version 2023-04-04
 */
public class KeyHandler implements KeyListener {
  public boolean upPressed, downPressed, leftPressed, rightPressed; // keys pressed

  @Override
  public void keyTyped(KeyEvent e) {}  // not using this one garbage

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_W) {
      upPressed = true;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = true;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = true;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_W) {
      upPressed = false;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = false;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = false;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = false;
    }
  }

}
