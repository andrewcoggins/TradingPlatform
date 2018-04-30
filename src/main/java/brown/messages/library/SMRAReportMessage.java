package brown.messages.library; 

import java.util.Map;

import brown.bidbundle.IBidBundle;
import brown.tradeable.ITradeable;

public class SMRAReportMessage extends GameReportMessage {

  private Map<ITradeable, Double> reserve; 
  
  /**
   * for kryo. 
   */
  public SMRAReportMessage() {
    this.reserve = null; 
  }

  public SMRAReportMessage(Map<ITradeable, Double> reserve) {
    this.reserve = reserve; 
  }
  
  public Map<ITradeable, Double> getReserve() {
    return this.reserve; 
  }
  
  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    // not sure need to do any sanitizing.
    return this; 
  } 
  
}