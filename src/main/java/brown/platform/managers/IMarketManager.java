package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.market.IMarketRules;
import brown.platform.tradeable.ITradeable;

/**
 * market manager keeps track of all open and closed markets within
 * a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {

  public void createSimultaneousMarket(List<IMarketRules> s, List<List<String>> marketTradeables, Map<String, List<ITradeable>> allTradeables);

  public void lock();
  
  public void handleTradeMessage(ITradeMessage message); 
  
  public IInformationMessage handleInformationRequest(IInformationRequestMessage message); 
  
  // one sided order book. 
  public Map<Integer, List<IAccountUpdate>> runSimultaneousMarket(); 
  
  public ITradeRequestMessage giveTradeRequest(Integer marketID, Integer agentID); 
  
  // this could be asking for highest bidder stuff, reserve price.. maybe I should just return all the information on the particular market. 
  
  public void reset(); 
  
}