package brown.tradeable.library;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.TradeableType;

public class ComplexTradeable implements ITradeable { 
  
  public final Integer ID; 
  public final Set<Tradeable> GOODS;
  public final TradeableType TYPE; 
  
  public ComplexTradeable(Integer ID, Set<Tradeable> goods) {
   this.ID = ID; 
   this.GOODS = goods;
   this.TYPE = TradeableType.Complex;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((GOODS == null) ? 0 : GOODS.hashCode());
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((TYPE == null) ? 0 : TYPE.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ComplexTradeable other = (ComplexTradeable) obj;
    if (GOODS == null) {
      if (other.GOODS != null)
        return false;
    } else if (!GOODS.equals(other.GOODS))
      return false;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (TYPE != other.TYPE)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ComplexTradeable [ID=" + ID + ", GOODS=" + GOODS + ", TYPE=" + TYPE
        + "]";
  }
  
  
  
}