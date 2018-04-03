package brown.messages.library; 

import java.util.Map;

import brown.bidbundle.IBidBundle;

public class SMRAReportMessage extends GameReportMessage {

  private IBidBundle reserve; 
  
  /**
   * for kryo. 
   */
  public SMRAReportMessage() {
    this.reserve = null; 
  }

  public SMRAReportMessage(IBidBundle reserve) {
    this.reserve = reserve; 
  }
  
  public IBidBundle getReserve() {
    return this.reserve; 
  }
  
  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    // not sure need to do any sanitizing.
    return this; 
  } 
  
}