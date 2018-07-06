package brown.platform.server.library;

import java.util.List;

import brown.auction.preset.AbsMarketPreset;
import brown.platform.server.ISimulMarkets;

/**
 * SimulMarkets specifies a series of auctions
 * that may be run simultaneously.
 * @author acoggins
 *
 */
public class SimulMarkets implements ISimulMarkets {

  private final List<AbsMarketPreset> markets;
  
  /**
   * specifies markets to be run simultaneously.
   * @param markets
   */
  public SimulMarkets(List<AbsMarketPreset> markets){
    this.markets = markets;
  }
  
  /**
   * getter for all markets in this simultaneous set of markets.
   */
  public List<AbsMarketPreset> getMarkets() {
    return this.markets;
  }
}
