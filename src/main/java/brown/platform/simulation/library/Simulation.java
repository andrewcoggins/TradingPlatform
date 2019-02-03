package brown.platform.simulation.library;

import brown.platform.managers.IWorldManager;
import brown.platform.simulation.ISimulation;

public class Simulation implements ISimulation {

    public final IWorldManager worldManager;

    public Simulation(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }
    
}
