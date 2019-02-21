package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.library.TradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.IOrder;
import brown.platform.market.IMarketRules;
import brown.platform.tradeable.ITradeable;

/**
 * market manager keeps track of all open and closed markets within
 * a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {

  public void createSimultaneousMarket(List<IMarketRules> s, List<Map<String, Integer>> marketTradeables, Map<String, List<ITradeable>> allTradeables);

  public void lock();
  
  public Map<Integer, List<IOrder>> runSimultaneousMarket(); 
  
  public Map<Integer, TradeRequestMessage> giveTradeRequests(); 
  
  // This should be an ITradeMessage
  public void handleTradeMessage(TradeMessage message); 
  
  
}