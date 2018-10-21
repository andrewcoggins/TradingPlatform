package brown.platform.world;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.library.Domain;

import java.util.List;

/**
 * A domain manager creates domains.
 */
public interface IDomainManager {

    /**
     * creates a domain
     * @param tradeables domain tradeables
     * @param valuation domain valuation distribution
     * @param acctManager domain accountManager
     */
    void createDomain(List<ITradeable> tradeables, IValuationDistribution valuation, IAccountManager acctManager);

    List<Domain> getAllDomains();

}
