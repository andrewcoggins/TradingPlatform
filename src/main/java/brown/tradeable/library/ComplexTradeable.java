package brown.tradeable.library;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.TradeableType;

/**
* A complex tradeable is a set of tradeables.
* COUNT must be 1!
* @author amy
*/
public class ComplexTradeable extends AbsTradeable { 
  
  public final Set<ITradeable> GOODS;
  
  /**
   * For Kryo
   * DO NOT USE
   */
  public ComplexTradeable() {
    super();
    this.GOODS = null;
  }
  
  public ComplexTradeable(Integer ID, Set<ITradeable> GOODS) {
    super(ID, 1, TradeableType.Complex);
    this.GOODS = GOODS;
  }

  @Override
  public String toString() {
    return "ComplexTradeable [GOODS=" + GOODS + ", ID=" + ID + ", COUNT="
        + COUNT + ", TYPE=" + TYPE + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((GOODS == null) ? 0 : GOODS.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ComplexTradeable other = (ComplexTradeable) obj;
    if (GOODS == null) {
      if (other.GOODS != null)
        return false;
    } else if (!GOODS.equals(other.GOODS))
      return false;
    return true;
  }

}