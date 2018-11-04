package brown.platform.simulation;

public interface ISimulationManager {

    void createSimulation (ISimulation simulation);

    void lock();

    void runSimulation(int numRuns);

}
