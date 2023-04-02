package org.project;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;

/**
 * SaveStateHandler manages reading and writing SaveState. Files saved in JSON format.
 */
public class SaveStateHandler {
  private String dirPath = "save/";
  private String fileName = "save_2.json";
  private String pathName = dirPath + fileName;

  /**
   * Stores save data as a JSON file in save directory.
   * @param saveState, current saveState of the game
   */
  public void save(SaveState saveState) throws IOException {
    JSONObject jsonSave = new JSONObject();
    jsonSave.put("playerData", saveState.getPlayerData());
    jsonSave.put("gamePanelData", saveState.getGamePanelData());

    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(pathName);
      fileWriter.write(jsonSave.toJSONString());
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException("Could not create or write to file at: " + dirPath + fileName, e);
    }
  }

  /**
   * Loads save data JSON file from directory.
   * @param fileName a String, name of the file
   * @throws FileNotFoundException
   */
  public void load(String fileName) throws FileNotFoundException {
    File saveFile = new File(fileName);
    JSONObject jsonSave;
    try {
      FileReader fileReader = new FileReader(saveFile);
      jsonSave = (JSONObject) JSONValue.parse(fileReader);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Cannot locate file:" + pathName);
    }
    SaveState saveState = new SaveState();
    saveState.setSaveState(jsonSave);
  }

  public static void main(String[] args) throws IOException {
    GamePanel gp = new GamePanel();
    KeyHandler kh = new KeyHandler();
    Player player = Player.getInstance(gp, kh);
    gp.setUpGame();

    SaveState saveState = new SaveState();
    saveState.setSaveState(player, gp);

    SaveStateHandler ssh = new SaveStateHandler();

    ssh.save(saveState);
    System.out.println("File saved");
  }

}
