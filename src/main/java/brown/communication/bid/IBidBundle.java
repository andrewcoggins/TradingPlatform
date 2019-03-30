package brown.communication.bid;

import java.util.Map;

public interface IBidBundle extends IBid {

  public Map<ICart, Double> getBids(); 
  
}
