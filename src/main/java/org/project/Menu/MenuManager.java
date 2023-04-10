package org.project.Menu;

import org.project.DataState.Client;
import org.project.DataState.SaveState;
import org.project.DataState.SaveStateHandler;
import org.project.UI.GameLoader;
import org.project.UI.GamePanel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The MenuManger class handles the logic and event handling for the Menu.
 * It implements KeyListener and MouseListener interfaces to handle keyboard
 * and mouse events.
 * @author Greg Song
 * @version 2023-04-09
 */
public class MenuManager implements KeyListener, MouseListener {
  private enum MenuState {
    MAIN_MENU,
    NEW_GAME,
    LOAD_GAME
  }
  private MenuState menuState;
  private final Menu menu;
  private final MenuTextField textField;
  private final ArrayList<MenuButton> buttons;
  SaveStateHandler saveStateHandler;
  SaveState saveState;
  GamePanel gamePanel;
  GameLoader gameLoader;
  Client client;

  /**
   * Constructs the MenuManager class.
   * @param menu The Menu object this manages.
   * @param gameLoader The GameLoader object that loads and starts the game.
   * @param gamePanel The GamePanel that displays the game.
   */
  public MenuManager(Menu menu, GameLoader gameLoader, GamePanel gamePanel) {
    this.menuState = MenuState.MAIN_MENU;
    this.menu = menu;
    this.textField = menu.getTextField();
    this.buttons = menu.getButtons();
    this.saveStateHandler = SaveStateHandler.getInstance();
    this.saveState = SaveState.getInstance();
    this.gamePanel = gamePanel;
    this.gameLoader = gameLoader;
    this.client = new Client(5000);
    attachListeners();
  }

  /**
   * Helper method for handling username input. Passes username to SaveStateHandler.
   * @param textField, a MenuTextField
   */
  private void handleTextInput(MenuTextField textField) {
    String username = textField.getInput().trim();
    saveStateHandler.setUsername(username);
  }

  /**
   * Used to attach listeners and ensures Menu has focus.
   */
  private void attachListeners() {
    menu.addMouseListener(this);
    menu.setFocusable(true);
    menu.requestFocusInWindow();
  }

  /**
   * Checks if character is a valid character for username input.
   * @param c The character to be checked.
   * @return true if character is valid, false otherwise.
   */
  private boolean isValidCharacter(char c) {
    return Character.isLetterOrDigit(c) || c == '_' || c == '-';
  }

  /**
   * Overrides keyTyped method from the KeyListener interface. Handles input
   * to MenuTextField.
   * @param e KeyEvent object representing the key event.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    char keyChar = e.getKeyChar();
    if (isValidCharacter(keyChar)) {
      textField.addChar(keyChar);
      textField.setTextColor(Color.BLACK);
      menu.repaint();
    }
  }

  /**
   * Overrides the mouseClicked method from MouseListener interface. Handles
   * mouse click events on MenuButtons. Used to which Button with ButtonName
   * is clicked, and handles accordingly.
   * @param e MouseEvent to be processed.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();
    for (MenuButton button : buttons) {
      if (button.contains(mouseX, mouseY)) {
        MenuButton.ButtonName buttonName = button.getButtonName();

        switch (buttonName) {
          case NEW_GAME -> {
            this.menuState = MenuState.NEW_GAME;
            handleMainButtonClick();
            menu.repaint();
          }
          case LOAD_GAME -> {
            this.menuState = MenuState.LOAD_GAME;
            handleMainButtonClick();
            menu.repaint();
          }
          case BACK -> {
            handleBack();
            menu.repaint();
            this.menuState = MenuState.MAIN_MENU;
          }
          case SUBMIT -> {
            handleSubmit();
            this.menuState = MenuState.MAIN_MENU;
            menu.repaint();
          }
          default -> System.err.println("Unknown ButtonName clicked.");
        }
      }
    }
  }

  /**
   * Handles button clicks when menu in MAIN_MENU state. Changes text displayed
   * in the buttons, visibility of visibles and enables KeyListening on the Menu.
   */
  private void handleMainButtonClick() {
    this.buttons.get(0).setButton(MenuButton.ButtonName.SUBMIT, "Submit");
    this.buttons.get(1).setButton(MenuButton.ButtonName.BACK, "Back");
    menu.setVisibles(true);
    menu.addKeyListener(this);
  }

  /**
   * Handles submit action. If menu in NEW_GAME state, creates a new save file
   * with username as fileName. If menu in LOAD_GAME state, loads the
   * username.json file.
   */
  private void handleSubmit() {
    if (this.menuState == MenuState.NEW_GAME) {
      handleTextInput(textField);
      gamePanel.setUpGame();
      gameLoader.switchToGamePanel();
    } else if (this.menuState == MenuState.LOAD_GAME) {
      handleLoadGameSubmit(gamePanel);
    }
  }

  /**
   * Helper method to handle loading game on submit action. Loads previous save
   * file at [username].json. If file not found changes textcolor to red to indicate
   * error.
   */
  private void handleLoadGameSubmit(GamePanel gamePanel) {
      handleTextInput(textField);
      SaveState saveState = null;
      try {
        saveState = saveStateHandler.load();
      } catch (FileNotFoundException ex) {
//        throw new RuntimeException(ex);
        textField.setTextColor(Color.RED);
        menu.repaint();
//        textField.setTextColor(Color.BLACK);
      }
      saveState.load(gamePanel.player, gamePanel);
      gameLoader.switchToGamePanel();
  }

  /**
   * Handles the back action in the menu. Resets buttons, textfield and label
   * on menu to Main menu settings.
   */
  private void handleBack() {
    this.buttons.get(0).setButton(MenuButton.ButtonName.NEW_GAME, "New Game");
    this.buttons.get(1).setButton(MenuButton.ButtonName.LOAD_GAME, "Load Game");
    menu.setVisibles(false);
    menu.removeKeyListener(this);
  }

  /**
   * Overrides keyPressed method in KeyListener interface. Enables Submit
   * action on Enter key press, and removing characters from TextField on
   * Backspace key press.
   * @param e The KeyEvent to process.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_BACK_SPACE) {
      textField.removeChar();
      textField.setTextColor(Color.BLACK);
      menu.repaint();
    } else if (keyCode == KeyEvent.VK_ENTER && this.menuState != MenuState.MAIN_MENU){
      handleSubmit();
    }
  }

  /**
   * Default empty implementation of MouseLister and KeyListener interfaces.
   * No action performed.
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {}
  @Override
  public void mouseReleased(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
  @Override
  public void keyReleased(KeyEvent e) {}

}
