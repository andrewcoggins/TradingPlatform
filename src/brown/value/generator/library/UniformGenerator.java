package brown.value.generator.library; 

import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.generator.IValuationGenerator;
import brown.value.valuable.library.Value;

public class UniformGenerator implements IValuationGenerator {

  @Override
  public Value makeValuation(Tradeable good) {
    // TODO Auto-generated method stub
    return new Value(0);
  }

  @Override
  public Value makeValuation(Set<Tradeable> goods) {
    // TODO Auto-generated method stub
    return new Value(0);
  }


  
}