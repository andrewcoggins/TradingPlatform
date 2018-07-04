package brown.server;

import java.util.List;

import brown.market.preset.AbsMarketPreset;

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
  List<AbsMarketPreset> getMarkets();
}
