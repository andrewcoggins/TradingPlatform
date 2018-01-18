package brown.rules.queryrules;

import brown.accounting.library.Ledger;
import brown.market.marketstate.ICompleteState;

public interface IQueryRule {

  public void makeChannel(ICompleteState state, Ledger ledger);
  
}
