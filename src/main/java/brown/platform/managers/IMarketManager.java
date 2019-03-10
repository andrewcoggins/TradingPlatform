package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.market.IMarket;
import brown.platform.market.IMarketRules;
import brown.platform.tradeable.ITradeable;

/**
 * market manager keeps track of all open and closed markets within a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {

  public void createSimultaneousMarket(List<IMarketRules> s, List<List<String>> marketTradeables, Map<String, List<ITradeable>> allTradeables);

  public void lock();
  
  public void handleTradeMessage(ITradeMessage message); 
  
  // cutting this out.
  //public IInformationMessage handleInformationRequest(IInformationRequestMessage message); 
  
  public Integer getNumMarketBlocks(); 
  
  public List<IMarket> getOpenMarkets(); 
  
  public List<IAccountUpdate> finishMarket(Integer marketID); 
  
  public List<ITradeRequestMessage> updateMarket(Integer marketID, List<Integer> agents); 
  
  public Map<Integer, IInformationMessage> constructInformationMessages(Integer marketID); 
  
  public boolean anyMarketsOpen(); 
  
  public void reset(); 
  
  // so... I could have agents receive a channel, and then query for information from within that channel call... 
  // but other than that, there's no way to do this. Would querying for info from within a channel call be worth it? 
  // It would be 'pinging' so to speak and would add another layer. They could query reserve prices, market information,
  // valuation stuff, like the distribution. But only the querying related to the market is handled through the market manager
  
}

  
