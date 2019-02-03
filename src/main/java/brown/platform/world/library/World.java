package brown.platform.world.library;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;
import brown.platform.world.IWorld;


public class World implements IWorld {

    public final IDomainManager domain;
    public final IMarketManager market;

    public World(IDomainManager domain, IMarketManager market) {
        this.domain = domain;
        this.market = market;
    }
}
