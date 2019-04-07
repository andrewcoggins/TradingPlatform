package brown.auction.rules;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;

public interface IInnerIRPolicy {

  public void updatePublicState(IMarketState state, IMarketPublicState publicState); 
  
}
