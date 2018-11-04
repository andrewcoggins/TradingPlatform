package brown.platform.world.library;

import brown.auction.value.manager.IValuationManager;
import brown.mechanism.tradeable.ITradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.world.IDomain;


/**
 * a domain consists of Tradeables, an IValuationGenerator, and an AccountManager.
 */
public class Domain implements IDomain {

    public final ITradeableManager tManager;
    public final  IValuationManager valuation;
    public final IAccountManager acctManager;
    public final IEndowmentManager endowmentManager;

    public Domain (ITradeableManager tManager, IValuationManager valuation, IAccountManager acctManager,
                   IEndowmentManager endowmentManager) {
        this.tManager = tManager;
        this.valuation = valuation;
        this.acctManager = acctManager;
        this.endowmentManager = endowmentManager;
    }
}
