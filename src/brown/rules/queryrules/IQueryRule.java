package brown.rules.queryrules;

import brown.accounting.Ledger;
import brown.market.marketstate.IMarketState;
public interface IQueryRule {

  public void makeChannel(IMarketState state, Ledger ledger);

}
