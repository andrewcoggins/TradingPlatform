package brown.platform.information;

import brown.auction.marketstate.IMarketPublicState;

public interface IWhiteboard {
  
  public void postInnerInformation(Integer marketID, Integer agentID, IMarketPublicState marketPublicState); 
  
  public void postOuterInformation(Integer marketID, IMarketPublicState marketPublicState); 

// TODO: include bids and trades in MarketPublicState
//  public void postBids(Integer marketID, List<IBid> bids); 
//  
//  public void postTrades(Integer marketID, List<ITransaction> completedTransactions); 
  
  public IMarketPublicState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep); 
  
  public IMarketPublicState getOuterInformation(Integer marketID); 
  
  
  
  public void clear(); 
  
}
