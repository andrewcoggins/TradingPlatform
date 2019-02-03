package brown.platform.simulator;

import brown.platform.world.IWorldManager;

public interface ISimulationManager {

    void createSimulation (int numSimulationRuns, IWorldManager worldManager);

    void lock();

    void runSimulation(int waitTime, int numRuns) throws InterruptedException;

}
