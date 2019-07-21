package brown.platform.world;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;

/**
 * A World contains a domainManager and a MarketManager. 
 * @author andrewcoggins
 *
 */
public interface IWorld {
  
  /**
   * gets the domainManager for the Simulation
   * @return
   */
  public IDomainManager getDomainManager();
  
  /**
   * gets the marketManager for the Simulation
   * @return
   */
  public IMarketManager getMarketManager(); 
  
}
