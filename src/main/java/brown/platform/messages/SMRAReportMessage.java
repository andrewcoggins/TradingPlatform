package brown.platform.messages; 

import java.util.Map;

import brown.mechanism.tradeable.ITradeable;

/**
 * A Game report message for a SMRA auction. 
 * Gives the reserve prices. 
 * @author andrew
 *
 */
public class SMRAReportMessage extends GameReportMessage {

  private Map<ITradeable, Double> reserve; 
  
  /**
   * void kryo. 
   */
  public SMRAReportMessage() {
    this.reserve = null; 
  }

  /**
   * SMRA report message is initialized with reserve prices.
   * @param reserve
   */
  public SMRAReportMessage(Map<ITradeable, Double> reserve) {
    this.reserve = reserve; 
  }
  
  /**
   * Gets the reserve prices per good.
   * @return
   */
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