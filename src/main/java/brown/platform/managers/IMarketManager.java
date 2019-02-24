package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.accounting.IOrder;
import brown.platform.market.IMarketRules;
import brown.platform.tradeable.ITradeable;

/**
 * market manager keeps track of all open and closed markets within a simulation. 
 * @author andrew
 *
 */
public interface IMarketManager {

  // delete marketTradeables argument
  // rename s -> marketRules (in imp'n as well)
  // actually, you can rename allTradeables, marketTradeables :)
  public void createSimultaneousMarket(List<IMarketRules> s, List<Map<String, Integer>> marketTradeables, Map<String, List<ITradeable>> allTradeables);

  public void lock();
  
  public Map<Integer, List<IOrder>> runSimultaneousMarket(); 
  
  // rename give -> sendTradeRequestsToAgents ?
  public Map<Integer, ITradeRequestMessage> giveTradeRequests(Integer marketID); 
  
  public void handleTradeMessage(ITradeMessage message); 
  
  // this could be asking for highest bidder stuff, reserve price...
  // maybe I should just return all the information on the particular market. 
  public IInformationMessage handleInformationRequest(IInformationRequestMessage message); 
  
  public void reset(); 
  
  // so... I could have agents receive a channel, and then query for information from within that channel call... 
  // but other than that, there's no way to do this. Would querying for info from within a channel call be worth it? 
  // It would be 'pinging' so to speak and would add another layer. They could query reserve prices, market information,
  // valuation stuff, like the distribution. But only the querying related to the market is handled through the market manager

}
