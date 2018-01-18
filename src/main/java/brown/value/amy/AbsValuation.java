package brown.value.amy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuable.library.Value;

public abstract class AbsValuation {

  protected AbsValuationGenerator generator;
  protected AMYValuationRepn valuation;
  
  AbsValuation(Set<ITradeable> allGoods, AbsValuationGenerator generator) {
    Map<ITradeable, Value> valMap = new HashMap<ITradeable, Value>();
    for(ITradeable good : allGoods) {
      valMap.put(good, generator.makeValuation(good));
    }
    this.valuation = new AMYValuationRepn(valMap);
  }
  
}
