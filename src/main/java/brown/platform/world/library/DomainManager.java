package brown.platform.world.library;

import brown.mechanism.tradeable.ITradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.IDomainManager;
import brown.auction.value.manager.IValuationManager;

import java.util.LinkedList;
import java.util.List;

public class DomainManager implements IDomainManager {

    private List<Domain> allDomains;

    /**
     * domainManager constructor instantializes  and stores all Domains
     */
    public DomainManager() {
        this.allDomains = new LinkedList<>();
    }

    public void createDomain(ITradeableManager manager, IValuationManager valuation, IAccountManager acctManager) {
        this.allDomains.add(new Domain(manager, valuation, acctManager));
    }

    public List<Domain> getAllDomains() {
        return this.allDomains;
    }

}
