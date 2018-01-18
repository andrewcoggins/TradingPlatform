package brown.value.amy;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface AMYIValuation {

  public AMYValuationRepn makeValuation(Set<ITradeable> allGoods);
  public AMYValuationRepn getValuation();
  
  public Value makeOneValue(ITradeable good);
  public Value getOneValue(ITradeable good); // sampling method
  
}