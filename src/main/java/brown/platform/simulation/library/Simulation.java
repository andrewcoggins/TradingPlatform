package brown.platform.simulation.library;

import brown.platform.managers.IWorldManager;
import brown.platform.simulation.ISimulation;

public class Simulation implements ISimulation {

    private IWorldManager worldManager;

    public Simulation(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @Override
    public IWorldManager getWorldManager() {
      return this.worldManager;
    }

}
