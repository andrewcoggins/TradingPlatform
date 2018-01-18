package brown.value.amy;

import java.util.HashMap;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.generator.library.UniformValGenerator;
import brown.value.valuable.library.Value;

public abstract class AbsValuation {

  protected AbsValuationGenerator generator;
  protected AMYValuationRepn valuation;
  
  AbsValuation(Set<ITradeable> allGoods) {
    this.valuation = new HashMap<ITradeable, Value>();
    UniformValGenerator rg = new AbsValuationGenerator();
    for(ITradeable good : allGoods) {
      valMap.put(good, rg.makeValuation(item));
    }
  }
  
  
}
