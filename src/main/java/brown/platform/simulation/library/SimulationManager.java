package brown.platform.simulation.library;

import brown.logging.library.PlatformLogging;
import brown.platform.simulation.ISimulation;
import brown.platform.simulation.ISimulationManager;

import java.util.LinkedList;
import java.util.List;

public class SimulationManager implements ISimulationManager {

    private List<ISimulation> simulations;
    private boolean lock;

    public SimulationManager() {
        this.simulations = new LinkedList<>();
        this.lock = false;
    }

    public void createSimulation(ISimulation simulation) {
        if (!this.lock) {
            this.simulations.add(simulation);
        } else {
            PlatformLogging.log("Creation denied: simulation manager locked.");
        }
    }

    public void lock() {
        this.lock = true;
    }

    public void runSimulation(int numRuns) {

    }
}
