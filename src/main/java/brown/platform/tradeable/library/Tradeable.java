
package brown.platform.tradeable.library;

import brown.platform.tradeable.ITradeable;

/**
* A simple tradeable is a single tradeable.
* @author amy
*/
public class Tradeable extends AbsTradeable implements ITradeable {   

  /**
   * For Kryo
   * DO NOT USE
   */
  public Tradeable() {
    super();
  }
  
  public Tradeable(Integer ID, String name) {
    super(ID, name, TradeableType.Simple);
  }
  
}