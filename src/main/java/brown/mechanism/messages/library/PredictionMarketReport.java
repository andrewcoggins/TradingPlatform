package brown.mechanism.messages.library;

import java.util.Map;

/**
 * A Game Report Message for the prediction market game.
 * @author kerry
 *
 */
public class PredictionMarketReport extends GameReportMessage {
  private Boolean coin;
  
  /**
   * void kryo
   */
  public PredictionMarketReport() {
    this.coin = null;
  }
  
  /**
   * Prediction market report simply displays the true coin
   * with which the agent was endowed.
   * @param coin: true if heads
   */
  public PredictionMarketReport(Boolean coin) {
    this.coin = coin;
  }  

  /**
   * Gets the true coin given to the agent.
   * @return boolean giving whether the coin is heads or not
   */
  public Boolean getCoin() {
    return this.coin;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((coin == null) ? 0 : coin.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PredictionMarketReport other = (PredictionMarketReport) obj;
    if (coin == null) {
      if (other.coin != null)
        return false;
    } else if (!coin.equals(other.coin))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PredictionMarketReport [coin=" + coin + "]";
  }

  // this is already sanitized when generated
  public GameReportMessage sanitize(Integer agent, Map<Integer,Integer> privateToPublic){    
    return this;
    }
}
