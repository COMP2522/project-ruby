package org.project;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {
  Clip clip;
  URL soundURL[] = new URL[30];

  public Sound() {
    soundURL[0] = getClass().getResource("assets/sound/fire2.wav");
  }

  public void setFile(int i) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);
      if (clip == null) {
        System.out.println("error");
      }
    } catch (Exception e) {
      System.out.println("unable to open file.");
    }
  }

  public void play() {
    clip.start();
  }

  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    clip.stop();
  }
}
