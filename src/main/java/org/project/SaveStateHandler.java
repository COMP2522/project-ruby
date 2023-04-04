package org.project;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;

/**
 * SaveStateHandler manages reading and writing SaveState. Files saved in JSON format.
 */
public class SaveStateHandler {
  private String dirPath = "save/";
  private String extension = ".json";
  private String username;
  private String pathName = dirPath + username + extension;

  /**
   * Stores save data as a JSON file in save directory.
   * @param saveState, current saveState of the game
   */
  public void save(SaveState saveState) {
    //TODO: fix this
    setUsername(username);
    JSONObject jsonSave = new JSONObject();
    jsonSave.put("playerData", saveState.getPlayerData());
    jsonSave.put("gamePanelData", saveState.getGamePanelData());

    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(pathName);
      fileWriter.write(jsonSave.toJSONString());
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException("Could not create or write to file at: " + pathName, e);
    }
  }

  /**
   * Loads save data JSON file from directory and returns saveState.
//   * @param username a String, name of the file
   * @throws FileNotFoundException
   * @return SaveState object
   */
  public SaveState load() throws FileNotFoundException {
    File saveFile = new File(dirPath + username + extension);
    JSONObject jsonSave;
    try {
      FileReader fileReader = new FileReader(saveFile);
      jsonSave = (JSONObject) JSONValue.parse(fileReader);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Cannot locate file:" + pathName);
    }
    SaveState saveState = SaveState.getInstance();
    saveState.setSaveState(jsonSave);
    return saveState;
  }

  /**
   * Sets username.
   * @param username a String, used as file name of save data
   */
  public void setUsername(String username) {
    this.username = username;
  }
}
