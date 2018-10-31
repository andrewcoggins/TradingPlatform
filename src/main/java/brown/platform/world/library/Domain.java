package brown.platform.world.library;

import brown.auction.value.manager.IValuationManager;
import brown.mechanism.tradeable.ITradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.IDomain;

import java.util.List;

/**
 * a domain consists of Tradeables, an IValuationGenerator, and an AccountManager.
 */
public class Domain implements IDomain {

    public final ITradeableManager tManager;
    public final  IValuationManager valuation;
    public final IAccountManager acctManager;

    public Domain (ITradeableManager tManager, IValuationManager valuation, IAccountManager acctManager) {
        this.tManager = tManager;
        this.valuation = valuation;
        this.acctManager = acctManager;
    }
}
