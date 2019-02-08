package brown.platform.tradeable.library;

import java.util.LinkedList;
import java.util.List;

import brown.platform.tradeable.ITradeable;

/**
* A simple tradeable is a single tradeable.
* @author amy
*/
public class SimpleTradeable extends AbsTradeable implements ITradeable {   

  /**
   * For Kryo
   * DO NOT USE
   */
  public SimpleTradeable() {
    super();
  }
  
  public SimpleTradeable(Integer ID) {
    super(ID, 1, TradeableType.Simple);
  }
  
  @Override
  public List<SimpleTradeable> flatten(){
    LinkedList<SimpleTradeable> toReturn = new LinkedList<SimpleTradeable>();
    toReturn.add(this);
    return toReturn;
  }
  
}