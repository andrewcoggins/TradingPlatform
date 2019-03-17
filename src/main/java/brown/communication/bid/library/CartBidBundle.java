package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.ICart;
import brown.communication.bid.ICartBidBundle;

public class CartBidBundle extends AbsCartBidBundle implements ICartBidBundle {

  public CartBidBundle(Map<ICart, Double> bids) {
    super(bids); 
  }
  
}
