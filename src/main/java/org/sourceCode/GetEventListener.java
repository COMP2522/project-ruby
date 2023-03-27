package org.sourceCode;

public class GetEventListener implements EventListener {

  @Override
  public void onTrigger() {
    // use FileHandler (Consumser?)
    // create response and return
    System.out.println("getEvent Triggered");
  }
}
