package brown.rules.queryrules;

import brown.assets.accounting.Ledger;
import brown.marketinternalstates.MarketInternalState;
public interface QueryRule {

  public void makeChannel(MarketInternalState state, Ledger ledger);

}
