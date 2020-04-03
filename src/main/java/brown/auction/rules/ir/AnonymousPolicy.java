package brown.auction.rules.ir;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.platform.utils.Utils;

public class AnonymousPolicy extends AbsRule implements IInformationRevelationPolicy {

  @Override
  public void updatePublicState(IMarketState state, IMarketPublicState publicState) {
    // TODO Auto-generated method stub
    publicState = Utils.toPublicState(state); 
    
  }


}
