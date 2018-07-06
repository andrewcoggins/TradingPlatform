package brown.auction.rules.library;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.mechanism.bid.library.TwoSidedBid;
import brown.mechanism.bidbundle.library.BundleType;
import brown.platform.messages.library.TradeMessage;

/**
 * Activity rule for call markets. Just assures that a twosided bid bundle
 * is being sent. 
 * @author kerry
 *
 */
public class CallMarketActivity implements IActivityRule {
  
  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    // in the future might want to have this handle some risk limits or something
    boolean acceptable = false;
    if (aBid.Bundle.getType() == BundleType.CANCEL){
      acceptable = true;
    }
    if (aBid.Bundle.getType() == BundleType.TWOSIDED){
      TwoSidedBid bid = (TwoSidedBid) aBid.Bundle.getBids();
      if (bid.price > 0 && bid.quantity > 0){
        acceptable = true;
      }
    }
    state.setAcceptable(acceptable);
  }

  @Override
  public void setReserves(IMarketState state) {
    //noop
  }

  @Override
  public void reset() {
    //noop  
  }
}
