package org.project;

import javax.swing.*;
import java.awt.*;

/**
 * MenuHandler provides menu interface to user. On interaction requests
 * save data, and loads GamePanel.
 */
public class Menu {
  private static final Dimension PANELSIZE = new Dimension(768, 576);
  private static final Dimension BUTTONSIZE = new Dimension(300,120);
  private static final Dimension LABELSIZE = new Dimension(300, 50);
  private static final Dimension TEXTFIELDSIZE = new Dimension(300, 200);
  private static final Dimension BUTTONPANELSIZE = new Dimension(600,576);
  private static final Dimension TITLESIZE = new Dimension(700, 150);
  private static final String TITLEIMG = "assets/menu/title.png";

  SaveStateHandler saveStateHandler;
  GamePanel gamePanel;

  public Menu() {
    this.saveStateHandler = new SaveStateHandler();
  }

  /**
   * Creates JPanel with background image and adds param JComponents.
   * @param components JComponents, JButton, JLabel, JTextField
   * @return a JPanel, to be added to MenuPanel
   */
  private JPanel createPanel(JComponent... components) {
    JPanel backgroundPanel = new ImagePanel();

    JPanel buttonPanel = new JPanel();
    buttonPanel.setPreferredSize(BUTTONPANELSIZE);
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.setOpaque(false);

    // add title Label
    JLabel title = new JLabel();
    ImageIcon imageIcon = new ImageIcon(TITLEIMG);
    title.setIcon(imageIcon);
    title.setHorizontalAlignment(SwingConstants.CENTER);
    title.setPreferredSize(TITLESIZE);
    title.setOpaque(false);
    buttonPanel.add(title);

    // add components
    for (JComponent component : components) {
      buttonPanel.add(component);
    }
    backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

    return backgroundPanel;
  }

  /**
   * Creates Username Label.
   */
  private JLabel createUserLabel() {
    JLabel label = new JLabel("Username:");
    label.setPreferredSize(LABELSIZE);
    label.setForeground(Color.ORANGE);
    Font font = label.getFont();
    label.setFont(new Font(font.getName(), font.getStyle(),30));
    return label;
  }

  /**
   * Creates standard JTextField for this menu.
   */
  private JTextField createTextField() {
    JTextField textField = new JTextField(18);
    Font font = new Font("Serif", Font.BOLD, 18);
    textField.setFont(font);
    return textField;
  }

  /**
   * Creates standard menu JButton.
   * @return JButton
   */
  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(BUTTONSIZE);
    return button;
  }

  /**
   * Starts MenuHandler.
   */
  public void startMenu() {
    JFrame frame = new JFrame("Ruby Rush");
    JPanel menu = createMenu();
    frame.add(menu);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  /**
   * Creates New Game Panel.
   * @param mainPanel, JPanel this should be added to
   * @return NewGamePanel, a JPanel
   */
  private JPanel createNewGameCard(JPanel mainPanel) {
    JButton newSubmitButton = createButton("Submit");
    JButton newBackButton = createButton("Back");
    JLabel userLabel = createUserLabel();
    JTextField newTextInput = createTextField();

    // onclick submit
    newSubmitButton.addActionListener(e -> {
      // take uid
      String input = newTextInput.getText();
      if (input.length() > 30){
        input = input.substring(0, 30);
      }
      System.out.println(input);
      // TODO: save uid, run GamePanel
      // call savestateHandler/client and store uid
      // start GamePanel

    });

    // onclick back
    newBackButton.addActionListener(e -> {
      CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
      cardLayout.show(mainPanel, "menu");
    });

    // on enter keypress
    newTextInput.addActionListener(e -> {
      String input = newTextInput.getText().trim();
      if (input.length() > 30) {
        input = input.substring(0, 30);
      }
      // TODO: save uid, run GamePanel
      System.out.println("Input: " + input);
    });

    return createPanel(userLabel, newTextInput, newSubmitButton, newBackButton);
  }

  /**
   * Creates New Game Panel.
   * @param mainPanel, JPanel this should be added to
   * @return NewGamePanel, a JPanel
   */
  private JPanel createLoadGameCard(JPanel mainPanel) {
    JButton loadSubmitButton = createButton("Submit");
    JButton loadBackButton = createButton("Back");
    JTextField loadTextInput = createTextField();
    JLabel userLabel = createUserLabel();

    loadSubmitButton.addActionListener(e -> {
      // take uid
      String input = loadTextInput.getText();
      if (input.length() > 30) {
        input = input.substring(0, 30);
      }
      // FIXME: testing
      System.out.println(input);
      // TODO: take uid, run Client, send Get Request, load, run GamePanel
      // call savestatehandler/client send request for doc
      // wait, load into GamePanel
      // start GamePanel
    });

    loadBackButton.addActionListener(e -> {
      CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
      cardLayout.show(mainPanel, "menu");
    });

    loadTextInput.addActionListener(e -> {
      String input = loadTextInput.getText().trim();
      if (input.length() > 30) {
        input = input.substring(0, 30);
      }
      // TODO: take uid, get request from Client, load savestate, run GamePanel
      System.out.println("Input: " + input);
    });

    return createPanel(userLabel, loadTextInput, loadSubmitButton, loadBackButton);
  }

  /**
   * Creates Main menu Panel.
   * @param mainPanel, JPanel this should be added to
   * @return Main Menu Panel, a JPanel
   */
  private JPanel createMenuCard(JPanel mainPanel) {
    JButton newGameButton = createButton("New Game");
    JButton loadGameButton = createButton("Load Game");
    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

    newGameButton.addActionListener(e -> {
      cardLayout.show(mainPanel, "new");
    });
    loadGameButton.addActionListener(e -> {
      cardLayout.show(mainPanel, "load");
    });

    return createPanel(newGameButton, loadGameButton);
  }

  /**
   * Creates Menu JPanel.
   * @return a JPanel with menu interface
   */
  public JPanel createMenu(){
    JPanel mainPanel = new JPanel(new CardLayout());
    mainPanel.setPreferredSize(PANELSIZE);

    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

    JPanel newGamePanel = createNewGameCard(mainPanel);
    JPanel loadGamePanel = createLoadGameCard(mainPanel);
    JPanel menuPanel = createMenuCard(mainPanel);

    mainPanel.add(menuPanel, "menu");
    mainPanel.add(newGamePanel, "new");
    mainPanel.add(loadGamePanel, "load");

    cardLayout.show(mainPanel, "background");
    return mainPanel;
  }

    // TODO: for testing, take out and implement in Main
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
