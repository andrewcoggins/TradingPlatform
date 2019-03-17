package brown.platform.world;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;

public interface IWorld {

  public IDomainManager getDomainManager();
  
  public IMarketManager getMarketManager(); 
  
}
