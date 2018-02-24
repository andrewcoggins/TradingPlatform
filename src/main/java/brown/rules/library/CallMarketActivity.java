package brown.rules.library;

import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;

public class CallMarketActivity implements IActivityRule {
  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    // in the future might want to have this handle some risk limits or something
    boolean acceptable = true;
    if (aBid.Bundle.getType() != BundleType.TWOSIDED){
      acceptable = false;
    } 
    TwoSidedBid bid = (TwoSidedBid) aBid.Bundle.getBids();
    if (bid.price < 0){
      acceptable = false;
    }
    state.setAcceptable(acceptable);
  }

  @Override
  public void setReserves() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub    
  }

}