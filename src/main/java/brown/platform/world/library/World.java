package brown.platform.world.library;

import java.util.List;
import brown.platform.market.IMarketBlock;
import brown.platform.world.IDomain;
import brown.platform.world.IWorld;
import brown.platform.whiteboard.IWhiteboard;


public class World implements IWorld {

    public final IDomain domain;
    public final List<IMarketBlock> market;
    public final IWhiteboard whiteboard;

    public World(IDomain domain, List<IMarketBlock> market, IWhiteboard whiteboard) {
        this.domain = domain;
        this.market = market;
        this.whiteboard = whiteboard;
    }
}
