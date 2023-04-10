package org.project.Menu;

import org.project.UI.GameLoader;
import org.project.UI.GamePanel;

import javax.swing.JPanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Menu class represents the Main menu screen of the game. It extends
 * JPanel and is responsible for initializing the MenuComponents.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public class Menu extends JPanel {
  /* Constants */
  private static final Dimension PANEL_SIZE = new Dimension(768, 576);
  private static final Dimension BUTTON_SIZE = new Dimension(300, 100);
  private static final Dimension LABEL_SIZE = new Dimension(100, 50);
  private static final Dimension INPUT_SIZE = new Dimension(300, 30);
  private static final Dimension TITLE_SIZE = new Dimension(700, 150);
  private static final String TITLE_IMAGE = "assets/menu/title.png";
  private static final String BG_IMAGE = "assets/menu/jungle.jpeg";
  private static final int PANEL_WIDTH_HALF = 768 / 2;
  private static final int BUTTON_X_OFFSET = PANEL_WIDTH_HALF - (BUTTON_SIZE.width / 2);
  private static final int TITLE_X_OFFSET = PANEL_WIDTH_HALF - (TITLE_SIZE.width / 2);
  private static final int[] VERTICAL_OFFSETS = {50, 200, 250, 290, 400};
  private static final Font FONT = new Font("Arial", Font.BOLD, 20);
  private static final Color BUTTON_COLOR = Color.orange;
  private static final Color CLEAR_COLOR = new Color(0, 0, 0, 0);

  /* Variables */
  private final ArrayList<MenuComponent> components;
  private final ArrayList<MenuButton> buttons;
  private final ArrayList<Visible> visibles;

  /**
   * Constructs the Menu.
   *
   * @param gameLoader, The GameLoader object
   * @param gamePanel,  The GamePanel to switch to.
   * @throws IOException If image cannot be opened.
   */
  public Menu(GameLoader gameLoader, GamePanel gamePanel) throws IOException {
    this.setPreferredSize(PANEL_SIZE);
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.setFocusable(true);
    components = new ArrayList<>();
    this.buttons = new ArrayList<>();
    this.visibles = new ArrayList<>();
    initComponents();
    new MenuManager(this, gameLoader, gamePanel);
  }

  /**
   * Helper method to add MenuComponents to components list.
   *
   * @param components The MenuComponent objects to be added to the menu.
   */
  private void addComponents(MenuComponent... components) {
    Collections.addAll(this.components, components);
  }

  /**
   * Initializes all the MenuComponents of the menu.
   *
   * @throws IOException If there is an error loading required images.
   */
  private void initComponents() throws IOException {
    MenuImage background = new MenuImage(0, 0, PANEL_SIZE.width, PANEL_SIZE.height, BG_IMAGE);
    MenuImage title = new MenuImage(TITLE_X_OFFSET, VERTICAL_OFFSETS[0],
      TITLE_SIZE.width, TITLE_SIZE.height, TITLE_IMAGE);
    MenuLabel label = new MenuLabel(
      BUTTON_X_OFFSET, VERTICAL_OFFSETS[1], LABEL_SIZE.width,
      LABEL_SIZE.height, CLEAR_COLOR, BUTTON_COLOR,
      FONT, "Username:", false);
    MenuTextField input = new MenuTextField(
      BUTTON_X_OFFSET, VERTICAL_OFFSETS[2], INPUT_SIZE.width,
      INPUT_SIZE.height, FONT, 30, false);
    MenuButton button1 = new MenuButton(
      BUTTON_X_OFFSET, VERTICAL_OFFSETS[3], BUTTON_SIZE.width,
      BUTTON_SIZE.height, BUTTON_COLOR, Color.BLACK,
      FONT, "New Game", MenuButton.ButtonName.NEW_GAME);
    MenuButton button2 = new MenuButton(
      BUTTON_X_OFFSET, VERTICAL_OFFSETS[4], BUTTON_SIZE.width,
      BUTTON_SIZE.height, BUTTON_COLOR, Color.BLACK,
      FONT, "Load Game", MenuButton.ButtonName.LOAD_GAME);
    addComponents(background, title, label, input, button1, button2);
    buttons.add(button1);
    buttons.add(button2);
    visibles.add(label);
    visibles.add(input);
  }

  /**
   * Overrides the paintComponent method. Used to paint all MenuComponents.
   *
   * @param g the Graphics object used for painting.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (MenuComponent component : components) {
      component.draw(g);
    }
  }

  /**
   * Gets the MenuTextField of this menu.
   *
   * @return MenuTextField for username.
   */
  public MenuTextField getTextField() {
    return (MenuTextField) this.components.get(3);
  }

  /**
   * Gets the MenuButtons of this menu.
   *
   * @return buttons The list of MenuButtons.
   */
  public ArrayList<MenuButton> getButtons() {
    return buttons;
  }

  /**
   * Sets the visibility of MenuComponents that implement Visible.
   *
   * @param isVisible true if MenuComponent should be set visible, false
   *                  if they should be hidden.
   */
  public void setVisibles(boolean isVisible) {
    for (Visible visible : visibles) {
      visible.setVisible(isVisible);
    }
  }


}
