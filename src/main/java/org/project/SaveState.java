package org.project;

import org.json.simple.JSONObject;

/**
 * Defines a save object which stores the current game state in a file
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class SaveState {
  JSONObject playerData;
  JSONObject gamePanelData;

  /**
   * Returns JSON data representing state of Player.
   * @return a JSONObject
   */
  public JSONObject getPlayerData() {
    return this.playerData;
  }

  /**
   * Returns JSON data representing state of GamePanel.
   * @return a JSONObject
   */
  public JSONObject getGamePanelData() {
    return this.gamePanelData;
  }

  /**
   * Sets SaveState data.
   * @param player a Player instance
   * @param gp a GamePanel instance
   */
  public void setSaveState(Player player, GamePanel gp) {
    setPlayerData(player);
    setGamePanelData(gp);
  }

  /* Helper methods */
  /**
   * Helper method to set the player data as a JSONObject.
   * @param player, current instance of Player
   */
  private void setPlayerData(Player player) {
    if (player == null) {
      throw new NullPointerException("Player object is null.");
    }
    JSONObject playerData = new JSONObject();
    playerData.put("worldX", player.worldX);
    playerData.put("worldY", player.worldY);
    playerData.put("screenX", player.screenX);
    playerData.put("screenY", player.screenY);
    playerData.put("speed", player.speed);
    playerData.put("direction", player.direction.ordinal());
    playerData.put("spriteCounter", player.spriteCounter);
    playerData.put("spriteNum", player.spriteNum);
    playerData.put("lives", player.getCurrentLives());
    playerData.put("rubies", player.getCurrentRubies());
    this.playerData = playerData;
  }

  /**
   * Helper method to set the Game Panel data as a JSONObject.
   * @param gp, current instance of GamePanel
   */
  private void setGamePanelData(GamePanel gp) {
    if (gp == null) {
      throw new NullPointerException("GamePanel object is null.");
    }
    JSONObject gamePanelData = new JSONObject();
    gamePanelData.put("elementArr", gp.elements);
    gamePanelData.put("npcArr", gp.npc);
    gamePanelData.put("monsterArr", gp.monster);
    this.gamePanelData = gamePanelData;
  }
}
