package brown.auction.marketstate.library;

import brown.auction.marketstate.IMarketPublicState;

public class MarketPublicState implements IMarketPublicState {
  
  private Integer marketID; 
  
  public MarketPublicState(Integer marketID) {
    this.marketID = marketID; 
  }
}
