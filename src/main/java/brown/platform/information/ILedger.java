package brown.platform.information;

import java.util.List;
import java.util.Map;

import brown.communication.messages.ITradeMessage;
import brown.platform.accounting.ITransaction;

public interface ILedger {

  public void postBid(ITradeMessage aBid, Integer timeStep); 
  
  public void postRejectedBid(ITradeMessage aBid, Integer timeStep); 
  
  public void postTransaction(Integer marketID, ITransaction transaction); 
  
  /**
   * get accepted bids for a market. Map is from market timestep to list of bids accepted at that timestep.
   * @param marketID
   * @return
   */
  public Map<Integer, List<ITradeMessage>> getAcceptedBids(Integer marketID); 
  
  /**
   * get rejected bids for a market. Map is from market timestep to list of bids accepted at that timestep.
   * @param marketID
   * @return
   */
  public Map<Integer, List<ITradeMessage>> getRejectedBids(Integer marketID); 
  
  /**
   * get list of completed transactions within a closed market. 
   * @param marketID
   * @return
   */
  public List<ITransaction> getMarketTransactions(Integer marketID); 
}

