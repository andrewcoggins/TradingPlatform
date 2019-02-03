package brown.platform.managers.library;

import brown.logging.library.PlatformLogging;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITradeableManager;
import brown.platform.managers.IValuationManager;
import brown.platform.world.IDomain;
import brown.platform.world.library.Domain;


public class DomainManager implements IDomainManager {

    private IDomain domain;
    private boolean lock;

    /**
     * domainManager constructor instantiates and stores Domain
     */
    public DomainManager() {
        this.lock = false;
    }

    public void createDomain(ITradeableManager manager, IValuationManager valuation, IAccountManager acctManager,
                             IEndowmentManager endowmentManager) {
        if (!this.lock){
            this.domain = new Domain(manager, valuation, acctManager, endowmentManager);
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: domain manager locked.");
        }
    }

    public IDomain getDomain() {
        return this.domain;
    }

}
