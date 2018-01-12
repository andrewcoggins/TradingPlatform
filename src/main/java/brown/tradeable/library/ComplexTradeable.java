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
  
}