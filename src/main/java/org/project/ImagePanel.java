package org.project;

import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel {
  private JPanel cardPanel;

  private Image backgroundImage;
  public static final int screenWidth = 768;
  public static final int screenHeight = 576;

  public ImagePanel(Image backgroundImage) {
    this.backgroundImage = backgroundImage;
//    this.cardPanel.setDoubleBuffered(true);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Ruby Rush");

    // cardPanel
    JPanel mainPanel = new JPanel(new CardLayout());
    mainPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));

    // main menu
    ImagePanel backgroundPanel = new ImagePanel(new ImageIcon("assets/mapData/tiles/bush.png").getImage());
//    backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

//    backgroundPanel.setLayout(new BorderLayout());

    // inits button panel
    JPanel buttonPanel = new JPanel();
//    buttonPanel.setBackground(Color.red);
    buttonPanel.setPreferredSize(new Dimension(400,576));
    buttonPanel.setLayout(new FlowLayout());
//    buttonPanel.setBounds(10); // TODO: stopped here
    buttonPanel.setOpaque(false); // to get rid of color

    // create buttons
    JButton button1 = new JButton();
    JButton button2 = new JButton("Button 2");
    button1.setPreferredSize(new Dimension(300,120));
    button2.setPreferredSize(new Dimension(300,120));
//    button2.setMaximumSize(new Dimension(100,200));

    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
    // action listeners for buttons to direct to different card
//    JButton button1 = new JButton("New Game");
    button1.addActionListener(e -> {
      cardLayout.show(mainPanel, "overlay");
    });

//
//    JButton button2 = new JButton("Load Game");
    button2.addActionListener(e -> {
      cardLayout.show(mainPanel, "load");
    });


    // Create title Label
    JLabel title = new JLabel("Ruby Rush");
    title.setHorizontalAlignment(SwingConstants.CENTER);
    title.setPreferredSize(new Dimension(300, 150));
    title.setBackground(Color.GREEN);
    title.setOpaque(true);

    // label for button 1
    ImageIcon img1 = new ImageIcon("assets/mapData/tiles/tree.png");
    Image image = img1.getImage();
    JLabel label1 = new JLabel(img1);
    label1.setBackground(Color.GREEN);
    label1.setHorizontalAlignment(SwingConstants.CENTER);
    label1.setPreferredSize(new Dimension(110,110)); // not sure how to scale
    button1.add(label1);

    // add to buttonpanel
    buttonPanel.add(title);
    buttonPanel.add(button1);
    buttonPanel.add(button2);

    // add buttonPanel to main Panel
    backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

//    Box verticalBox = Box.createVerticalBox();
//    verticalBox.add(button1);
//    verticalBox.add(button2);
//    buttonPanel.add(verticalBox);

//    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
//    buttonPanel.add(Box.createVerticalGlue());
//    buttonPanel.add(button1);
//    buttonPanel.add(Box.createVerticalStrut(10));
//    buttonPanel.add(button2);
//    buttonPanel.add(Box.createVerticalGlue());
//    buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

//    backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

//    JPanel buttonPanel = new JPanel();
//    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
//    buttonPanel.add(button1);
//    buttonPanel.add(button2);
//    backgroundPanel.add(buttonPanel);
//
//    backgroundPanel.setPreferredSize(new Dimension(400,400));


    // newGamePanel
    JPanel newGamePanel = new JPanel();
    newGamePanel.add(new JLabel("This is the New Game panel"));


    JPanel loadPanel = new JPanel();
    loadPanel.add(new JLabel("This is load Panel"));

    mainPanel.add(backgroundPanel, "background");
    mainPanel.add(newGamePanel, "new");
    mainPanel.add(loadPanel, "load");

//    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
    cardLayout.show(mainPanel, "background");




//    backgroundPanel.add(button1);
//    backgroundPanel.add(button2);

    frame.add(mainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
