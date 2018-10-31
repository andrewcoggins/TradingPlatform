package brown.platform.world;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.ITradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.library.Domain;
import brown.auction.value.manager.IValuationManager;
import java.util.List;

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
    void createDomain(ITradeableManager manager, IValuationManager valuation, IAccountManager acctManager);

    List<Domain> getAllDomains();

}
