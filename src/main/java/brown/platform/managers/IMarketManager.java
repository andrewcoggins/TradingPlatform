package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IStatusMessage;
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
  
  public IStatusMessage handleTradeMessage(ITradeMessage message); 
  
  public void openMarkets(int index); 
  // cutting this out.
  //public IInformationMessage handleInformationRequest(IInformationRequestMessage message); 
  
  public Integer getNumMarketBlocks(); 
  
  public List<IMarket> getActiveMarkets(); 
  
  public List<IAccountUpdate> finishMarket(Integer marketID); 
  
  public List<ITradeRequestMessage> updateMarket(Integer marketID, List<Integer> agents); 
  
  public Map<Integer, IInformationMessage> constructInformationMessages(Integer marketID); 
  
  public void finalizeMarket(Integer marketID); 
  
  public boolean anyMarketsOpen(); 
  
  public void reset(); 
  
}
