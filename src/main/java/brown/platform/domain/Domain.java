package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;


/**
 * a domain consists of Tradeables, an IValuationGenerator, and an AccountManager.
 */
public class Domain implements IDomain {

    private IItemManager tManager;
    private IValuationManager valuationManager;
    private IAccountManager acctManager;
    private IEndowmentManager endowmentManager;

    public Domain (IItemManager tManager, IValuationManager valuationManager, IAccountManager acctManager,
                   IEndowmentManager endowmentManager) {
        this.tManager = tManager;
        this.valuationManager = valuationManager;
        this.acctManager = acctManager;
        this.endowmentManager = endowmentManager;
    }
    
    @Override
    public IItemManager getTradeableManager() {
      return this.tManager;
    }

    @Override
    public IAccountManager getAccountManager() {
      return this.acctManager;
    }

    @Override
    public IEndowmentManager getEndowmentManager() {
      return this.endowmentManager;
    }

    @Override
    public IValuationManager getValuationManager() {
      return valuationManager;
    }
    
    
    @Override
    public String toString() {
      return "Domain [tManager=" + tManager + ", valuationManager=" + valuationManager
          + ", acctManager=" + acctManager + ", endowmentManager="
          + endowmentManager + "]";
    }
   
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result =
          prime * result + ((acctManager == null) ? 0 : acctManager.hashCode());
      result = prime * result
          + ((endowmentManager == null) ? 0 : endowmentManager.hashCode());
      result = prime * result + ((tManager == null) ? 0 : tManager.hashCode());
      result =
          prime * result + ((valuationManager == null) ? 0 : valuationManager.hashCode());
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
      Domain other = (Domain) obj;
      if (acctManager == null) {
        if (other.acctManager != null)
          return false;
      } else if (!acctManager.equals(other.acctManager))
        return false;
      if (endowmentManager == null) {
        if (other.endowmentManager != null)
          return false;
      } else if (!endowmentManager.equals(other.endowmentManager))
        return false;
      if (tManager == null) {
        if (other.tManager != null)
          return false;
      } else if (!tManager.equals(other.tManager))
        return false;
      if (valuationManager == null) {
        if (other.valuationManager != null)
          return false;
      } else if (!valuationManager.equals(other.valuationManager))
        return false;
      return true;
    }
    
}
