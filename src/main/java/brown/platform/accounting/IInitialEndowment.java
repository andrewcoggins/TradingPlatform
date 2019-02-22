package brown.platform.accounting;

import java.util.List;
import java.util.Map;

import brown.platform.tradeable.ITradeable;

/**
 * IInitialEndowment- specifies an endowment of tradeables and money for the
 */
public interface IInitialEndowment {
  
  public double getMoney(); 
  
  public Map<String, List<ITradeable>> getGoods(); 
  
}
