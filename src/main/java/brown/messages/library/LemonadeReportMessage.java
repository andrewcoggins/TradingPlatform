package brown.messages.library;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        "\n" + "With IDs:" + Arrays.toString(slots_ids) + ", isAnon: " + isAnon + "]";
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
  
  public GameReportMessage sanitize(Integer agent, Map<Integer,Integer> privateToPublic){    
    if (this.isAnon){  
      return this;
    } else {
      @SuppressWarnings("unchecked")      
      List<Integer>[] sanitized_slots = (List<Integer>[]) new List[this.slots_anon.length];;
      for (int i=0;i<this.slots_anon.length;i++){
        sanitized_slots[i] = new LinkedList<Integer>();
        for (Integer a : this.slots_ids[i]){
          sanitized_slots[i].add((a == agent? a : privateToPublic.get(a)));
        }
      }
      return new LemonadeReportMessage(sanitized_slots,this.slots_anon,false);
    }   
  }
  
  public boolean isAnon(){
    return this.isAnon;
  }
}
