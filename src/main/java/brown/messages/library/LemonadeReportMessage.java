package brown.messages.library;

import brown.messages.library.GameReportMessage;

public class LemonadeReportMessage extends GameReportMessage {
  
  private final Integer[] SLOTS;
  
  public LemonadeReportMessage() {
    this.SLOTS = null;
  }
  
  public LemonadeReportMessage(Integer[] slots) {
    this.SLOTS = slots;
  }
  
  public int getCount(int slot) {
    return this.SLOTS[slot];
  }
  
}
