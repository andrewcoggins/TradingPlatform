package brown.platform.world.library;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;
import brown.platform.world.IWorld;


public class World implements IWorld {

    private IDomainManager domain;
    private IMarketManager market;

    public World(IDomainManager domain, IMarketManager market) {
        this.domain = domain;
        this.market = market;
    }
    
    @Override
    public IDomainManager getDomainManager() {
      return this.domain;
    }

    @Override
    public IMarketManager getMarketManager() {
      return this.market;
    }
    
    
    @Override
    public String toString() {
      return "World [domain=" + domain + ", market=" + market + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((domain == null) ? 0 : domain.hashCode());
      result = prime * result + ((market == null) ? 0 : market.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      World other = (World) obj;
      if (domain == null) {
        if (other.domain != null)
          return false;
      } else if (!domain.equals(other.domain))
        return false;
      if (market == null) {
        if (other.market != null)
          return false;
      } else if (!market.equals(other.market))
        return false;
      return true;
    }

}
