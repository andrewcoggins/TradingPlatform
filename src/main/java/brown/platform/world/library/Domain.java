package brown.platform.world.library;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITradeableManager;
import brown.platform.managers.IValuationManager;
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
