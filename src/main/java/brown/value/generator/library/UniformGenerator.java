package brown.value.generator.library; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuable.library.Value;

public class UniformGenerator extends AbsValuationGenerator {

  @Override
  public Value makeValuation(ITradeable good) {
    // TODO Auto-generated method stub
    return new Value(0);
  }

  @Override
  public Value makeValuation(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return new Value(0);
  }


  
}