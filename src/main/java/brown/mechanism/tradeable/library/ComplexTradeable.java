package brown.mechanism.tradeable.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;

/**
* A complex tradeable is a set of tradeables.
* COUNT must be 1!
* @author amy
*/
public class ComplexTradeable extends AbsTradeable { 
  
  public final int ID; 
  public final Set<ITradeable> GOODS;
  /**
   * For Kryo
   * DO NOT USE
   */
  public ComplexTradeable() {
    super();
    this.GOODS = null;
    this.ID = 0; 
  }
  
  public ComplexTradeable(Integer ID, Set<ITradeable> GOODS) {
    super(ID, 1, TradeableType.Complex);
    this.GOODS = GOODS;
    this.ID = ID; 
  }

  @Override
  public List<SimpleTradeable> flatten() {
    LinkedList<SimpleTradeable> toReturn = new LinkedList<SimpleTradeable>();
    for (ITradeable t: this.GOODS){
      toReturn.addAll(t.flatten());
    }
    return toReturn;
  }
  
}
