package org.project;

import org.junit.jupiter.api.Test;
import org.project.ui.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class SoundTest {

  // Test for constructor
  @Test
  public void testConstructor() {
    Sound sound = new Sound();
    assertNotNull(sound);
  }

  // Test for setFile() method
  @Test
  public void testSetFile() {
    Sound sound = new Sound();
    URL soundURL = null;
    try {
      soundURL = new File("assets/sound/background.wav").toURI().toURL();
    } catch (Exception e) {
      e.printStackTrace();
    }
    AudioInputStream audioInputStream = null;
    try {
      audioInputStream = AudioSystem.getAudioInputStream(soundURL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Clip clip = null;
    try {
      clip = AudioSystem.getClip();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      clip.open(audioInputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
    sound.setFile(0);
    assertEquals(clip.getFrameLength(), sound.clip.getFrameLength());
  }

  // Test for stop() method
  @Test
  public void testStop() {
    Sound sound = new Sound();
    sound.setFile(0);
    sound.play();
    sound.stop();
    assertFalse(sound.clip.isRunning());
  }

  // Test for play() method when clip is not null but not open
  @Test
  public void testPlayClipNotOpen() throws LineUnavailableException {
    Sound sound = new Sound();
    sound.clip = AudioSystem.getClip();
    sound.play();
    assertFalse(sound.clip.isRunning());
  }

  // Test for loop() method when clip is not null but not open
  @Test
  public void testLoopClipNotOpen() throws LineUnavailableException {
    Sound sound = new Sound();
    sound.clip = AudioSystem.getClip();
    sound.loop();
    assertFalse(sound.clip.isRunning());
  }
  }
