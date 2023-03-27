package org.sourceCode;

public class PostEventListener implements EventListener {

  @Override
  public void onTrigger() {
    // take data and write to file
    // use DatabaseHandler
    System.out.println("Postevent triggered");
  }
}
