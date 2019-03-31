package brown.communication.bid;

import java.util.Map;

import brown.platform.item.ICart;

public interface IBidBundle extends IBid {

  public Map<ICart, Double> getBids(); 
  
}
