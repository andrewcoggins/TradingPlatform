package brown.platform.information;

import brown.auction.marketstate.IMarketState;

public interface IWhiteboard {
  
  public void postInnerInformation(Integer marketID, Integer agentID, IMarketState marketPublicState); 
  
  public void postOuterInformation(Integer marketID, IMarketState marketPublicState); 

// TODO: include bids and trades in MarketPublicState
//  public void postBids(Integer marketID, List<IBid> bids); 
//  
//  public void postTrades(Integer marketID, List<ITransaction> completedTransactions); 
  
  public IMarketState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep); 
  
  public IMarketState getOuterInformation(Integer marketID); 
  
  
  
  public void clear(); 
  
}
