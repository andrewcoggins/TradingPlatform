package brown.server;

import java.util.List;

import brown.server.library.SimulMarkets;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;

/**
 * Interface for a simulation, which runs a sequence of 
 * auctions one time.
 * @author acoggins
 *
 */
public interface ISimulation {

  /**
   * Gets the sequence of auctions to be run.
   * @return a list of markets, some of which may be run simultaneously. 
   * Markets at the beginning of list are run before those at the end.
   */
  List<SimulMarkets> getSequence();

  /**
   * Gets valuation configuration info. 
   * @return a ValConfig.
   */
  ValConfig getValInfo();

  /**
   * Gets all tradeables being used in the auction.
   * @return
   */
  List<ITradeable> getTradeables();

  /**
   * Gets initial money endowed to agents. 
   * @return a double that represents initial endowment. 
   */
  double getInitialMonies();

  /**
   * Gets initial tradeables endowed to agents.
   * @return list of tradeables at the start of simulation. =
   */
  List<ITradeable> getInitialGoods();
}
