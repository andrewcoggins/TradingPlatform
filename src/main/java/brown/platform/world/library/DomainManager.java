package brown.platform.world.library;

import brown.mechanism.tradeable.ITradeable;
import brown.auction.value.distribution.IValuationDistribution;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.IDomainManager;

import java.util.LinkedList;
import java.util.List;

public class DomainManager implements IDomainManager {

    private List<Domain> allDomains;

    /**
     * domainManager constructor instantializes allDomains
     */
    public DomainManager() {
        this.allDomains = new LinkedList<>();
    }

    public void createDomain(List<ITradeable> tradeables, IValuationDistribution valuation, IAccountManager acctManager) {
        this.allDomains.add(new Domain(tradeables, valuation, acctManager));
    }

    public List<Domain> getAllDomains() {
        return this.allDomains;
    }

}
