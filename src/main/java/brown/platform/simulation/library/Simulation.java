package brown.platform.simulation.library;

import brown.platform.managers.IWorldManager;
import brown.platform.simulation.ISimulation;

/**
 * Simulation encompasses a world manager and encapsulates a scenario run 
 * by the platform
 * @author andrewcoggins
 *
 */
public class Simulation implements ISimulation {

  private IWorldManager worldManager;
  private int groupSize;

  /**
   * A Simulation consists only of a IWorldManager.
   * 
   * @param worldManager
   */
  public Simulation(int groupSize, IWorldManager worldManager) {
    this.groupSize = groupSize;
    this.worldManager = worldManager;
  }

    @Override
    public IWorldManager getWorldManager() {
      return this.worldManager;
    }
    
    @Override
    public int getGroupSize() {
      return this.groupSize;
    }
}
