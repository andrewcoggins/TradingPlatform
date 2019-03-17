package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.ICart;
import brown.communication.bid.ICartBidBundle;

public abstract class AbsCartBidBundle implements ICartBidBundle {

  private Map<ICart, Double> bids; 
  
  public AbsCartBidBundle(Map<ICart, Double> bids) {
    this.bids = bids; 
  }
  
  public Map<ICart, Double> getCartBids() {
    return this.bids; 
  }
  
}
