package org.project;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuHandler {
  private static final Dimension PANELSIZE = new Dimension(768, 576);

  /**
   * Constructs Menu
   */
  public MenuHandler() {

  }

  public ActionListener getNewGameButtonListener(CardLayout cardLayout, JPanel mainPanel) {
    return new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(mainPanel, "new");
      }
    };
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    Menu menuhandler = new Menu();
    JPanel mainPanel = menuhandler.createMenu();
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(PANELSIZE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }


}
