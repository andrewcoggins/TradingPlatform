package brown.platform.messages;

import java.util.Map;

import brown.mechanism.tradeable.ITradeable;

/**
 * A Combinatorial Clock Report provides a game report for a combinatorial clock auction.
 * @author kerry
 *
 */
public class CombinatorialClockReport  extends GameReportMessage {
  public Map<ITradeable, Double> winnings;
  
  /**
   * void kryo
   */
  public CombinatorialClockReport() {
    this.winnings = null;
  }
  
  /**
   * A Combinatorial clock report is initialized with price paid per tradeable won.
   * @param winnings: a map from won tradeables to the price paid per tradeable. 
   */
  public CombinatorialClockReport(Map<ITradeable, Double> winnings) {
    this.winnings = winnings;
  }  

  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    return this;
  }

}
