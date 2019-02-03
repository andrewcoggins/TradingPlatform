package brown.platform.managers;

public interface ISimulationManager {

    void createSimulation (int numSimulationRuns, IWorldManager worldManager);

    void lock();

    void runSimulation(int waitTime, int numRuns) throws InterruptedException;

}
