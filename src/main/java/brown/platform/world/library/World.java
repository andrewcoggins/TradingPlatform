package brown.platform.world.library;

import brown.platform.market.IMarketManager;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorld;


public class World implements IWorld {

    public final IDomainManager domain;
    public final IMarketManager market;

    public World(IDomainManager domain, IMarketManager market) {
        this.domain = domain;
        this.market = market;
    }
}
