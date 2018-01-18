package brown.rules.queryrules;

import brown.accounting.Ledger;
import brown.market.marketstate.ICompleteState;

public interface IQueryRule {

  public void makeChannel(ICompleteState state, Ledger ledger);
  
}
