package brown.platform.world.library;

import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.IDomainManager;
import brown.platform.world.IDomain;
import brown.auction.value.manager.IValuationManager;


public class DomainManager implements IDomainManager {

    private IDomain domain;
    private boolean lock;

    /**
     * domainManager constructor instantiates and stores Domain
     */
    public DomainManager() {
        this.lock = false;
    }

    public void createDomain(ITradeableManager manager, IValuationManager valuation, IAccountManager acctManager) {
        if (!this.lock){
            this.domain = new Domain(manager, valuation, acctManager);
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: domain manager locked.");
        }
    }

    public IDomain getDomain() {
        return this.domain;
    }

}
