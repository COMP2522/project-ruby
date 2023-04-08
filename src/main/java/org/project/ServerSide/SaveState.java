package org.project.ServerSide;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.project.*;
import org.project.Entities.Entity;
import org.project.Entities.Monster;
import org.project.Entities.Player;
import org.project.Entities.Villager;
import org.project.Objects.*;

import java.util.Arrays;
import java.util.Objects;

/**
 * Defines a save object which stores the current game state in a file
 *
 * @author Nathan Bartyuk, Greg Song
 * @version 2023-03-31
 */
public class SaveState {
  private static final int ARR_SIZE = 20;
  private static SaveState instance;
  JSONObject playerData;
  JSONObject gamePanelData;
  GamePanel gamePanel;


  public static SaveState getInstance() {
    if (instance == null) {
      instance = new SaveState();
    }
    return instance;
  }

  /**
   * Loads Player variables from JSON playerData.
   * @param player a Player object in game
   */
  public void load(Player player, GamePanel gamePanel) {
    if (playerData == null) {
      throw new NullPointerException("PlayerData object is null.");
    }
    this.gamePanel = player.gp;

    player.setWorldX(((Long) playerData.get("worldX")).intValue());
    player.setWorldY(((Long) playerData.get("worldY")).intValue());
    player.speed = ((Long) playerData.get("speed")).intValue();
    player.spriteCounter = ((Long) playerData.get("spriteCounter")).intValue();
    player.spriteNum = ((Long) playerData.get("spriteNum")).intValue();
    player.setLives(((Long) playerData.get("lives")).intValue());
    player.setCurrentRubies(((Long) playerData.get("rubies")).intValue());
    
    gamePanel.elements = parseElementArr((JSONArray) this.gamePanelData.get("elementArr"));
    gamePanel.npc = parseNPCArr((JSONArray) this.gamePanelData.get("npcArr"));
    gamePanel.monster = parseMonsterArr((JSONArray) this.gamePanelData.get("monsterArr"));
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
    playerData.put("worldX", player.getWorldX());
    playerData.put("worldY", player.getWorldY());
    playerData.put("speed", player.speed);
    playerData.put("direction", player.direction.ordinal());
    playerData.put("spriteCounter", player.spriteCounter);
    playerData.put("spriteNum", player.spriteNum);
    playerData.put("lives", player.getLives());
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
   * Creates a JSONArray of Positionable objects consisting of each worldX and worldY.
   * @param positionables, a Positionable array
   * @return a JSONArray of JSONObjects with x and y properties
   */
  private JSONArray arrToJSON(Positionable[] positionables) {
    JSONArray jsonArr = new JSONArray();
    Arrays.stream(positionables)
        .filter(Objects::nonNull)
        .map(positionable -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", positionable.getClass().getSimpleName());
            jsonObject.put("x", positionable.getWorldX());
            jsonObject.put("y", positionable.getWorldY());
            return jsonObject;
        })
        .forEach(jsonArr::add);
      return jsonArr;
  }

  /**
   * Parses JSONArray and returns Element array.
   * @param objs JSONArray, in save file
   * @return Element[]
   */
  private Element[] parseElementArr(JSONArray objs) {
    Element[] array = new Element[20];
    for (int i = 0; i < objs.size(); i++) {
      JSONObject obj = (JSONObject) objs.get(i);
      array[i] = jsonToElement(obj);
    }
    return array;
  }

  /**
   * Parse JSONArray and returns Monster array.
   * @param objs a JSONObject representing Monster
   * @return Entity[]
   */
  private Entity[] parseMonsterArr(JSONArray objs) {
    Entity[] array = new Entity[ARR_SIZE];
    for (int i = 0; i < objs.size(); i++) {
      JSONObject obj = (JSONObject) objs.get(i);
      Monster monster = new Monster(gamePanel, 0, 0);
      ((Positionable) monster).setWorldX(((Number) obj.get("x")).intValue());
      ((Positionable) monster).setWorldY(((Number) obj.get("y")).intValue());
      array[i] = monster;
    }
    return array;
  }

  /**
   * Parse JSONArray and return NPC array.
   * @param objs a JSONObject representing NPC
   * @return Entity[]
   */
  private Entity[] parseNPCArr(JSONArray objs) {
    Entity[] array = new Entity[ARR_SIZE];
    for (int i = 0; i < objs.size(); i++) {
      JSONObject obj = (JSONObject) objs.get(i);
      Villager villager = new Villager(gamePanel, 0, 0);
      ((Positionable) villager).setWorldX(((Number) obj.get("x")).intValue());
      ((Positionable) villager).setWorldY(((Number) obj.get("y")).intValue());
      array[i] = villager;
    }
    return array;
  }

  /**
   * Converts JSONObject representing Element and converts to Element subtype.
   * @param obj a JSONObject representing Element
   * @return Ruby, Fire, PowerUp or Door with proper worldX and worldY
   */
  private Element jsonToElement(JSONObject obj) {
    String type = (String) obj.get("type");
    switch (type) {
      case "Ruby" -> {
        Ruby ruby = new Ruby();
        ((Positionable) ruby).setWorldX(((Number) obj.get("x")).intValue());
        ((Positionable) ruby).setWorldY(((Number) obj.get("y")).intValue());
        return ruby;
      }
      case "PowerUp" -> {
        PowerUp powerUp = new PowerUp();
        ((Positionable) powerUp).setWorldX(((Number) obj.get("x")).intValue());
        ((Positionable) powerUp).setWorldY(((Number) obj.get("y")).intValue());
        return powerUp;
      }
      case "Fire" -> {
        Fire fire = new Fire();
        ((Positionable) fire).setWorldX(((Number) obj.get("x")).intValue());
        ((Positionable) fire).setWorldY(((Number) obj.get("y")).intValue());
        return fire;
      }
      case "Door" -> {
        Door door = new Door();
        ((Positionable) door).setWorldX(((Number) obj.get("x")).intValue());
        ((Positionable) door).setWorldY(((Number) obj.get("y")).intValue());
        return door;
      }
      default -> throw new IllegalArgumentException("Invalid entity type: " + type);
    }
  }
  
  public JSONObject getPlayerData() {
    return this.playerData;
  }
  public JSONObject getGamePanelData() {
    return this.gamePanelData;
  }
  public void setSaveState(GamePanel gp) {
    this.gamePanel = gp;
    setPlayerData(gp.player);
    setGamePanelData(gp);
  }
  public void setSaveState(JSONObject json) {
    this.gamePanelData = (JSONObject) json.get("gamePanelData");
    this.playerData = (JSONObject) json.get("playerData");
  }
}
