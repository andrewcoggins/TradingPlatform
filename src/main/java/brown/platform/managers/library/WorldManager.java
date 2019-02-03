package brown.platform.managers.library;

import brown.logging.library.PlatformLogging;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.IWorldManager;
import brown.platform.world.IWorld;
import brown.platform.world.library.World;


public class WorldManager implements IWorldManager {

    private IWorld world;
    private boolean lock;

    /**
     * worldManager constructor instantiates world
     */
    public WorldManager() {
        this.lock = false;
    }

    public void createWorld(IDomainManager domain, IMarketManager markets) {
        if (!this.lock) {
            this.world = new World(domain, markets);
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: world manager locked.");
        }
    }

    public IWorld getWorld() {
        return this.world;
    }
}
