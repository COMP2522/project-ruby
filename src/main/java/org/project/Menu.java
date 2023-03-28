package org.project;

import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Menu implements ActionListener {

  /* Menu Settings */
  public final KeyHandler handler = new KeyHandler();

  /* Screen Settings */
  public static final int screenWidth = 768;
  public static final int screenHeight = 576;


  private CardLayout cardLayout;
  private JPanel cardPanel;
  private ImagePanel backgroundPanel;
//  private ImagePanel backgroundImage;
//  private ImagePanel = new ImagePanel(new ImageIcon("assets/mapData/tiles/bush.png").getImage());


  /**
   * Constructs Menu
   */
  public Menu() {
    // init
//    this.cardLayout = new CardLayout();
    this.cardPanel = new JPanel(new CardLayout());
    // sets CardPanel's layout
//    this.cardPanel.setLayout(cardLayout);

    // settings
    this.cardPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.cardPanel.setDoubleBuffered(true);
    this.cardPanel.addKeyListener(handler); // maybe take out
//    this.cardPanel.setBackground(Color.BLUE);


    ImagePanel backgroundPanel = new ImagePanel(new ImageIcon("assets/mapData/tiles/water.png").getImage());
    backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

    JButton newGameButton = new JButton("New Game");
    newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newGameButton.setPreferredSize(new Dimension(300, 100));

    JButton loadGameButton = new JButton("Load Game");
    loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadGameButton.setPreferredSize(new Dimension(300, 100));

    backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
    backgroundPanel.add(newGameButton);
    backgroundPanel.add(loadGameButton);

    backgroundPanel.add(Box.createVerticalGlue()); // add some vertical space at the top
    backgroundPanel.add(newGameButton);
    backgroundPanel.add(Box.createRigidArea(new Dimension(0, 50))); // add some vertical space between the buttons
    backgroundPanel.add(loadGameButton);
    backgroundPanel.add(Box.createVerticalGlue()); // add some vertical space at the bottom
//


    this.cardPanel.add(backgroundPanel);
//      this.cardPanel.add(createMainMenu());

  }

  /**
   * Creates Main menu
   * @return JPanel, to be added as card in cardPanel
   */
  private @NotNull JPanel createMainMenu() {
    JPanel mainMenu = new JPanel();
    // TODO: take out background color -- just for testing
    mainMenu.setBackground(Color.BLUE);

//    ImageIcon img = new ImageIcon(this.getClass().getResource("assets/mapData/tiles/water.png"));
//    JLabel label= new JLabel(img);
//    label.setSize(768, 576);



    JPanel panel = new JPanel();
    panel.setOpaque(false);
    JButton newGameButton = new JButton("New Game");
    newGameButton.setPreferredSize(new Dimension(200, 100));

    JButton loadGameButton = new JButton("Load Game");
    loadGameButton.setPreferredSize(new Dimension(200, 100));
    // test
    panel.add(newGameButton);
    panel.add(loadGameButton);

//    backgroundPanel.add(newGameButton);
//    backgroundPanel.add(loadGameButton);

//    mainMenu.setLayout(new BorderLayout());



//    mainMenu.add(newGameButton);
//    mainMenu.add(loadGameButton);
//    JPanel buttonPanel = new JPanel();
//    buttonPanel.setOpaque(false); // make it transparent
//    buttonPanel.add(new JButton("New Game"));
//    buttonPanel.add(new JButton("Load Game"));
//
//    mainMenu.add(buttonPanel, BorderLayout.PAGE_END);

//    JPanel mainMenu = new JPanel(new BorderLayout());
//    ImagePanel backgroundImage = new ImagePanel(new ImageIcon("assets/mapData/tiles/bush.png").getImage());
//    mainMenu.add(backgroundImage, BorderLayout.CENTER);
//
//    JButton newGameButton = new JButton("New Game");
//    newGameButton.setPreferredSize(new Dimension(200, 100));
//    mainMenu.add(newGameButton, BorderLayout.CENTER);
//
//    JButton loadGameButton = new JButton("Load Game");
//    loadGameButton.setPreferredSize(new Dimension(200, 100));
//    mainMenu.add(loadGameButton, BorderLayout.CENTER);
//    mainMenu.paintComponents();

//    JPanel container = new JPanel();
//    OverlayLayout overlayLayout = new OverlayLayout(container);
//    container.setLayout(overlayLayout);
//
//    JPanel panel1 = new JPanel();
//    panel1.setPreferredSize(new Dimension(200, 200));
//    panel1.setBackground(Color.BLUE);
//
//    JPanel panel2 = new JPanel();
//    panel2.setPreferredSize(new Dimension(150, 150));
//    panel2.setBackground(Color.RED);
//
//    container.add(panel1);
//    container.add(panel2);

//    return mainMenu;
//    return backgroundPanel;
      return panel;
  }



  public JPanel getCardPanel() {
    return this.cardPanel;
  }

  public void show(String cardType) {
    this.cardLayout.show(cardPanel, cardType);
  }

  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Ruby Rush");

    ImagePanel imagePanel = new ImagePanel(new ImageIcon("assets/mapData/tiles/bush.png").getImage());
    window.getContentPane().add(imagePanel);

    Menu menu = new Menu();
    window.add(menu.getCardPanel());

    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);


  }


  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(e);
  }
}
