package brown.platform.simulator.library;

import java.util.LinkedList;
import java.util.List;

import brown.logging.library.PlatformLogging;
import brown.platform.simulator.ISimulation;
import brown.platform.simulator.ISimulationManager;
import brown.platform.world.IWorldManager;

public class SimulationManager implements ISimulationManager {

    private List<ISimulation> simulations;
    private List<Integer> numSimulationRuns;  
    private boolean lock;

    public SimulationManager() {
        this.simulations = new LinkedList<>();
        this.lock = false;
        this.numSimulationRuns = new LinkedList<Integer>(); 
    }

    public void createSimulation(int numSimulationRuns, IWorldManager worldManager) {
        if (!this.lock) {
            this.simulations.add(new Simulation(worldManager));
            this.numSimulationRuns.add(numSimulationRuns); 
        } else {
            PlatformLogging.log("Creation denied: simulation manager locked.");
        }
    }

    public void lock() {
        this.lock = true;
    }

    public void runSimulation(int waitTime, int numRuns) throws InterruptedException {
      Thread.sleep(waitTime * 1000);
      for (int i = 0; i < numRuns; i++) {
        // what needs to happen here? 
        
        // 
      }
    }
    
}
