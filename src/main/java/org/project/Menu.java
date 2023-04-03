package org.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * MenuHandler provides menu interface to user. On interaction requests
 * save data, and loads GamePanel.
 */
public class Menu {
  private static final Dimension PANELSIZE = new Dimension(768, 576);
  private static final Dimension BUTTONSIZE = new Dimension(300,100);
  private static final Dimension LABELSIZE = new Dimension(300, 50);
  private static final Dimension TEXTFIELDSIZE = new Dimension(300, 200);
  private static final Dimension BUTTONPANELSIZE = new Dimension(500,576);
  private static final Dimension TITLESIZE = new Dimension(700, 150);
  private static final String TITLEIMG = "assets/menu/title.png";

  SaveStateHandler saveStateHandler;
  GamePanel gamePanel;
  JFrame window;
  GameLoader gameLoader;
  Client client;

  public Menu(GameLoader gameLoader, JFrame window, GamePanel gamePanel, SaveStateHandler saveStateHandler) {
    this.saveStateHandler = saveStateHandler;
    this.window = window;
    this.gamePanel = gamePanel;
    this.gameLoader = gameLoader;
    this.client = new Client(5000);
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
//    textField.setPreferredSize(TEXTFIELDSIZE);
    Font font = new Font("Arial", Font.BOLD, 18);
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
    button.setFont(new Font("Arial", Font.BOLD, 20));
    return button;
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

    // onclick submit button
    newSubmitButton.addActionListener(e -> {
      handleTextInput(newTextInput);
      gameLoader.switchToGamePanel();
    });

    // onclick back button
    newBackButton.addActionListener(e -> {
      CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
      cardLayout.show(mainPanel, "menu");
    });

    // on enter keypress
    newTextInput.addActionListener(e -> {
      handleTextInput(newTextInput);
      gameLoader.switchToGamePanel();
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

    loadSubmitButton.addActionListener(handleLoadGame(client, gamePanel, loadTextInput));

    loadBackButton.addActionListener(e -> {
      CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
      cardLayout.show(mainPanel, "menu");
    });

    loadTextInput.addActionListener(handleLoadGame(client, gamePanel, loadTextInput));

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

  /**
   * Helper method to handle loading game.
   * @return ActionListener
   */
  private ActionListener handleLoadGame(Client client, GamePanel gamePanel, JTextField loadTextInput) {
    return e -> {
      handleTextInput(loadTextInput);
      SaveState saveState;
      try {
        saveState = saveStateHandler.load(null);
      } catch (FileNotFoundException ex) {
        throw new RuntimeException(ex);
      }
      gamePanel.player.loadPlayerData(saveState.getPlayerData());
      // wait, load into GamePanel
      gameLoader.switchToGamePanel();
    };
  }

  /**
   * Helper method for handling JTextField input.
   * @param textField
   */
  private void handleTextInput(JTextField textField) {
    String username = textField.getText().trim();
    if (username.length() > 30) {
      username = username.substring(0, 30);
    }
    saveStateHandler.setUsername(username);
  }
}
