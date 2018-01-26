package brown.messages.library;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import brown.messages.library.GameReportMessage;
import brown.setup.Logging;

public class LemonadeReportMessage extends GameReportMessage {
  private List<Integer>[] slots_ids;
  private final Integer[] slots_anon;
  private boolean isAnon;
  
  // void kryo
  public LemonadeReportMessage(){
    this.slots_anon = null;
    this.slots_ids = null;
    this.isAnon = true;
  }
  
  @Override
  public String toString() {
    return "LemonadeReportMessage [Anon=" + Arrays.toString(slots_anon) + 
        "\n" + "With IDs:" + Arrays.toString(slots_ids) + "]";
  }
  
  public LemonadeReportMessage(Integer[] slots, boolean isAnon) {
    this.slots_anon = slots;
    this.slots_ids = null;
    this.isAnon = isAnon;
  }
  
  public LemonadeReportMessage(List<Integer>[] slots, boolean isAnon){
    this.slots_ids = slots;
    this.slots_anon = null;
    this.isAnon = isAnon;
  }
  public LemonadeReportMessage(List<Integer>[] slots_ids, Integer[] slots_anon, boolean isAnon){
    this.slots_ids = slots_ids;
    this.slots_anon = slots_anon;
    this.isAnon = isAnon;
  }    
  
  public int getCount(int slot) {
    if (this.slots_anon != null){
      return this.slots_anon[slot]; 
    } else {
      Logging.log("[x] LemonadeReport: Null slots_anon field");
      return -1;      
    }
  }

  public List<Integer> getIDs(int slot) {
    if (this.slots_ids != null){
      return this.slots_ids[slot];      
    } else {
      Logging.log("[x] LemonadeReport: Null slots_ids field");
      return new LinkedList<Integer>();
    }
  }
  
  public boolean isAnon(){
    return this.isAnon;
  }
}
