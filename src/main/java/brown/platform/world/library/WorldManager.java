package brown.platform.world.library;

import brown.logging.library.PlatformLogging;
import brown.platform.market.IMarketManager;
import brown.platform.world.IDomain;
import brown.platform.whiteboard.IWhiteboard;
import brown.platform.market.IMarketBlock;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorld;
import brown.platform.world.IWorldManager;

import java.util.List;

public class WorldManager implements IWorldManager {

    private IWorld world;
    private boolean lock;

    /**
     * worldManager constructor instantiates world
     */
    public WorldManager() {
        this.lock = false;
    }

    public void createWorld(IDomainManager domain, IMarketManager markets, IWhiteboard whiteboard) {
        if (!this.lock) {
            this.world = new World(domain, markets, whiteboard);
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: world manager locked.");
        }
    }

    public IWorld getWorld() {
        return this.world;
    }
}
