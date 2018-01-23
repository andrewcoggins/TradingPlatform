package brown.tradeable; 

import java.util.List;

import brown.tradeable.library.SimpleTradeable;
import brown.tradeable.library.TradeableType;

public interface ITradeable {
  
  public Integer getID();
  
  public Integer getCount();
  
  public TradeableType getType();
  
  public List<SimpleTradeable> flatten();
}