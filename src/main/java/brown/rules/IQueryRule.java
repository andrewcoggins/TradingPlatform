package brown.rules;

import brown.accounting.library.Ledger;
import brown.market.marketstate.IMarketState;

public interface IQueryRule {

  public void makeChannel(IMarketState state, Ledger ledger);

  public void reset();
  
}
