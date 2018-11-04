package brown.platform.world.library;

import brown.platform.market.IMarketManager;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorld;
import brown.platform.whiteboard.IWhiteboard;


public class World implements IWorld {

    public final IDomainManager domain;
    public final IMarketManager market;
    public final IWhiteboard whiteboard;

    public World(IDomainManager domain, IMarketManager market, IWhiteboard whiteboard) {
        this.domain = domain;
        this.market = market;
        this.whiteboard = whiteboard;
    }
}
