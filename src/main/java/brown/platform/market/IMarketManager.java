package brown.platform.market;

import java.util.Collection;
import java.util.List;

import brown.auction.preset.AbsMarketRules;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Ledger;
import brown.platform.market.library.Market;

/**
 * market manager keeps track of all open and closed markets within
 * a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {
  
  /**
   * Closes a market and tells it to convert if applicable
   * @param server
   * @param ID
   * @param closingState
   */
  public void close(Integer ID);

  /**
   * Opens a market
   * @param market
   * @return
   */
  public boolean open(AbsMarketRules rules, Integer marketID, List<ITradeable> tradeables, List<Integer> agents);


  /**
   * Gets the ledger for this market ID
   * @param ID
   * @return
   */
  Ledger getLedger(Integer ID);

  /**
   * Gets the market for this ID
   * @param ID
   * @return
   */
  Market getMarket(Integer ID);

  /**
   * Gets all of the auctions
   * @return
   */
  Collection<Market> getAuctions();

  /**
   * updates a market with previous state information.
   * @param marketID
   */
  void update(Integer marketID);
  
  /**
   * boolean for whether any markets are currently open
   * within the simulation.
   * @return
   */
  boolean anyMarketsOpen();

  /**
   * completely resets between simulation- 
   * erases all markets stored within market manager. 
   */
  void reset();

  void lock();

}