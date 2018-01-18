package brown.tradeable; 

import java.util.List;

import brown.tradeable.library.SimpleTradeable;

public interface ITradeable {
  
  public Integer getID();
  
  public Integer getCount();
  
  public TradeableType getType();
  
  public List<SimpleTradeable> flatten();
}