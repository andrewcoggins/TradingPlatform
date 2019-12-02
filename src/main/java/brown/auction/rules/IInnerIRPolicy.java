package brown.auction.rules;

import brown.auction.marketstate.IMarketState;

public interface IInnerIRPolicy {

  public void updatePublicState(IMarketState state, IMarketState publicState); 
  
}
