package brown.platform.world.library;

import brown.platform.world.IDomain;
import brown.platform.whiteboard.IWhiteboard;
import brown.platform.market.IMarketBlock;
import brown.platform.world.IWorld;
import brown.platform.world.IWorldManager;

import java.util.LinkedList;
import java.util.List;

public class WorldManager implements IWorldManager {

    private List<IWorld> worlds;

    /**
     * worldManager constructor instantializes  and stores all Domains
     */
    public WorldManager() {
        this.worlds = new LinkedList<>();
    }

    public void createWorld(IDomain domain, List<IMarketBlock> markets, IWhiteboard whiteboard) {
        this.worlds.add(new World(domain, markets, whiteboard));
    }

    public List<IWorld> getWorlds() {
        return this.worlds;
    }
}
