package brown.platform.world;

import brown.platform.market.IMarketManager;

public interface IWorldManager {

    void createWorld(IDomainManager domain, IMarketManager markets);

    IWorld getWorld();

}
