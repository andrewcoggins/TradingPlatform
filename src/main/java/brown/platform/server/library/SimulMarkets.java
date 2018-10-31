package brown.platform.server.library;

import java.util.List;

import brown.auction.preset.AbsMarketRules;
import brown.platform.server.ISimulMarkets;

/**
 * SimulMarkets specifies a series of auctions
 * that may be run simultaneously.
 * @author acoggins
 *
 */
public class SimulMarkets implements ISimulMarkets {

  private final List<AbsMarketRules> markets;
  
  /**
   * specifies markets to be run simultaneously.
   * @param markets
   */
  public SimulMarkets(List<AbsMarketRules> markets){
    this.markets = markets;
  }
  
  /**
   * getter for all markets in this simultaneous set of markets.
   */
  public List<AbsMarketRules> getMarkets() {
    return this.markets;
  }
}
