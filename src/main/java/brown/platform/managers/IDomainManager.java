package brown.platform.managers;

import brown.platform.domain.IDomain;

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
