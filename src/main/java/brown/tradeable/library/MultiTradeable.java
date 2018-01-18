package brown.tradeable.library;

import brown.tradeable.TradeableType;

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
  
}
