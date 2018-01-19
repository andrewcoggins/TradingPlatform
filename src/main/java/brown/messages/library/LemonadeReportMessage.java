package brown.messages.library;

import java.util.Arrays;

import brown.messages.library.GameReportMessage;

public class LemonadeReportMessage extends GameReportMessage {
  
  @Override
  public String toString() {
    return "LemonadeReportMessage [SLOTS=" + Arrays.toString(SLOTS) + "]";
  }

  private final Integer[] SLOTS;
  
  public LemonadeReportMessage() {
    this.SLOTS = new Integer[12];
    for (int i = 0; i<12;i++){
      this.SLOTS[i] = 0;
    }
  }
  
  public LemonadeReportMessage(Integer[] slots) {
    this.SLOTS = slots;
  }
  
  public int getCount(int slot) {
    return this.SLOTS[slot];
  }
  
}
