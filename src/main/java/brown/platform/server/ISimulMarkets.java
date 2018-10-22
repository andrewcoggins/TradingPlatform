package brown.platform.server;

import java.util.List;

import brown.auction.preset.AbsMarketRules;

/**
 * Interface for Simulmarkets, which are markets that are open simultaneously in
 * a simulation.
 * @author acoggins
 *
 */
public interface ISimulMarkets {
   
  /**
   * Gets all of the markets.
   * @return A list of AbsMarketPresets, which specify the rules for
   * all the markets.
   */
  List<AbsMarketRules> getMarkets();
}
