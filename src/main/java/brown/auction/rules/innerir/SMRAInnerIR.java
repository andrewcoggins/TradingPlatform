package brown.auction.rules.innerir;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IInnerIRPolicy;

public class SMRAInnerIR implements IInnerIRPolicy {

  @Override
  public void updatePublicState(IMarketState state,
      IMarketPublicState publicState) {
    // just add the necessary prices. 
    publicState.setReserves(state.getReserves());
    publicState.setAllocation(state.getAllocation());
    System.out.println(state.getTradeHistory());
  }

}
