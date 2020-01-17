package brown.communication.bid;

import java.util.Map;

import brown.platform.item.ICart;

public interface IBidBundle {

  public Map<ICart, Double> getBids(); 
  
}
