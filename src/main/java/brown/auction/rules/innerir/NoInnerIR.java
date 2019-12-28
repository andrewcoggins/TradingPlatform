package brown.auction.rules.innerir;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IInnerIRPolicy;

public class NoInnerIR extends AbsRule implements IInnerIRPolicy {

  @Override
  public void updatePublicState(IMarketState state,
      IMarketState publicState) {
    
  }

}
