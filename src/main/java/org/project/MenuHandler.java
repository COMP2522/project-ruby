package org.project;

import javax.swing.*;
import java.awt.*;

/**
 * MenuHandler provides menu interface to user. On interaction requests
 * save data, and loads GamePanel.
 */
public class MenuHandler {
  private static final Dimension PANELSIZE = new Dimension(768, 576);
  private static final Dimension BUTTONSIZE = new Dimension(300,120);
  private static final Dimension LABELSIZE = new Dimension(300, 50);
  private static final Dimension TEXTFIELDSIZE = new Dimension(300, 200);
  private static final Dimension BUTTONPANELSIZE = new Dimension(600,576);
  private static final Dimension TITLESIZE = new Dimension(700, 150);
  private static final String TITLEIMG = "assets/menu/title.png";

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
    Font font = label.getFont();
    label.setFont(new Font(font.getName(), font.getStyle(),30));
    return label;
  }

  /**
   * Creates a JTextField.
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
   * Creates Menu JPanel.
   * @return a JPanel with menu interface
   */
  public JPanel createMenu(){
//    ImagePanel imagePanel = new ImagePanel();

    // Create cardPanel
    JPanel mainPanel = new JPanel(new CardLayout());
    mainPanel.setPreferredSize(PANELSIZE);
    // create cardLayout
    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

    // Buttons
    JButton newGameButton = createButton("New Game");
    JButton loadGameButton = createButton("Load Game");
    JButton newSubmitButton = createButton("Submit");
    JButton loadSubmitButton = createButton("Submit");
    JButton newBackButton = createButton("Back");
    JButton loadBackButton = createButton("Back");

    JTextField newTextInput = createTextField();
    JTextField loadTextInput = createTextField();

    // Menu Button ActionListeners
    newGameButton.addActionListener(e -> {
      cardLayout.show(mainPanel, "new");
    });
    loadGameButton.addActionListener(e -> {
      cardLayout.show(mainPanel, "load");
    });
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
      loadSubmitButton.addActionListener(e -> {
        // take uid
        String input = loadTextInput.getText();
        if (input.length() > 30) {
          input = input.substring(0, 30);
        }
        System.out.println(input);
        // TODO: take uid, run Client, send Get Request, load, run GamePanel
        // call savestatehandler/client send request for doc
        // wait, load into GamePanel
        // start GamePanel
      });
      loadBackButton.addActionListener(e -> {
        cardLayout.show(mainPanel, "menu");
      });
      newBackButton.addActionListener(e -> {
        cardLayout.show(mainPanel, "menu");
      });
      // for Enter onclick
      newTextInput.addActionListener(e -> {
        String input = newTextInput.getText().trim();
        if (input.length() > 30) {
          input = input.substring(0, 30);
        }
        // TODO: save uid, run GamePanel
        System.out.println("Input: " + input);

      });
      loadTextInput.addActionListener(e -> {
        String input = loadTextInput.getText().trim();
        if (input.length() > 30) {
          input = input.substring(0, 30);
        }
        // TODO: take uid, get request from Client, load savestate, run GamePanel
        System.out.println("Input: " + input);
      });

      JLabel userLabel1 = createUserLabel();
      JLabel userLabel2 = createUserLabel();

      JPanel newGamePanel = createPanel(userLabel1, newTextInput, newSubmitButton, newBackButton);
      JPanel loadGamePanel = createPanel(userLabel2, loadTextInput, loadSubmitButton, loadBackButton);
      JPanel menuPanel = createPanel(newGameButton, loadGameButton);

      mainPanel.add(menuPanel, "menu");
      mainPanel.add(newGamePanel, "new");
      mainPanel.add(loadGamePanel, "load");

//    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
      cardLayout.show(mainPanel, "background");
      return mainPanel;
    }

    // TODO: for testing, take out and implement in Main
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    MenuHandler menuhandler = new MenuHandler();
    JPanel mainPanel = menuhandler.createMenu();
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(menuhandler.PANELSIZE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

}
