package brown.platform.simulation;

import brown.platform.managers.IWorldManager;

/**
 * ISimulation encompasses a world and encapsulates a scenario to run
 * 
 * @author andrewcoggins
 *
 */
public interface ISimulation {
  
  /**
   * Gets the WorldManager for the simulation.
   * 
   * @return
   */
  public IWorldManager getWorldManager(); 
  
}
