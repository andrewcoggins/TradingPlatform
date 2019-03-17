package brown.communication.bid;

import java.util.Map;

public interface ICartBidBundle extends IBid {

  public Map<ICart, Double> getCartBids(); 
  
}
