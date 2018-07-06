package brown.mechanism.tradeable.library;

import java.util.LinkedList;
import java.util.List;

/**
* A simple tradeable is a single tradeable.
* @author amy
*/
//AMY: would prefer for Simple to extend Multi
//Fix later, if TradeableTypes go away
public class SimpleTradeable extends AbsTradeable {   

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