package brown.platform.managers.library;

import brown.logging.library.PlatformLogging;
import brown.platform.domain.Domain;
import brown.platform.domain.IDomain;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;

/**
 * Domain Manager creates and stores domains. 
 * @author andrewcoggins
 *
 */
public class DomainManager implements IDomainManager {

    private IDomain domain;
    private boolean lock;

    /**
     * domainManager constructor instantiates and stores Domain
     */
    public DomainManager() {
        this.lock = false;
    }

    public void createDomain(IItemManager manager, IValuationManager valuation,
                             IEndowmentManager endowmentManager, IAccountManager acctManager) {
        if (!this.lock){
            this.domain = new Domain(manager, valuation, endowmentManager, acctManager);
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: domain manager locked.");
        }
    }

    public IDomain getDomain() {
        return this.domain;
    }

}
