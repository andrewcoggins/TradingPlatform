package brown.platform.world;

import brown.platform.market.IMarketManager;
import brown.platform.whiteboard.IWhiteboard;

public interface IWorldManager {

    void createWorld(IDomainManager domain, IMarketManager markets, IWhiteboard whiteboard);

    IWorld getWorld();

}
