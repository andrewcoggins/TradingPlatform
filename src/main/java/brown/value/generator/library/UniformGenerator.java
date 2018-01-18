package brown.value.generator.library; 

import java.util.Set;

import brown.tradeable.library.MultiTradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuable.library.Value;

public class UniformGenerator extends AbsValuationGenerator {

  @Override
  public Value makeValuation(MultiTradeable good) {
    // TODO Auto-generated method stub
    return new Value(0);
  }

  @Override
  public Value makeValuation(Set<MultiTradeable> goods) {
    // TODO Auto-generated method stub
    return new Value(0);
  }


  
}