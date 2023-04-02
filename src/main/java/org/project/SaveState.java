package org.project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Arrays;

/**
 * Defines a save object which stores the current game state in a file
 *
 * @author Nathan Bartyuk, Greg Song
 * @version 2023-03-31
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
   * Sets SaveState data from Object instances.
   * @param gp a GamePanel instance
   */
  public void setSaveState(GamePanel gp) {
    setPlayerData(gp.player);
    setGamePanelData(gp);
  }

  /**
   * Sets SaveState data from JSONObject
   * @param json
   */
  public void setSaveState(JSONObject json) {
    this.gamePanelData = (JSONObject) json.get("gamePanelData");
    this.playerData = (JSONObject) json.get("playerData");
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
    gamePanelData.put("elementArr", arrToJSON(gp.elements));
    gamePanelData.put("npcArr", arrToJSON(gp.npc));
    gamePanelData.put("monsterArr", arrToJSON(gp.monster));
    this.gamePanelData = gamePanelData;
  }

  /**
   * Creates a JSONArray of Positionable objects consisting of each
   * Positionable's worldX and worldY.
   * @param positionables, a Positionable array
   * @return a JSONArray of JSONObjects with x and y properties
   */
  private JSONArray arrToJSON(Positionable[] positionables) {
    JSONArray jsonArr = new JSONArray();
    Arrays.stream(positionables)
        .filter(n -> n != null)
        .map(positionable -> {
          if (positionable != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", ((Positionable) positionable).getWorldX());
            jsonObject.put("y", ((Positionable) positionable).getWorldY());
            return jsonObject;
          } else {
            return null;
          }
        })
        .forEach(jsonArr::add);
      return jsonArr;
  }
}
