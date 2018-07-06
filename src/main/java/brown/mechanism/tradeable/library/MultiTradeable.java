package brown.mechanism.tradeable.library;

import java.util.LinkedList;
import java.util.List;

/**
 * This tradeable is comprised of multiple copies.
 * @author amy
 */
public class MultiTradeable extends AbsTradeable {
  
  /**
   * For Kryo
   * DO NOT USE
   */
  public MultiTradeable() {
    super();
  }
  
  public MultiTradeable(Integer ID, Integer COUNT) {
    super(ID, COUNT, TradeableType.Multi);
  }
  
  @Override
  public List<SimpleTradeable> flatten(){
    LinkedList<SimpleTradeable> toReturn = new LinkedList<SimpleTradeable>();
    for (int i=0; i < this.COUNT;i++){
      toReturn.add(new SimpleTradeable(this.ID)); 
    }
    return toReturn;
  }  
}
