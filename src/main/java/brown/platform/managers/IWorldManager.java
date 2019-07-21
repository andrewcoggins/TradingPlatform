package brown.platform.managers;

import brown.platform.world.IWorld;

/**
 * IWorldManager creates and stores IWorld
 * 
 * @author andrewcoggins
 *
 */
public interface IWorldManager {

  /**
   * creates an IWorld given an IDomainManager and an IMarketManager
   * @param domain
   * IDomainManager used to create world
   * @param markets
   * IMarketManager used to create world
   */
  void createWorld(IDomainManager domain, IMarketManager markets);

  /**
   * get the IWorld stored within the IWorldManager
   * @return
   */
  IWorld getWorld();

}
