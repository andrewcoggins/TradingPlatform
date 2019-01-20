package brown.platform.market;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Ledger;
import brown.platform.market.library.AbsMarketRules;
import brown.platform.market.library.Market;

/**
 * market manager keeps track of all open and closed markets within
 * a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {


  void createSimultaneousMarket(List<IMarketRules> s, List<Map<String, Integer>> marketTradeables, Map<String, List<ITradeable>> allTradeables);


  void lock();

}