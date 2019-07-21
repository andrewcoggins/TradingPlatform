package brown.platform.simulation;

import brown.platform.managers.IWorldManager;

/**
 * ISimulation encompasses a world manager and encapsulates a scenario run 
 * by the platform
 * @author andrewcoggins
 *
 */
public interface ISimulation {
  
  /**
   * gets the WorldManager for the simulation
   * @return
   */
  public IWorldManager getWorldManager(); 
  
}
