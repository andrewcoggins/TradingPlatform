package brown.platform.simulation.library;

import brown.platform.simulation.ISimulation;
import brown.platform.simulation.ISimulationManager;

public class SimulationManager implements ISimulationManager {

    private final ISimulation simulation;

    public SimulationManager(ISimulation simulation) {
        this.simulation = simulation;
    }

    public void runSimulation(int numRuns) {

    }
}
