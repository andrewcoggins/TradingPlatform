package brown.platform.world;

import brown.mechanism.tradeable.ITradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.auction.value.manager.IValuationManager;
import brown.platform.accounting.IEndowmentManager;

/**
 * A domain manager creates domains.
 */
public interface IDomainManager {

    /**
     * creates a domain
     * @param manager domain tradeable manager
     * @param valuation domain valuation distribution
     * @param acctManager domain accountManager
     */
    void createDomain(ITradeableManager manager, IValuationManager valuation, IAccountManager acctManager,
                      IEndowmentManager endowmentManager);

    IDomain getDomain();

}
