package brown.platform.simulation.library;

import brown.platform.simulation.ISimulation;
import brown.platform.world.IWorldManager;

public class Simulation implements ISimulation {

    public final IWorldManager worldManager;

    public Simulation(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }
}
