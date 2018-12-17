package brown.platform.simulator.library;

import brown.logging.library.PlatformLogging;
import brown.platform.simulator.ISimulation;
import brown.platform.simulator.ISimulationManager;
import brown.platform.world.IWorldManager;

import java.util.LinkedList;
import java.util.List;

public class SimulationManager implements ISimulationManager {

    private List<ISimulation> simulations;
    private boolean lock;

    public SimulationManager() {
        this.simulations = new LinkedList<>();
        this.lock = false;
    }

    public void createSimulation(IWorldManager worldManager) {
        if (!this.lock) {
            this.simulations.add(new Simulation(worldManager));
        } else {
            PlatformLogging.log("Creation denied: simulation manager locked.");
        }
    }

    public void lock() {
        this.lock = true;
    }

    public void runSimulation(int waitTime, int numRuns) {

    }
    
}
