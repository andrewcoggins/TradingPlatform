package brown.platform.simulator.library;

import brown.platform.simulator.ISimulation;
import brown.platform.world.IWorldManager;

public class Simulation implements ISimulation {

    public final IWorldManager worldManager;

    public Simulation(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }
    
}
