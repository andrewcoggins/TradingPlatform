package brown.platform.managers;

import brown.platform.world.IWorld;

public interface IWorldManager {

    void createWorld(IDomainManager domain, IMarketManager markets);

    IWorld getWorld();

}
