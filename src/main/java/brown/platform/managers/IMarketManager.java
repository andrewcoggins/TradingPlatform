package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarket;
import brown.platform.tradeable.ITradeable;

/**
 * market manager keeps track of all open and closed markets within a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {

  public void createSimultaneousMarket(List<IFlexibleRules> s, List<List<String>> marketTradeables, Map<String, List<ITradeable>> allTradeables);

  public void lock();
  
  public Integer getNumMarketBlocks(); 
  
  public void openMarkets(int index); 
  
  public IStatusMessage handleTradeMessage(ITradeMessage message); 
  
  public List<Integer> getActiveMarketIDs(); 
  
  public IMarket getActiveMarket(Integer marketID); 
  
  public List<ITradeRequestMessage> updateMarket(Integer marketID, List<Integer> agents); 
  
  public Map<Integer, IInformationMessage> constructInformationMessages(Integer marketID, List<Integer> agentIDs); 
  
  public List<IAccountUpdate> finishMarket(Integer marketID); 
  
  public void finalizeMarket(Integer marketID); 
  
  public boolean marketOpen(Integer marketID); 
  
  public boolean anyMarketsOpen(); 
  
  public void reset(); 
  
}
