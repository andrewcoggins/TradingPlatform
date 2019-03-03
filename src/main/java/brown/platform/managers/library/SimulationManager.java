package brown.platform.managers.library;

import java.util.LinkedList;
import java.util.List;

import brown.communication.messageserver.IMessageServer;
import brown.communication.messageserver.library.MessageServer;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ISimulationManager;
import brown.platform.managers.IWorldManager;
import brown.platform.simulation.ISimulation;
import brown.platform.simulation.library.Simulation;
import brown.system.setup.library.SimpleSetup;

public class SimulationManager implements ISimulationManager {

    private List<ISimulation> simulations;
    private List<Integer> numSimulationRuns;  
    private boolean lock;
    private IMessageServer messageServer; 

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
    
    
    public void startMessageServer() {
      this.messageServer = new MessageServer(2121, new SimpleSetup());
    }
    // handle information requests
    
    // handle trade messages
    
    public void runSimulation(int waitTime, int numRuns) throws InterruptedException {
      Thread.sleep(waitTime * 1000);
      // send registration, register agents
      
      // start up the message server
      
      for (int i = 0; i < numRuns; i++) {

        // send valuations
    	  
    	// for simulmarket: markets
    	
    	// run simultaneous markets
    	  
    	// update accounts 
      }
    }
    
}
