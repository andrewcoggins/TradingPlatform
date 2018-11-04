package brown.platform.simulation;

import brown.platform.world.IWorldManager;

public interface ISimulationManager {

    void createSimulation (IWorldManager worldManager);

    void lock();

    void runSimulation(int waitTime, int numRuns);

}
