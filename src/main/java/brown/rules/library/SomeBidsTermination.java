package brown.rules.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.IInnerTC;

/**
 * Inner Termination condition- 
 * If any (valid) bids are received by the market, 
 * the market is not terminated. 
 * If there are no bids, the market terminates.
 * useful for ascending (English) auctions.
 * @author acoggins
 *
 */
public class SomeBidsTermination implements IInnerTC {

  @Override
  public void innerTerminated(IMarketState state) {
    if (state.getBids().isEmpty()) {
      state.setInnerOver(true);
    }
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }
  
}